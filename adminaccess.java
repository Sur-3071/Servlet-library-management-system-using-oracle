import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class adminaccess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String email=request.getParameter("ename");
		String password=request.getParameter("pname");
		String repassword=request.getParameter("rpname");
		if(password.equals(repassword))
		{
			p.println("<head><style>\r\n"
					+ "    img\r\n"
					+ "    {\r\n"
					+ "        width:200px;\r\n"
					+ "        animation: shake 0.5s linear infinite;\r\n"
					+ "    }\r\n"
					+ "    @keyframes shake\r\n"
					+ "    {\r\n"
					+ "        0%{\r\n"
					+ "            transform:rotate(10deg)\r\n"
					+ "        }\r\n"
					+ "        50%\r\n"
					+ "        {\r\n"
					+ "            transform:rotate(-10deg)\r\n"
					+ "        }\r\n"
					+ "    }\r\n"
					+ "    </style></head>");
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Suresh");
				Statement st=con.createStatement();
				ResultSet r=st.executeQuery("select * from login");
				int c=0;
				while(r.next())
				{
					if(r.getString(1).equals(email))
					{
						c=1;
						break;
					}
				}
				if(c==0)
				{
					PreparedStatement st1=con.prepareStatement("insert into login values(?,?)");
					st1.setString(1,email);
					st1.setString(2,password);
					st1.executeQuery();
					p.println("<marquee behavior=alternate scrollamount=12><h1><i>New admin access is granted</i></h1></marquee>");
			        p.println("<a href='login.java'><button type='button'>Back</button></a>");
					p.println("<div align=center><img src='a5.jpg' height='200px' width='200px'></a></div>");
					
				}
				else
				{
					p.println("<marquee behavior=alternate scrollamount=12><h1><i>Email user is already exists</i></h1></marquee>");
			        p.println("<a href='newadmin.html'><button type='button'>Back</button></a>");
				}
		     }
			catch(Exception e)
			{
				p.println("ERROR"+e.getMessage());
			}
		}
		else
		{
			p.println("<marquee behavior=alternate scrollamount=12><h1><i>Password and Reentered password are not same try again</i></h1></marquee>");
			p.println("<div align=center><a href='newadmin.html'><img src='a1.png' height='200px' width='200px'></a>");


		}

}
}
