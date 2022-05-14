package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConnect;

public class Bill {
	
	
	private static Connection con=null;
	
	//insert a bill
		public String insertBill(String billCode,String cusId, String month, String units,String KWHCharge,
				String fixedCharge,String rebate, String total)
		{
			
				String output = "";
				String res="";

				try
				{
					con = DBConnect.connect();
					if (con == null)
					{
						return "Error while connecting to the database for inserting.";
					}
		
					// create a prepared statement
					String query = " insert into bill (`billId`,`billCode`,`customerID`,`month`,`units`,`KWHCharge`,`fixedCharge`,`rebate`,`total`)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
					//allow to write parameterized queirs.PreparedStatement,
					//the Database uses an already compiled and defined access plan, this allows the prepared statement query to run 
					//faster than a normal query
					PreparedStatement preparedStmt = con.prepareStatement(query);
		
					// binding values
					preparedStmt.setInt(1, 0);
					preparedStmt.setString(2,billCode);
					preparedStmt.setInt(3,Integer.parseInt(cusId));
					preparedStmt.setString(4, month);
					preparedStmt.setInt(5,Integer.parseInt(units));
					preparedStmt.setDouble(6,Double.parseDouble(KWHCharge));
					preparedStmt.setDouble(7,Double.parseDouble(fixedCharge));
					preparedStmt.setDouble(8,Double.parseDouble(rebate));
					preparedStmt.setDouble(9,Double.parseDouble(total));
		

					
					// execute the statement
					preparedStmt.execute();  
		
					//close the connection
					con.close();
					
					
		
					String newBill = readBill();
					output = "{\"status\":\"success\", \"data\": \"" +
					newBill + "\"}";
					
				}catch (SQLException e)
				{
					output = "{\"status\":\"error\", \"data\":\"Customer not found\"}";
					System.err.println(e.getMessage());
				}
				//catch exception
				catch (Exception e)
				{
					output = "{\"status\":\"error\", \"data\":\"Error while inserting the bill.\"}";
					System.err.println(e.getMessage());
				}
		
				return output;
			}
		
		
			//read all bills
			public String readBill()
			{
			
				String output = "";
		
				try
				{
					con = DBConnect.connect();
		
					if (con == null)
					{
						return "Error while connecting to the database for reading.";
			
					}
		
					// Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Bill Code</th>" +
				 "<th>Customer Id</th>" +
				 "<th>Month</th>" +
				 "<th>Units</th>" +
				 "<th>KWH Charge</th>" +
				 "<th>Fixed Charge</th>" +
				 "<th>Rebate</th>" +
				 "<th>Total</th>" +
				 "<th>Update</th><th>Remove</th></tr>";
		
				 //query
				 String query = "select * from bill";
		
				 Statement stmt = con.createStatement();
		
				 ResultSet rs = stmt.executeQuery(query);
				 System.err.println("res"+rs);
		
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
					 String billId = Integer.toString(rs.getInt("billId"));
					 String billCode = rs.getString("billCode");
					 String customerId = Integer.toString(rs.getInt("customerID"));
					 String month = rs.getString("month");
					 String units = Integer.toString(rs.getInt("units"));
					 String KWHCharge=Double.toString(rs.getDouble("KWHCharge"));
					 String fixedCharge=Double.toString(rs.getDouble("fixedCharge"));
					 String rebate=Double.toString(rs.getDouble("rebate"));
					 String total =Double.toString(rs.getDouble("total")); 
		
					 // Add into the html table
					 output += "<tr><td>" + billCode + "</td>";
					 output += "<td>" + customerId + "</td>";
					 output += "<td>" + month + "</td>";
					 output += "<td>" + units + "</td>";
					 output += "<td>" + KWHCharge + "</td>";
					 output += "<td>" + fixedCharge + "</td>";
					 output += "<td>" + rebate + "</td>";
					 output += "<td>" + total + "</td>";
		
					
				 
					 // buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' "
					+ "class='btnUpdate btn btn-secondary' data-itemid='" + billId  + "'></td>"
					+ "<td><input name='btnRemove' type='button' value='Remove' "
					+ "class='btnRemove btn btn-danger' data-itemid='" + billId  + "'></td></tr>";
					
				}
		
				 	//close the connection
				 	con.close();
		
				 	// output as html table
				 	output += "</table>";
					}
					catch (Exception e)
				{
						output = "Error while reading the bill";
						System.err.println(e.getMessage());
				}
		
