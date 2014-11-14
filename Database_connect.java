import java.sql.Connection;
import java.sql.DriverManager;


public class Database_connect
{
	
   public static void main(String args[])
    {
	    
    }
   
   public void estab_connect()
    {
    	try
		{
		  Class.forName("com.mysql.jdbc.Driver");
		  con= DriverManager.getConnection("jdbc:mysql://localhost:3306/score","root","password");
		  
		}
		
		   catch(Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		   
    }
   public Connection con;
}