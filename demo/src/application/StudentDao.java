package application;

import java.sql.Connection;
import java.sql.*;
public class StudentDao {
 public static boolean  insertStudnetToDB(Student st)
{	boolean f=false;
	//jdbc code...
	 try {
		 Connection con=CP.createC();
		 String q="insert into students(sname,sphone,scity) values(?,?,?)";
		 
		 //prepared Statement
		 PreparedStatement pstmt=con.prepareStatement(q);
		 
		 //set the values of parameters
		 pstmt.setString(1, st.getStudentName());
		 pstmt.setString(2, st.getStudentPhone());
		 pstmt.setString(3, st.getStudentCity());
		 
		 
		 //execute
		 pstmt.executeUpdate();
		 f=true;
	} catch (Exception e) {
		// TODO: handle exception
	}
return f;
}

public static boolean deleteStudent(int userId) {
	// TODO Auto-generated method stub
	boolean f=false;
	//jdbc code...
	 try {
		 Connection con=CP.createC();
		 String q="delete from students where sid=?";
		 //prepared Statement
		 PreparedStatement pstmt=con.prepareStatement(q);
		 
		 //set the values of parameters
		 pstmt.setInt(1,userId);
		
		 
		 
		 //execute
		 pstmt.executeUpdate();
		 f=true;
	} catch (Exception e) {
		// TODO: handle exception
	}
return f;
}

public static boolean DisplayAll() {
	boolean f=false;
	//jdbc code...
	 try {
		 Connection con=CP.createC();
		 String q="select *from students;";
		 Statement stmt=con.createStatement();
		 
		 ResultSet set=stmt.executeQuery(q);
		 
		 while(set.next())
		 {
			 int id=set.getInt(1);
			 String name=set.getString(2);
			 String phone=set.getString(3);
			 String city=set.getString("scity");
			 
			 System.out.println("ID : "+id);
			 System.out.println("Name : "+name);
			 System.out.println("Phone : "+phone);
			 System.out.println("City: "+city);
			 System.out.println("+++++++++++++++++++++++++++++++++++");
			 

		 }
		 f=true;
	} catch (Exception e) {
		// TODO: handle exception
	}
return f;
	
}

}
