import java.sql.*;
import java.io.*;
public class GetConnection
 {
     private String drivers="com.mysql.jdbc.Driver";
     private String host;
     private String user;
     private String password;
     private Connection connmysql;
     private Statement stmt;
     private String databasename;
     public GetConnection(String ahost,String databasename,String auser,String apassword)
            {
                this.host="jdbc:mysql://"+ahost+":3306/"+databasename;
		this.user=auser;
		this.password=apassword;
             };
     public Connection connect()
            throws SQLException,IOException ,ClassNotFoundException
            {
                Class.forName(drivers);
                connmysql= DriverManager.getConnection(host,user,password);
                return connmysql;
             } 
 }
