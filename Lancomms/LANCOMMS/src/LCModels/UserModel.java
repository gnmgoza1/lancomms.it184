/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LCModels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author eufrik
 */
public class UserModel {
    
    public boolean checkPassword(int userId, String oldpw){
        Connection con=null;
        ConnectDB callConnector = new ConnectDB();  
        ResultSet rs=null;
        Statement stmt=null; 
        try{
            con = callConnector.connectToDB();         
            stmt = con.createStatement();              
            String sql = "SELECT * FROM `user` WHERE user_id = '" + userId + "';";
            rs = stmt.executeQuery(sql);
            rs.next();
            String userPw = rs.getString("user_password");
            if(oldpw.equals(userPw))
                {
                return true;            
                }
            else{return false;}
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Cannot connect at this time.");
            System.out.println("An error has occurred: "+e.getMessage());                       
        }
        finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (con != null) con.close(); } catch (Exception e) {};            
        }
        return true;
    }
    
    public void updatePassword(int userId, String newpw){
        Connection con=null;
        ConnectDB callConnector = new ConnectDB();  
        ResultSet rs=null;
        Statement stmt=null; 
        try{
            con = callConnector.connectToDB();         
            stmt = con.createStatement();
            String sql = "UPDATE `user` SET user_password='"+newpw+"' WHERE user_id='"+userId+"';";
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (con != null) con.close(); } catch (Exception e) {};              
        } 
    }
    
    public String getUserName(int userId){
        Connection con=null;
        ConnectDB callConnector = new ConnectDB();  
        ResultSet rs=null;
        Statement stmt=null; 
        String userName="";
        try{
            con = callConnector.connectToDB();         
            stmt = con.createStatement();
            String sql = "SELECT * FROM `user` WHERE user_id='"+userId+"';";
            rs = stmt.executeQuery(sql);
            rs.next();
            userName = rs.getString("user_username");
            System.out.println(userName);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (con != null) con.close(); } catch (Exception e) {};   
            return userName;
        }         
    }
        
    public String getName(int userId){
        Connection con=null;
        ConnectDB callConnector = new ConnectDB();           
        ResultSet rs=null;
        Statement stmt=null; 
        String lName="";
        String fName="";
        String name=null;
        try{        
            con = callConnector.connectToDB(); 
            stmt = con.createStatement();
            String sql = "SELECT * FROM `user` WHERE user_id='"+userId+"';";
            rs = stmt.executeQuery(sql);
            rs.next();
            lName = rs.getString("user_lname");
            fName = rs.getString("user_fname");
            //System.out.println(fName+" "+lName);
            name=fName+" "+lName;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (con != null) con.close(); } catch (Exception e) {};   
            return name;
        }         
    }    

    public ArrayList<Integer> getUserList() throws SQLException{
        ArrayList<Integer> users = new ArrayList<Integer>();
        Connection con=null;
        ConnectDB callConnector = new ConnectDB();           
        ResultSet rs=null;
        Statement stmt=null; 
        int id;

        try{        
            con = callConnector.connectToDB(); 
            stmt = con.createStatement();
            String sql = "SELECT user_id FROM `user` WHERE user_status=1;";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                id = rs.getInt("user_id");
                users.add(id);
                }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            try { if (stmt != null) stmt.close(); } catch (Exception e) {};
            try { if (con != null) con.close(); } catch (Exception e) {};   
            return users;
        }         
    }     
}
