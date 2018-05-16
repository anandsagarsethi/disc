package connect;
import java.sql.*;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.*;

public class MyConnection {
	public Connection conn;
	public ArrayList<Content> content;
	Content  cvalue;
	 ArrayList<Codes> codes;
	Codes code;
	

	public MyConnection(){
		content = new ArrayList<Content>();
		codes = new ArrayList<Codes>();
		
		connect();
		fetchType();
		fetchCodes();
		merge();
		
		
	}
	
	
	  public void connect()
	  {
	    try
	    {
	      // create our mysql database connection
	    	
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://lowes.cmhcphlh7squ.us-east-2.rds.amazonaws.com";
	      Class.forName(myDriver);
	       conn = DriverManager.getConnection(myUrl, "anandsagarsethi", "Newuser123");
	    }
	      catch (Exception e)
		    {
		      System.out.println(e);
		    }
	  }
	    
	    public void fetchType() {
	      
	    	 try
	 	    {
	    		 cvalue = new Content();
	      // our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query = "select * from lowes.Distype";

	      // create the java statement
	      Statement st = conn.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(query);
	      
	      // iterate through the java resultset
	      while (rs.next())
	      {
	    	  cvalue = new Content();
	    	  cvalue.setId(rs.getInt("id"));
	    	  cvalue.setCoupon(rs.getString("dis_name"));
 	 // System.out.println(cvalue.getId());
	    	  content.add(cvalue);
	      }
	      st.close();
	      rs.close();
	    }
	    catch (Exception e)
	    {
	      System.out.println(e);
	    }
	  }
	    
	    
	  
	   public void fetchCodes() {
		   
			 try
		 	    {
				 code = new Codes();
				 // if you only need a few columns, specify them by name instead of using "*"
			      String query = "select * from lowes.discode";

			      // create the java statement
			      Statement st = conn.createStatement();
			      
			      // execute the query, and get a java resultset
			      ResultSet rs = st.executeQuery(query);
			      
			      
			      // iterate through the java resultset
			      
			      
			      while (rs.next())
			      {
			    	code = new Codes();
			    	code.setId(rs.getInt("id"));
			    	code.setCodes(rs.getString("code"));
			    //	System.out.println(code.getCodes());
			    	codes.add(code);
			    	
			      
			      }
			      st.close();
			      rs.close();
			      conn.close();
			      
				 
		 	    }
			 catch (Exception e)
			    {
			      System.out.println(e);
			    }
		   
	   }

	   public void merge() {
		   ArrayList<String> str;
		   for (int i = 0; i<content.size(); i++) {
			   cvalue = content.get(i);
			   		   str =  new ArrayList<String>();
			   for( int j = 0; j<codes.size();j++) {
				   code = codes.get(j);
				   if (cvalue.getId()== code.getId()){
					   String s= code.getCodes();
					   str.add(s);
					   					   
				   }
				   
			   }
			   cvalue.setCodes(str);
			   
			   
		   }
		   
		   
		for (int i =0; i<content.size();i++) {
			cvalue = content.get(i);
		//	System.out.println(cvalue.getId());
		//	System.out.println(cvalue.getCoupon());
			str = cvalue.getCodes();
		//	for (int j=0;j<str.size(); j++) {
				// System.out.println(str.get(j).toString());
				 		
				
		//	}
			
		}
	   }
	   
	   
	   public JSONObject getJSON() {
		   ArrayList<String> str;
		   JSONObject obj = new JSONObject();
		   JSONObject ret_obj = new JSONObject();
		   
		   
		   JSONArray array = new JSONArray();
		   JSONArray all = new JSONArray();
		   //System.out.println("JSON Start");
		   
		   
			for (int i =0; i<content.size();i++) {
				cvalue = content.get(i);
				obj = new JSONObject();
				obj.put("id", cvalue.getId()+"");
				obj.put("name", cvalue.getCoupon());
				
				str = cvalue.getCodes();
				array  = new JSONArray();
				for (int j=0;j<str.size(); j++) {
					
					array.add(str.get(j));
					//System.out.println(str.get(j));
				}
				
				obj.put("code", array);
				//System.out.println(obj);
				
				all.add(obj);
				
			}
			
			
		   ret_obj.put("Values", all);
			System.out.println(ret_obj.toJSONString());
			
		   return ret_obj;
	   }
	   
	  
	  public static void main( String[] args) {
		  MyConnection connect = new MyConnection();
		  
		  System.out.println(connect.getJSON());
		/*
		  connect.connect();
		  
		  connect.fetchType();
		  connect.fetchCodes();
		  connect.merge();
		  
		  */

		  
		  
	/*	  JSONObject obj = new JSONObject();
		  JSONArray array = new JSONArray();
		  array.add("123");
		  array.add("133");
		  
obj.put("id", "1");
obj.put("desc", "10$off50");
obj.put("code", array);

		

	      System.out.print(obj);
	  */

		  
	  }
	}

