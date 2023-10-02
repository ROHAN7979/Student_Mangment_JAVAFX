package Student;

import java.sql.Connection;
import java.sql.*;

public class CP {
static Connection con;
public static Connection createC()
{
	try {
		//load the driver
		Class.forName("com.mysql.jdbc.Driver");
		
		//creating the connection..
		String user="root";
		String password="rohan@1515";
		String url="jdbc:mysql://localhost:3306/student_manage";
		con=DriverManager.getConnection(url, user, password);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return con;
}

}
