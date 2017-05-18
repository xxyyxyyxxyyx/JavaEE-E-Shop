

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MultipleQuery
 */
@WebServlet("/querymv")
public class MultipleQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultipleQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop","root","xxyyxyyx");
			stmt = conn.createStatement();
			String[] authors = request.getParameterValues("author");
			String select = "Select * from books where author in (";
			select += "'"+authors[0]+"'";
			for(int i=1;i<authors.length;++i){
				select += ", '"+authors[i]+"'";
			}
			select += ")";
			out.println("Your query:"+select);
			ResultSet rset = stmt.executeQuery(select);
			out.println("<br/> Result:<br/>");
			while(rset.next()){
				out.println(rset.getString("author")+"-----"+rset.getString("title")+"<br/>");
			}
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			out.close();
			try{
				conn.close();
				stmt.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
