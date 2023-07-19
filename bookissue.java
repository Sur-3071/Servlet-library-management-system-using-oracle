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

public class bookissue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String bookid =request.getParameter("bid");
		String name=request.getParameter("sname");
		String roll=request.getParameter("rname");
		String year=request.getParameter("yname");
		String dept=request.getParameter("dname");
		
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
			ResultSet r=st.executeQuery("select * from BOOKS");
			int c=0;
			PreparedStatement st2=con.prepareStatement("delete from books where bookid=?");
			String title=null;
			String author=null;
			String lang=null;
			String page=null;
			String publisher=null;
			while(r.next())
			{
				if(r.getString(1).equals(bookid))
				{
					c=1;
					title=r.getString(2);
					author=r.getString(3);
					lang=r.getString(4);
					page=r.getString(5);
					publisher=r.getString(6);
					//p.println(title+" "+author+" "+lang+" "+page+" "+publisher);
					st2.setString(1,bookid);
					st2.executeQuery();
					break;
				}
			}
			if(c==1)
			{
				PreparedStatement st1=con.prepareStatement("insert into bookissue(BOOKID,TITLE,	AUTHOR,	LANGUAGECODE,PAGE_NUMBERS,PUBLISHER,STUDENT_NAME,ROLL_NUMBER,YEAR,DEPARTMENT) values(?,?,?,?,?,?,?,?,?,?)");
				st1.setString(1, bookid);
				st1.setString(2, title);
				st1.setString(3, author);
				st1.setString(4, author);
				st1.setString(5, author);
				st1.setString(6, publisher);
				st1.setString(7, name);
				st1.setString(8, roll);
				st1.setString(9, year);
				st1.setString(10, dept);
				st1.executeQuery();
				
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Data entered succesfully done</i></h1></marquee>");
				p.println("<div align=center><img src='a5.jpg' height='200px' width='200px'></a></div>");

			}
			else
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Invalid book id or these books are not authorized to issued</i></h1></marquee>");
				p.println("<div align=center><img src='a10.jpeg' height='200px' width='200px'></a></div>");
			}
				
		}
		catch(Exception e)
		{
			p.println("ERROR"+e.getMessage());
		}
	}
}