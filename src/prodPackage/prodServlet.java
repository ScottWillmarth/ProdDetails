package prodPackage;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class prodServlet
 * Create a servlet-based application that shows a form to enter a product ID. 
 * The product ID is then validated, and product details are retrieved from the 
 * database and displayed to the user. You need to create a product table in MySQL 
 * and prepopulate it with data. Use JDBC to do all database processing.
 */

//@WebServlet("/prodServlet")
public class prodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public prodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		// JDBC driver name and database URL
	      final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	      final String DB_URL="jdbc:mysql://localhost:3306/pets";

	      //  Database credentials
	      final String USER = "root";
	      final String PASS = "";

	      // Set response content type
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Database Result";
	      
	      String docType =
	         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	      
	      out.println(docType +
	         "<html>\n" +
	         "<head><title>" + title + "</title></head>\n" +
	         "<body bgcolor = \"#f0f0f0\">\n" +
	         "<h1 align = \"center\">" + title + "</h1>\n");
	      
	      
	      try 
	      {
	    	 
	         // Register JDBC driver
	         Class.forName(JDBC_DRIVER);
	         
	         // Open a connection    
	    	  
	         Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	         
	         // Execute SQL query
	         java.sql.Statement stmt = conn.createStatement();
	         /////////
	         
	         String input = request.getParameter("prod_id");
	         
	         String sql = "SELECT * FROM `products` WHERE `id` = " + input + ";";
	         //String sql = "SELECT * FROM `products` WHERE `id` = 1;";
	         
	         ResultSet rs = stmt.executeQuery(sql);
	         
	         if (rs.next() == false) 
	         { 
	        	 out.println("No result found"); 
	         }
	         else
	         {
	        	// Extract data from result set
		         do 
		         {
		            //Retrieve by column name
		            String name = rs.getString("name");
		            String color = rs.getString("color");
		            double price  = rs.getDouble("price");
		            int id  = rs.getInt("id");

		            //Display values
		            out.println("Name: " + name + "<br>");
		            out.println("Color: " + color + "<br>");
		            out.println("Price: " + price + "<br>");
		            out.println("ID: " + id + "<br>");
		            
		         } while(rs.next());
	         }


	         out.println("</body></html>");

	         // Clean-up environment
	         rs.close();
	         stmt.close();
	         conn.close();
	      } 
	      catch(SQLException se) 
	      {
	         //Handle errors for JDBC
	         se.printStackTrace();
	      } 
	      catch(Exception e) 
	      {
	         //Handle errors for Class.forName
	         e.printStackTrace();
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
