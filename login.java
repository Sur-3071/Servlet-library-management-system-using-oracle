import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String email=request.getParameter("ename");
		String password=request.getParameter("pname");
		p.println("<head><style>\r\n"
				+ "    img\r\n"
				+ "    {\r\n"
				+ "        width:200px;\r\n"
				+ "        animation: shake 0.5s linear infinite;\r\n"
				+ "    }\r\n"
				+ "    @keyframes shake\r\n"
				+ "    {\r\n"
				+ "        0%{\r\n"
				+ "            transform:rotate(10deg);\r\n"
				+ "        }\r\n"
				+ "        50%\r\n"
				+ "        {\r\n"
				+ "            transform:rotate(-10deg);\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "    </style></head>'");
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Suresh");
			Statement st=con.createStatement();
			ResultSet r=st.executeQuery("select * from login");
			int c=0;
			while(r.next())
			{
				if(r.getString(1).equals(email) && r.getString(2).equals(password))
				{
					c=1;
					break;
				}
			}
			if(c==0)
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Invalid email or password so please try again to click on logo</i></h1></marquee>");
				p.println("<div align=center><a href='login.html'><img src='a4.png' height='200px' width='200px'></a>");
			}
			else
			{
				response.sendRedirect("adminpage.html");

			}
	     }
		catch(Exception e)
		{
			p.println("ERROR"+e.getMessage());
		}

}
}
