import java.sql.*;
import java.io.*;
public class GetConnection
 {
     private String drivers="com.mysql.jdbc.Driver";
     private String host;
     private String user,sqlstatecode;
     private String password;
     private Connection connmysql;
     private Statement stmt;
     private String databasename;
     private int errorcode;
     public GetConnection(String ahost,String databasename,String auser,String apassword)
            {
                this.host="jdbc:mysql://"+ahost+":3306/"+databasename+"?useSSL=false";
		this.user=auser;
		this.password=apassword;
             };
     public Connection connect()
            throws SQLException,IOException ,ClassNotFoundException
            {  
              // try{
                Class.forName(drivers);
                connmysql= DriverManager.getConnection(host,user,password);
               // return connmysql;
               //  }
             /*  catch (SQLException sqle)
                {
                   errorcode=sqle.getErrorCode();
                   sqlstatecode=sqle.getSQLState();
                 } */
                return connmysql;
             } 
 }
