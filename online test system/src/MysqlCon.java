import java.sql.*;  
class MysqlCon{ 

Connection conn;

void connect()
{
	try{ 

	Class.forName("com.mysql.jdbc.Driver");  
	conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/saumya?autoReconnect=true&useSSL=false","root","Shaurya@123");  
	//here sonoo is database name, root is username and password  
	}
	catch(Exception e)
	{
	}
}
void print()
{
	try{
		
	Statement stmt=conn.createStatement();  
	ResultSet rs=stmt.executeQuery("select * from info"); 
 
	while(rs.next())  
		System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
	conn.close();  
	}
	catch(Exception e)
	{ 
		System.out.println(e);
	}  
}
void put_new(String user,String pass)
{
	try{
		String query = " insert into info "
		        + " values (?, ?, ?)";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = conn.prepareStatement(query);
		      preparedStmt.setString (1, "shaurya is god");
		      preparedStmt.setString (2, user);
		      preparedStmt.setString (3, pass);
		      // execute the preparedstatement
		      preparedStmt.execute();
	/*Statement statement = conn.createStatement();
	//String sql1="INSERT INTO info VALUES ( 'shaurya' , 'fdfsd', 'sfsf');";
	ResultSet rs=statement.executeQuery("select * from info");
	while(rs.next())  
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
	conn.close();
	//statement.executeUpdate(sql1);*/
	}
	catch(Exception e){
		System.out.print(e.toString());
	}
}
boolean check_old(String username,String password)
{
	boolean b=false;
	String res;
	int count=0;
	try{
		
		PreparedStatement statement = conn.prepareStatement("select username from info where username = ? and password = ?");
		statement.setString(1, username); 
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) 
		{
			res=resultSet.getString(1);
			if(res.length()>0)
				count++;
		}
		conn.close();
		if(count==1)
			b=true;
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}
	return b;  
}
String get_score(int no,String username,String password)
{
	String ans="";
	try{
		
		PreparedStatement statement = conn.prepareStatement("select ? from info where username = ? and password = ?");
		statement.setString(1, "score"+no);
		statement.setString(2, username); 
		statement.setString(3, password);
		ResultSet rs = statement.executeQuery(); 
		
		while(rs.next())  
			ans=rs.getString(1);  
		conn.close();  
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}  
	return ans;
}
void put_score(int test,int score,String username,String password)
{
	try
	{
		PreparedStatement statement = conn.prepareStatement("update info set ? = ? where username = ? and password = ?");
		statement.setString(1, "score"+test);
		statement.setString(2, Integer.toString(score));
		statement.setString(3, username); 
		statement.setString(4, password);
		ResultSet rs = statement.executeQuery();
	}catch(Exception e)
	{
		
	}
}
}