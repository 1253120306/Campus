package db;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBconnection {
 static Connection conn=null;
 static String driver="com.mysql.jdbc.Driver"; 
 static String url="jdbc:mysql://localhost:3306/classmate_record"; 
 static String user="root"; 
 static String password="123456";
 public static Connection getConnector()
 {
 try { 
     Class.forName(driver); 
     //System.out.println("����������سɹ�"); 
 } catch (ClassNotFoundException e) { 
     e.printStackTrace(); 
 } 
 try { 
     conn=(Connection) DriverManager.getConnection(url, user, password); 
//     if (conn!=null) { 
//         System.out.println("�������ݿ�ɹ�"); 
//     } 
 } catch (SQLException e) { 
     System.out.println("�������ݿ�ʧ��: "+e.getMessage()); 
 }
     return conn;	
 }	
 
}

