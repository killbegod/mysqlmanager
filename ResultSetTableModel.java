import java.sql.*;
import javax.swing.table.*;
public class ResultSetTableModel extends AbstractTableModel
{
   private ResultSet rs;
   private ResultSetMetaData rsmd;
   public ResultSetTableModel(ResultSet aResultSet)
	{
            this.rs=aResultSet;
          try
          {
            this.rsmd=rs.getMetaData();
          }
          catch(SQLException e)
         {
          e.printStackTrace();
         }
        }
   public String getColumnName(int c)
       {
         try
         {
           return rsmd.getColumnName(c+1);
         }
         catch(SQLException e)
         {
           e.printStackTrace();
           return "";
         }
       }
    public int getColumnCount()
       {
         try
         {
          return rsmd.getColumnCount();
         }
         catch(SQLException e)
         {
           e.printStackTrace();
           return 0;
         }
       }
    public Object getValueAt(int r,int c)
      {
        try
        {
          rs.absolute(r+1);
          return rs.getObject(c+1);
        }
        catch(SQLException e)
        {
         e.printStackTrace();
         return null;
        }
      }
    public int getRowCount()
     {
       try
       {
        rs.last();
        return rs.getRow();
       }
       catch(SQLException e)
       {
        e.printStackTrace();
        return 0;
       }
     }
   public boolean isCellEditable(int rowIndex,int column)
     {
       return true;
     }
   public void setValueAt(Object aValue,int row,int column)
     {
       try
       {
        rs.absolute(row+1);
        rs.updateObject(column+1,aValue);
        rs.updateRow();
        fireTableCellUpdated(row,column);
       }
       catch(SQLException evt)
       {
        evt.printStackTrace();
       }
     }
}
            
