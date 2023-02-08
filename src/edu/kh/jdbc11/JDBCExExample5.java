package edu.kh.jdbc11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc11.model.vo.Eemp;
import edu.kh.jdbc11.model.vo.Employee1;

public class JDBCExExample5 {

	public static void main(String[] args) {
		//입사일을 입력("2022-09-06") 받아
		//입력 받은 값보다 먼저 입사한 사람의
		// 이름, 입사일,성별(m,F)조회
		
		Scanner sc = new Scanner(System.in);
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		try {
			
			System.out.println("입사일 : ");
			String inputHireDate = sc.next();
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
					
					String url = "jdbc:oracle:thin:@localhost:1521:XE";
					String user = "kh";
					String pw = "kh1234";
			
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_NAME,TO_CHAR(HIRE_DATE,'YYYY\"년\" MM\"월\" DD\"일\"') HIRE_DATE,DECODE(SUBSTR(EMP_NO,8,1),1,'M',2,'F') GENDER"
					+ " FROM EMPLOYEE "
					+ " WHERE HIRE_DATE < TO_CHAR('"+ inputHireDate +"')";
					
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			List<Employee1> list = new ArrayList<>();
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("HIRE_DATE");
				String gender = rs.getString("GENDER");
				
				list.add(new Employee1(empName,hireDate,gender));
				
				
				
				
				
			}
			
		if(list.size() == 0) {
			System.out.println("조회결과없습니다");
		}else {
			
			for(int i = 0; i<list.size();i++) {
				System.out.printf("%02d) %s / %s / %s\n"
						,i+1,
						list.get(i).getEmpName(),
						list.get(i).getHireDate(),
						list.get(i).getGender());
			}
		}
			
			
			
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(rs != null )rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
				
				
			}catch(SQLException e) {
				e.printStackTrace();
				
			}
		}
		
		
	}

}
