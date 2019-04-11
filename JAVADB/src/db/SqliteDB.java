package db;

import java.sql.DriverManager;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.sql.PreparedStatement; 


public class SqliteDB {
   
    Connection c = null;
    Statement stmt = null;
    
    SqliteDB() {
    
        try {
            
            Class.forName("org.sqlite.JDBC");
                        
        } catch (Exception e) {
            
            //log
            System.out.println("Error: " + e.getMessage());
            
        }
    }
    
    private Connection connect() {  
       
        //SQLite connection string with database filename  
        String url = "jdbc:sqlite:DB.db";  
        Connection conn = null;  
        
        try {  
            
            conn = DriverManager.getConnection(url);
            
        } catch (SQLException e) { 
            
            //log
            System.out.println(e.getMessage());  
        }  
        
        return conn;  
    }  
     
    //get last records id
    public int getLastId() {
       
        String sql = "SELECT * FROM Students";  
        int lastId = 0;
        
        try {  
           
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            //loop through the result set  
            while (rs.next()) {  
                lastId++;
                    
                //log
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("name") + "\t" +  
                                   rs.getString("lastname")); 
            }  
                        
        } catch (SQLException e) {  
            
            //log
            System.out.println(e.getMessage());  
        }  
        
        return lastId;
    }
    
    public void update(int id, String name, String lastname) {
        
        //SQL statement for update
        String sql = "UPDATE Students SET name = ? , "
                                   + "lastname = ? "
                                   + "WHERE id = ?";
 
        try {
           
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
 
            //setting parameter
            pstmt.setString(1, name);
            pstmt.setString(2, lastname);
            
            //where id = ?
            pstmt.setInt(3, id);
            
            //update 
            pstmt.executeUpdate();
            
            //log
            System.out.println("Updated record with ID = " + Integer.toString(id));
        
        } catch (SQLException e) {
            
            //log
            System.out.println(e.getMessage());
        }
    }
    
    public void delete(int id) {
        
        //SQL statement for delete
        String sql = "DELETE FROM Students WHERE id = ?";
 
        try {
                  
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
 
            // where id = ?
            pstmt.setInt(1, id);
            
            //update
            pstmt.executeUpdate();
                
            //log
            System.out.println("Deleted record with ID = " + Integer.toString(id));
        } catch (SQLException e) {
            
            //log
            System.out.println(e.getMessage());
        }
    }
    
    public void insert(String name, String lastname) {  
        
        //SQL statement for insert
        String sql = "INSERT INTO Students(name, lastname) VALUES(?,?)";  
   
        try {  
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            
            pstmt.setString(1, name);  
            pstmt.setString(2, lastname);  
            pstmt.executeUpdate();
            
            //log
            System.out.println("Inserted record with name = " + name + " lastname = " + lastname);
            
        } catch (SQLException e) { 
            
            //log
            System.out.println(e.getMessage());  
        }  
    }  
    
    
    public void selectAll(){  
        
        //SQL statement for select all
        String sql = "SELECT * FROM Students";  
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  

                
                //loop through the result set  
                while (rs.next()) {
                    //log
                    System.out.println(rs.getInt("id") +  "\t" +   
                                       rs.getString("name") + "\t" +  
                                       rs.getString("lastname")); 
                    
                    //creating array and transfer it to main class
                    String[] strArray = new String[]{rs.getString("id"),rs.getString("name"),rs.getString("lastname")};
                    JavaDB.setMessagesArea(strArray);
                }  
               
        } catch (SQLException e) { 
            
            //log
            System.out.println(e.getMessage());  
        }  
    }
     
    public void createNewTable() {  
                  
        //SQL statement for new table
        String sql = "CREATE TABLE IF NOT EXISTS Students (\n"  
                   + " id integer PRIMARY KEY,\n"  
                   + " name text NOT NULL,\n"  
                   + " lastname text\n"  
                   + ");";  
          
        try {  
           
           Connection conn = this.connect();
           Statement stmt = conn.createStatement();  
           stmt.execute(sql);
           
           //log
           System.out.println("Students table is ready");
           
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
  
    public void closeConnection() {
        
        try {
        
        } catch (Exception e) {
            
            //log
            System.out.println("Error: " + e.getMessage());
        }
    }
    
  
    
    
    public void executeQuery(String query) {
        try {
            
            stmt = c.createStatement();
            stmt.executeQuery(query);
            
        } catch (SQLException e) {
            
            //log
            System.out.println("Error: " + e.getMessage());
        }
    }
}


