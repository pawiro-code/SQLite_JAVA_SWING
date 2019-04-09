package DB;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JED
 */
import java.sql.DriverManager;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.sql.PreparedStatement; 
import javax.swing.JTextArea;

public class SqliteDB {
    Connection c = null;
    Statement stmt = null;
    private JTextArea tArea;

    SqliteDB() {
    
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            System.out.println("Connected to DB");
            //c.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            
        }
        
    }
     private Connection connect() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:DB.db";  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }  
     
    public void listStudents() {
        try {
            //this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
            
                while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("name") + "\t" +  
                                   rs.getDouble("capacity"));  
            }  
           /* 
            while(rs.next()) {
               // int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                
                System.out.println(firstname);
            }*/
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
     public void insert(String name, double capacity) {  
        String sql = "INSERT INTO Students(name, capacity) VALUES(?,?)";  
   
        try{  
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, name);  
            pstmt.setDouble(2, capacity);  
            pstmt.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
    
    
     public void selectAll(){  
        String sql = "SELECT * FROM Students";  
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
            
           
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getInt("id") +  "\t" +   
                                   rs.getString("name") + "\t" +  
                                   rs.getDouble("capacity")); 
                
                 Glowna.setMessagesArea(rs.getInt("id") +  "\t" +   
                                   rs.getString("name") + "\t" +  
                                   rs.getDouble("capacity") +"\n");
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
     
       public static void createNewTable() {  
        // SQLite connection string  
        String url = "jdbc:sqlite:DB.db";  
          
        // SQL statement for creating a new table  
        String sql = "CREATE TABLE IF NOT EXISTS Students (\n"  
                + " id integer PRIMARY KEY,\n"  
                + " name text NOT NULL,\n"  
                + " capacity real\n"  
                + ");";  
          
        try{  
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
  
    public void closeConnection() {
    try {
        
    } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            
        }
    }
    
  
    
    
    public void executeQuery(String query) {
        try {
            String sql = "SELECT * FROM Students";  
            stmt = c.createStatement();
            stmt.executeQuery(query);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}


