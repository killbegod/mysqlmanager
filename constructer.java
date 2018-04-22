import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
public class constructer
{
  JFrame jfcon = new JFrame("EPC-DATABASE");
  //jfcon.setSize(100,200);
  JDialog jfshuru= new JDialog(jfcon,"database",true);
  JMenuBar jmb = new JMenuBar();
  JMenu database = new JMenu("Database");
  JButton find = new JButton("find");
  JToolBar jtb= new JToolBar();
  JTextField findtext= new JTextField(15);
  JMenu view = new JMenu ("view");
  ButtonGroup kok= new ButtonGroup();
  //styl.setLayout(new GridLayout(4,1));
  JCheckBox metalItem= new JCheckBox("Metal",true);
  JCheckBox nimbusItem= new JCheckBox("Nimbus");
  JCheckBox windowsItem= new JCheckBox("Windows");
  JCheckBox classicItem= new JCheckBox("Classic");
  JCheckBox motifiItem= new JCheckBox("Motifi");

  private static DefaultMutableTreeNode top= new DefaultMutableTreeNode("DATABASES");
 // private DefaultTreeModel dtm=new DefaultTreeModel(top);
  private static JTree databasetree=new JTree(top);
  //JMenuItem connect = new JMenuItem("connect...");
//  JMenuItem disconnect = new JMenuItem("disconnect..."); 
   private JScrollPane left=new JScrollPane(new JTextArea());
   private JScrollPane right=new JScrollPane(new JTable(20,10));
   private JSplitPane boot=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,left,right);
   private JPanel shuru= new JPanel();
   private JLabel  hostl=new JLabel("host:");
   private JLabel  namel=new JLabel("name:");
   private  JLabel  passwordl= new JLabel("password:");
   private static JPasswordField password=new JPasswordField(10);
   private static  JTextField name=new JTextField(10);
   private static JTextField host=new JTextField(10);
            JButton connectbn=new JButton("connect");
            JButton cancelbn=new JButton("disconnect");
   private static ResultSet rst;
   private ResultSetTableModel rstml;
   private Connection connmysql;
   private Statement stmt;
   private DatabaseMetaData dmd;
   private static String hostt,namet,passwordt;
  public void init()
  {
     //shuru.setSize(100,200);
     left.add(databasetree);
     boot.setDividerLocation(200);
     shuru.setLayout(new GridLayout(4,2));
     shuru.add(hostl);
     shuru.add(host);
     shuru.add(namel);
     shuru.add(name);
     shuru.add(passwordl);
     shuru.add(password);
     shuru.add(connectbn);
     shuru.add(cancelbn);
     jfshuru.add(shuru);
  jtb.setFloatable(false);
  jtb.setLayout(null);
  find.setBounds(15,0,50,21);
  findtext.setBounds(65,0,115,21);
  JScrollPane center= new JScrollPane(20,30);
  JTable context=new JTable(10,5);
  center.add(context);
  //mslb.add(connect);
  jmb.add(database);
  view.add(metalItem);
  view.add(nimbusItem);
  view.add(windowsItem);
  view.add(classicItem);
  view.add(motifiItem);
  kok.add(metalItem);
  kok.add(nimbusItem);
  kok.add(windowsItem);
  kok.add(classicItem);
  kok.add(motifiItem);
  jmb.add(view);
  jtb.add(find);
  jtb.add(findtext);
  jmb.add(jtb);
  database.addMouseListener(new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
           {
              if(e.getButton()==MouseEvent.BUTTON1)  
               {
                jfshuru.pack();    
                jfshuru.setVisible(true);
                jfshuru.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
             }
              }
     }
         );
   connectbn.addActionListener(new ActionListener()
     {
        public void actionPerformed(ActionEvent e)
           { 
           try
            {
			constructer.hostt=host.getText();
                        constructer.namet=name.getText();
                        constructer.passwordt=String.valueOf(password.getPassword());
                        //System.out.println(passwordt);  
                        connmysql=new GetConnection(hostt,"information_schema",namet,passwordt).connect();
		       // dmd=connmysql.getMetaData();
                       // stmt=connmysql.createStatement();
                       top=new PutInTree(connmysql,"SCHEMA_NAME","SCHEMATA",top).gettreenode(); 
                        left.setViewportView(databasetree);
                        host.setText(null);
                        name.setText(null);
                        password.setText(null);
                        jfshuru.setVisible(false);
                   
			}
          catch(SQLException l)
              {
                if(1045==l.getErrorCode())
                  {
                  JOptionPane.showMessageDialog(jfshuru, "用户名或密码错误");
                 name.setText(null);
                 password.setText(null);
                 // l.printStackTrace();
                 return;
                  }
              }
			  catch(IOException io)
			   {
				  System.out.println("dodldo");
			   }
			 catch(ClassNotFoundException cf)
			   {
					  cf.getMessage();
					  System.out.println("++++++++++++"+"\br");
					  cf.printStackTrace();
			   }
                     /* String query= "select * from user";
            rst=stmt.executeQuery(query);*/
           
             
            } }  
             );
  //jfcon.add(jtb,BorderLayout.NORTH);
  databasetree.addMouseListener(new MouseAdapter()
     {
      public void mouseClicked(MouseEvent e)
      {
       TreePath selPath=constructer.databasetree.getSelectionPath();
       DefaultMutableTreeNode selNode=(DefaultMutableTreeNode)selPath.getLastPathComponent();
       TreeNode[] nodearray=selNode.getPath();
       int an=nodearray.length;
      // System.out.println(an);
       if(e.getClickCount()==2 && nodearray[an-1].isLeaf() && an==3)
       {
       try
       { 
        connmysql=new GetConnection(hostt,nodearray[an-2].toString(),namet,passwordt).connect();
         String sqli="select * from "+selNode.toString();
         stmt=connmysql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
          rst=stmt.executeQuery(sqli);
        rstml=new ResultSetTableModel(rst);
	}
        catch(SQLException l)
              {
                System.out.println("database2 connect fail");
                l.printStackTrace();
              }
			  catch(IOException io)
			   {
				  System.out.println("IO is fail");
			   }
			 catch(ClassNotFoundException cf)
			   {
					  cf.getMessage();
					  System.out.println("+++++classnotfount+++++++"+"\br");
					  cf.printStackTrace();
			   }        
        JTable  context=new JTable(rstml);
        right.setViewportView(context);
       }
      }
     });
  jfcon.setJMenuBar(jmb);
  jfcon.add(boot);
  jfcon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  jfcon.pack();
  jfcon.setVisible(true); 
  }
  public static void main(String[] args)
  {
     new constructer().init();
  }
}
