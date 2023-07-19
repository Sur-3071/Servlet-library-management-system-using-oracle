import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/authorsearch")
public class authorsearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String author=request.getParameter("aname");
		//String author="suresh";
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Suresh");
			Statement st=con.createStatement();
			ResultSet r=st.executeQuery("select * from books");
			int c=0,k=0;
			ResultSetMetaData rsmd=r.getMetaData();  
			int total=rsmd.getColumnCount();  
			p.print("<table width=50% border=2 align='center'>");  
			p.print("<tr>");  
			for(int i=1;i<=total;i++)  
			{  
			p.print("<th>"+rsmd.getColumnName(i)+"</th>");  
			}  
			  
			p.print("</tr>");  
			while(r.next())
			{
				if(r.getString(3).equals(author))
				{
					c=1;
					k++;
					p.print("<tr><td>"+r.getString(1)+"</td><td>"+r.getString(2)+"</td><td>"+r.getString(3)+"</td><td>"+r.getString(4)+"</td><td>"+r.getString(5)+"</td><td>"+r.getString(6)+"</td></tr>"); 
				}
			}
			p.print("</table>");  
			if(c==0)
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Required  authors book are not there in library</i></h1></marquee>");

			}
			else
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>"+k+" Required authors books are avalible in library</i></h1></marquee>");
			}

		}
		catch(Exception e)
		{
			p.println("ERROR"+e.getMessage());
		}
	}

}
