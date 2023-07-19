import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/issuepublishersearch")
public class issuepublishersearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter p=null;
		p=response.getWriter();
		String publisher=request.getParameter("pname");
		//String publisher="pragati";
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Suresh");
			Statement st=con.createStatement();
			ResultSet r=st.executeQuery("select * from bookissue");
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
				if(r.getString(4).equals(publisher))
				{
					c=1;
					k++;
					p.print("<tr><td>"+r.getString(1)+"</td><td>"+r.getString(2)+"</td><td>"+r.getString(3)+"</td><td>"+r.getString(4)+"</td><td>"+r.getString(5)+"</td><td>"+r.getString(6)+"</td><td>"+r.getString(7)+"</td><td>"+r.getString(8)+"</td><td>"+r.getString(9)+"</td></tr>"); 
				}
			}
			p.print("</table>");  
			if(c==0)
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>Required publisher books are not issued to any student</i></h1></marquee>");

			}
			else
			{
				p.println("<marquee behavior=alternate scrollamount=12><h1><i>"+k+" Required publisher books are issued to students</i></h1></marquee>");

			}

		}
		catch(Exception e)
		{
			p.println("ERROR"+e.getMessage());
		}
	}

}