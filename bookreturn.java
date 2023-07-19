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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class bookreturn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String bookid =request.getParameter("bid");
		String date1 =request.getParameter("dname");
		
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
			ResultSet r=st.executeQuery("select * from bookissue");
			int c=0;
			PreparedStatement st2=con.prepareStatement("delete from bookissue where bookid=?");
			String title=null;
			String author=null;
			String lang=null;
			String page=null;
			String publisher=null;
			String date=null;
			int fine=0;
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
					date=r.getString(11);
					st2.setString(1,bookid);
					st2.executeQuery();
					break;
				}
			}

			SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yy");
			        try {
			            Date d1 = sdf.parse(date);
			            Date d2 = sdf.parse(date1);
			            
			            long difference_In_Time
			                    = d2.getTime() - d1.getTime();

			            long difference_In_Years
			                    = (difference_In_Time
			                    / (1000l * 60 * 60 * 24 * 365));

			            long difference_In_Days
			                    = (difference_In_Time
			                    / (1000 * 60 * 60 * 24))
			                    % 365;
			       
			            fine=(int) (difference_In_Years*365+difference_In_Days);
			           fine=fine-10;
			        }
			        catch (ParseException e) 
			        {
			            p.println("ERROR given date formate is wrong try again");
			        }
			
			if(c==1)
			{
				PreparedStatement st1=con.prepareStatement("insert into books values(?,?,?,?,?,?)");
				st1.setString(1, bookid);
				st1.setString(2, title);
				st1.setString(3, author);
				st1.setString(4, lang);
				st1.setString(5, page);
				st1.setString(6, publisher);
				st1.executeQuery();
				if(fine>0)
				{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>You have to pay fine up to: "+fine*5+" Rupees for extraa "+fine+" days </i></h1></marquee>");
				}
				else
				{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Book retured succesfully done</i></h1></marquee>");
				}
				p.println("<div align=center><img src='a5.jpg' height='200px' width='200px'></a></div>");

			}
			else
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Invalid book id</i></h1></marquee>");
				p.println("<div align=center><img src='a10.jpeg' height='200px' width='200px'></a></div>");
			}
				
		}
		catch(Exception e)
		{
			p.println("ERROR"+e.getMessage());
		}
	}
}