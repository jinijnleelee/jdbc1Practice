package edu.kh.jdbc11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc11.model.vo.Employee1;

public class JDBCExExample4 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//직급명,급여를 입력받아
//		해당 직급에서 입력 받은 급여보다 많이 받는 사원의
//		이름,지급명,급여,연봉을 조회하여 출력
//		단,조회결과 없으면 "조회결과없음 " 출력
//	
//		조회결과 있으면 아래와 같이 출력
//		선동일 / 대표 / 8000000 / 96000000
//		송중기 / 부장  / 6000000/ 72000000
//		//...
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.out.print("직급명 : ");
			String inputJobName = sc.next();
			
			
			System.out.print("급여 : ");
			int inputSarary = sc.nextInt();
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
				String url = "jdbc:oracle:thin:@localhost:1521:XE";
				String user = "kh";
				String pw = "kh1234";
			
				conn = DriverManager.getConnection(url,user,pw);
				
				
				String sql = "SELECT EMP_NAME,JOB_NAME,SALARY,SALARY * 12 AS ANNAUAL_INCOME "
						+ "	FROM EMPLOYEE "
						+ "	JOIN JOB USING(JOB_CODE) "
						+ "	WHERE JOB_NAME ='" + inputJobName + "'"
						+ "	AND SALARY > " + inputSarary;
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				List<Employee1> list = new ArrayList<>();
				
				while(rs.next()) {
					
					String empName = rs.getString("EMP_NAME");
					String jobName = rs.getString("JOB_NAME");
					int salary = rs.getInt("SALARY");
					int annualIncome = rs.getInt("ANNAUAL_INCOME");
					
					
					list.add(  new Employee1(empName, jobName, salary, annualIncome));
					
						
					
		
				}
				
				
				if(list.isEmpty()) { // list가 비어있을경우 
					//비어있으면 true 
					System.out.println("조회결과없음 ");
				}else {
					//향상된 for
					for(Employee1 eemp : list)
						System.out.println(eemp);
				}
				
				

			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(rs != null)rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
				
			}
		}
		
		
	}
}
