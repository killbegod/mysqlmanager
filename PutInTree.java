import javax.swing.tree.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
public class PutInTree
{
     private String tablename,sql,sql2,database;
     private Connection subconnmysql;
     private DefaultMutableTreeNode treenode;
     private ResultSet rst,rst2;
     private Statement statement;
     private  List<DefaultMutableTreeNode>  databases=new ArrayList<DefaultMutableTreeNode>();
     public PutInTree(Connection connmysql,String columnname,String tablename,DefaultMutableTreeNode rootnode)
            {
                  this.subconnmysql=connmysql;
                  this.tablename=tablename;
                  this.sql="select "+columnname+" from "+tablename;
                  this.treenode=rootnode;
             }
     public DefaultMutableTreeNode gettreenode()
            throws SQLException
            {
                  statement=subconnmysql.createStatement();
                  rst=statement.executeQuery(sql);
                  int k=0;
                  while(rst.next())
                  {
                     databases.add(new DefaultMutableTreeNode(rst.getString(1)));
                    // treenode.add(new DefaultMutableTreeNode(rst.getString(1)));
                     k++;
                  };
                  for(int i=0;i<k;i++)
                  {
                     database=databases.get(i).toString();
                     sql2="select table_name from information_schema.tables where table_schema="+"\""+database+"\"";
                     rst2=statement.executeQuery(sql2);
                     while(rst2.next())
                     {
                       databases.get(i).add(new DefaultMutableTreeNode(rst2.getString(1)));
                     };
                     treenode.add(databases.get(i));
                   }
                   return treenode;
              }
 }    
                                  