					return output;
				}
		

			//update the bill
			public String updateBill(String billId,String billCode, String customerId, String month, String units,String KWHCharge,
				String fixedCharge,String rebate, String total)
			{
				String output = "";
			try
			{
				con = DBConnect.connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
				
			}
				// create a prepared statement
				String query = "UPDATE bill SET billCode=?,customerID=?,month=?,units=?,KWHCharge=?,fixedCharge=?,rebate=?,total=? WHERE billId=?";
			
				PreparedStatement preparedStmt = con.prepareStatement(query);
			
				// binding values
				preparedStmt.setString(1, billCode);
				preparedStmt.setInt(2, Integer.parseInt(customerId));
				preparedStmt.setString(3, month);
				preparedStmt.setInt(4, Integer.parseInt(units));
				preparedStmt.setDouble(5,Double.parseDouble(KWHCharge));
				preparedStmt.setDouble(6,Double.parseDouble(fixedCharge));
				preparedStmt.setDouble(7,Double.parseDouble(rebate));
				preparedStmt.setDouble(8,Double.parseDouble(total));
				preparedStmt.setInt(9, Integer.parseInt(billId));
				
								
			
				// execute the statement
				preparedStmt.execute();
			
				//close connection
				con.close();
			
				String newBill = readBill();
				output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";
				
			}
				//catch exceptions
				catch (Exception e)
				{
					output = "{\"status\":\"error\", \"data\": \"Error while updating the bill.\"}";
					System.err.println(e.getMessage());
				}
			
					return output;
				}
		
		
			//delete the bill
			public String deleteBill(String billId)
			{
				
				String output = "";
			
				try
				{
					con = DBConnect.connect();
					if (con == null)
				
				{
					return "Error while connecting to the database for deleting.";
				
				}
					
					// create a prepared statement
					String query = "delete from bill where billId=?";
			
					PreparedStatement preparedStmt = con.prepareStatement(query);
			
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(billId));
			
					// execute the statement
					preparedStmt.execute();
		
					//close connection
					con.close();
			
					String newBill = readBill();
					output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";
				}
				catch (Exception e)
				{
					output = "{\"status\":\"error\", \"data\": \"Error while delete the bill.\"}";
					System.err.println(e.getMessage());
				}
				return output;
				}
		

			//get a particular  bill using billCode
			public String getBill(String billCode)
			{
				
				String output = "";
			
				try
				{
				
					con = DBConnect.connect();
			
					if (con == null)
				
					{
						return "Error while connecting to the database for getting data.";
				
					}
					// create a prepared statement
					String query = "select * from bill where billCode=?";
			
					PreparedStatement preparedStmt = con.prepareStatement(query);
			
					// binding values
					preparedStmt.setString(1, billCode);
			
					ResultSet rs = preparedStmt.executeQuery();
			
					// get all the details for a particular billCode
					while (rs.next())
					{
						String billID = Integer.toString(rs.getInt("billId"));
						String billCODE = rs.getString("billCode");
						String customerId = rs.getString("customerID");
						String month = rs.getString("month");
						String units = Integer.toString(rs.getInt("units"));
						String KWHCharge=Double.toString(rs.getDouble("KWHCharge"));
						String fixedCharge=Double.toString(rs.getDouble("fixedCharge"));
						String rebate=Double.toString(rs.getDouble("rebate"));
						String total=Double.toString(rs.getDouble("total"));
			
						output +="<h4>"+ "View Bill " + " Bill Code-" +billCODE+ " BillId-" +billID + "</h4><br>";
						output += "<p>"+"<b>Customer Id</b> :"+ customerId+"</p>";
						output += "<p>"+"Month :"+ month+"</p>";
						output += "<p>"+"No of Units :"+ units+"</p>";
						output += "<p>"+"KWHCharge :"+ KWHCharge+"</p>";
						output += "<p>"+"Fixed Charge :"+ fixedCharge+"</p>";
						output += "<p>"+"Rebate :"+ rebate+"</p>";
						output += "<p>"+"Total :"+ total+"</p>";
						
						String data=output;
						
						output = "{\"status\":\"success\", \"data\": \"" + data + "\"}";
						
						
			
					}
					con.close();
					}
					catch (Exception e)
					{
						output = "{\"status\":\"error\", \"data\": \"Error while get the bill.\"}";
						System.err.println(e.getMessage());
					}
						return output;
				}
		 
}
