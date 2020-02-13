/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import credentials.KeyGeneration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Dbconnect {
Connection con;
ResultSet rs;
    public Dbconnect() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smarthome", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public int addDevice(String dname,int port)
   {
       String sql="insert into agent(device_name,device_port) values(?,?)";
       PreparedStatement ps;
       int i=0;
    try {
        ps = con.prepareStatement(sql);
         ps.setString(1, dname);
       ps.setInt(2, port);
       i=ps.executeUpdate();
       
       if(i>0)
       {
           KeyGeneration key=new KeyGeneration();
           key.getPubKey();
           
           int devid=0;
           sql="select max(device_id) from agent";
           ps=con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
               devid= rs.getInt(1);
            }
            sql="insert into device_credentials(device_id,private_key,public_key) values(?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setInt(1, devid);
            ps.setString(2,String.valueOf(key.privKey));
            ps.setString(3, String.valueOf(key.getPubKey()));
            i=ps.executeUpdate();
            return 01;
       }
    } catch (SQLException ex) {
        Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
    }
      return i;
   }
   
   public int removeDevice(int device_id)
   {
       String sql="delete from agent where device_id=?";
       PreparedStatement ps;
       int i=0;
    try {
        ps = con.prepareStatement(sql);
         ps.setInt(1, device_id);
      
       i=ps.executeUpdate();
    }
   catch (SQLException ex) {
        Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
    }
    return i;
   }
   public ResultSet selectData(){
       
      String sql="select * from agent";
       PreparedStatement ps;
       
        try {
        ps = con.prepareStatement(sql);
        
       rs=ps.executeQuery();
//       while(rs.next()){
//           System.out.println("device name:"+rs.getString(2));
//       }
       
       
    } catch (SQLException ex) {
        Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
    }
      return rs;
   }
}
