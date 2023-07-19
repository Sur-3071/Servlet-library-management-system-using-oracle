import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class bookdelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String bookid =request.getParameter("bid");
		
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
				+ "    </style></head>");
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Suresh");
			Statement st=con.createStatement();
			ResultSet r=st.executeQuery("select * from books");
			int c=0;
			while(r.next())
			{
				if(r.getString(1).equals(bookid))
				{
					c=1;
					break;
				}
			}
			if(c==1)
			{
				PreparedStatement st1=con.prepareStatement("delete from  books where bookid=?");
				st1.setString(1, bookid);
				st1.executeQuery();
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Book is successfully deleted</i></h1></marquee>");
				p.println("<div align=center><img src='a5.jpg' height='200px' width='200px'></a></div>");

			}
			else
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>book id is not  exist</i></h1></marquee>");
				p.println("<div align=center><img src='a10.jpeg' height='200px' width='200px'></a></div>");

			}
				
			}
		catch(Exception e)
		{
			p.println("ERROR"+e.getMessage());
		}
	}
}