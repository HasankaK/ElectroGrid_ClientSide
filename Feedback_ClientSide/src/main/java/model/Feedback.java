package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Feedback {
	private Connection connect() 
	 { 
	 Connection con = null; 
	 try
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/feedback", "root", ""); 
	 } 
	 catch (Exception e) 
	 {e.printStackTrace();} 
	 return con; 
	 } 
	
	public String readFeedback() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for reading."; } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>name</th><th>email</th>" +
	 "<th>contact</th>" + 
	 "<th>message</th>" +
	 "<th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from feedbacks"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
		 String feedbackId = Integer.toString(rs.getInt("feedbackId")); 
		 String name = rs.getString("name"); 
		 String email = rs.getString("email"); 
		 String contact = rs.getString("contact"); 
		 String message = rs.getString("message"); 
		 
	 // Add into the html table
		 output += "<tr><td><input id='hidFeedbackIdUpdate' name='hidFeedbackIdUpdate'   type='hidden' value='" + feedbackId
				 + "'>" + name + "</td>"; 
	 output += "<td>" + email + "</td>"; 
	 output += "<td>" + contact + "</td>"; 
	 output += "<td>" + message + "</td>"; 
	 
	// buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update'  class=''btnUpdate btn btn-secondary'></td>"
	+ "<td><input name='btnRemove' type='button' value='Remove'  class='btnRemove btn btn-danger'data-feedbackid='" 
	+ feedbackId + "'>" + "</td></tr>"; 
	  } 
	 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the Feedbacks"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 } 
	
	//insert method
		public String insertFeedback( String name, String email, String contact ,String message) 
		 { 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) {
			 return "Error while connecting to the database for inserting.";
		 } 
		 
		 // create a prepared statement
		 String query = " insert into feedbacks (`feedbackId`,`name`,`email`,`contact`,`message`)"
				 		+ " values (?, ?, ?, ?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		
		
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setString(2, name); 
		 preparedStmt.setString(3, email ); 
		 preparedStmt.setString(4, contact ); 
		 preparedStmt.setString(5, message); 
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 String newFeedback = readFeedback();
			output = "{\"status\":\"success\", \"data\": \"" + newFeedback + "\"}";
		 
		 } 
		 catch (Exception e) 
		 { 
		 output = "{\"status\":\"error\", \"data\":  \"Error while inserting feedback.\"}"; 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
		
	
	public String updateFeedback(String FeedbackId, String name, String email ,String contact, String message) 

	{ 
		 String output = ""; 
		 try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for updating."; } 
		 // create a prepared statement
		 
		 String query = "UPDATE feedbacks SET name=?,email=?,contact=?,message=?  WHERE feedbackId=?"; 
		
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 // binding values
		  preparedStmt.setString(1, name); 
		  preparedStmt.setString(2, email);
		  preparedStmt.setString(3, contact);
		 preparedStmt.setString(4, message); 
		 preparedStmt.setInt(5, Integer.parseInt(FeedbackId)); 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 String newFeedback = readFeedback(); 
		 output = "{\"status\":\"success\", \"data\": \"" + newFeedback + "\"}"; 
		 } 

		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\":  \"Error while updating the feedback.\"}"; 
					 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
	
	
		public String deleteFeedback(String feedbackId) 
		 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 	{return "Error while connecting to the database for deleting."; } 
			 
		 // create a prepared statement
		 String query = "delete from feedbacks where feedbackId=?"; 
		 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(feedbackId)); 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close();
		 
		 String newFeedback = readFeedback(); 
		 output = "{\"status\":\"success\", \"data\": \"" + newFeedback + "\"}"; 

		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\":  \"Error while deleting the feedback.\"}"; 
		 
		 System.err.println(e.getMessage()); 
		 } 
		 return output; 
		 } 
		
		} 

	 



