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
				
				Employee1 eemp = new Employee1();
				
				
				eemp.setEmpName(rs.getString("EMP_NAME"));
				eemp.setHireDate( rs.getString("HIRE_DATE"));
				// 현재행의 "입사일" 컬럼의 문자열 값을 얻어와 
				//eemp가 참조하는 객체의 hireDate필드에 세팅
				eemp.setGender(rs.getString("GENDER").charAt(0));
				//-> char 자료형 매개변수 필요
				//"중요 "
				//java의 char 문자 1개 의미
				//DB 칼럼값을 char 자료 
				//String.charAt(index) 이용!
				
				//list 에 eemp 객체 추가 
				list.add(eemp);
				
				
				
				
				
			}
			
		if(list.size() == 0) {
			System.out.println("조회결과없습니다");
		}else {
			
			for(int i = 0; i<list.size();i++) {
				System.out.printf("%02d) %s / %s / %s\n"
						,i+1,
						list.get(i).getEmpName(),
						//List.get(index) 
						// list의 순서를 볼수 있다.
						//list.get(0)하면 0번째 인덱스인 empNAme을 가져올수 있고 
						//list.get(1)하면 1번째 인덱스인 HireDate을 가져올수 있음
						
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
