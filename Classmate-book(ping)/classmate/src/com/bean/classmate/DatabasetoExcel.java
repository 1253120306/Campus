package com.bean.classmate;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
 
public class DatabasetoExcel {
	
	private static final int[] colWidth={2000,2000,5000,5000,
			5000,5000,5000,5000};
	
	//文件路径
	private static String outPutFile="."+File.separator+"user.xls";
	//数据库连接参数
	private static String url="jdbc:mysql://localhost:3306/classmate_record";
	private static String user="root";
	private static String password="123456";

	public static void work() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//连接数据库查询数据
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(url, user, password);
			String sql="select * from t_user";
			ps=conn.prepareStatement(sql);
			//获取结果集
			rs = ps.executeQuery();
			//用于获取字段的描述信息,比如字段名
			ResultSetMetaData metaData = rs.getMetaData();
			//创建workBook对象
			HSSFWorkbook workBook=new HSSFWorkbook();
			//在workBook对象中创建一张表格
			HSSFSheet sheet= workBook.createSheet("classmate");
			//设置每一列的宽度
			for(int i=0;i<colWidth.length;i++){
				sheet.setColumnWidth(i, colWidth[i]);
			}
			//单元格样式对象
			HSSFCellStyle cellStyle = workBook.createCellStyle();
			//设置文本居中
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			//创建第0行,作为表格的表头
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=null;
			for(int i=0;i<colWidth.length;i++){
				cell=row.createCell(i);
				//动态获取字段名
				cell.setCellValue(metaData.getColumnLabel(i+1));
				cell.setCellStyle(cellStyle);
			}
			int rowIndex=1;
			while(rs.next()){
				//循环将查询出来的数据封装到表格的一行中
				row=sheet.createRow(rowIndex);
				for(int i=0;i<colWidth.length;i++){
					cell=row.createCell(i);
					cell.setCellValue(rs.getString(i+1));
					cell.setCellStyle(cellStyle);
				}
				rowIndex++;
			}
			FileOutputStream fos=new FileOutputStream(outPutFile);
			//输出流将文件写到硬盘
			workBook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

}