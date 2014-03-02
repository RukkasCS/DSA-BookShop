/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

/**
 *
 * @author malinda
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DbUtil 
{
    	private static final String USERNAME="malinda";
	private static final String PASSWORD="malinda123";
	/*private static final String USERNAME="dbuser";
	private static final String PASSWORD="dbpassword";*/
	//private static final String CONNATRING="jdbc:mysql://localhost/BookShop?useUnicode=true&amp;characterEncoding=UTF-8";
	private static final String CONNATRING="jdbc:mysql://localhost:3306/bookshop?zeroDateTimeBehavior=convertToNull";
	private static final String CONNATRING1="jdbc:hsqldb:data/BookShop";
	

	
	
	public static Connection getConnection(DbType db) throws SQLException, ClassNotFoundException
	{
		switch (db) 
		{
		case MySQL:
			return DriverManager.getConnection(CONNATRING, USERNAME, PASSWORD);		

		case Access:
			String driver ="sun.jdbc.odbc.JdbcOdbcDriver";
			Class.forName(driver);
			String database="jdbc:odbc:BookShop";
			return DriverManager.getConnection(database);
			
		case HSQL:
			return DriverManager.getConnection(CONNATRING1, USERNAME, PASSWORD);	
		default:
			return null;
		
		}
	} 
        public static ArrayList<Node> getSimilarNodes(Node node) throws Exception 
        {
            ArrayList<Node> nodeList=new ArrayList<>();
            try 
		(
				java.sql.Connection conn= DbUtil.getConnection(DbType.MySQL);
				
                )
		{
                       // java.sql.Connection conn= this.getConnection(DbType.MySQL);
				java.sql.PreparedStatement stmt=conn.prepareStatement("SELECT * FROM BOOK WHERE TITLE LIKE ?");
                                stmt.setString(1,node.title +"%" );
				ResultSet rs=stmt.executeQuery();	
			while(rs.next())
                        {
                            Node node1=new Node();
                            
                            node1.setIsbn(rs.getInt("ISBN"));
                            node1.setTitle(rs.getString("TITLE"));
                            node1.setAuthorName(rs.getString("AUTHORNAME"));
                            node1.setAuthorSurname(rs.getString("AUTHORSURNAME"));
                            
                            nodeList.add(node1);
                        }	
		} 
		catch (Exception e) 
		{
			throw  e;
		}
            return nodeList;
        }
	
	public static boolean insertValuesToDB(Node node) throws ClassNotFoundException, SQLException
	{
		String sql="INSERT INTO BOOK (ISBN,TITLE,AUTHORNAME,AUTHORSURNAME)"+
	    "VALUES(?,?,?,?)";
		Connection conn=DbUtil.getConnection(DbType.MySQL);
		java.sql.PreparedStatement stmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setLong(1, node.getIsbn());
		stmt.setString(2, node.title);
		stmt.setString(3, node.getAuthorName());
		stmt.setString(4, node.getAuthorSurname());
		int value=stmt.executeUpdate();
                return value != 0;
		
	}
	
	public static boolean DeleteNodeFromDB(Node node) throws ClassNotFoundException, SQLException
	{Connection conn=DbUtil.getConnection(DbType.MySQL);
		if (node.title==null) 
		{
			String sql="DELETE FROM BOOK WHERE ISBN=?";
			java.sql.PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, node.isbn);
			if (stmt.executeUpdate()!=0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			String sql="DELETE FROM BOOK WHERE TITLE=?";
			java.sql.PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, node.title);
			if (stmt.executeUpdate()!=0)
			{
				return true;
			}
			else
			{
				return false;
			}
	

	}
}
}
