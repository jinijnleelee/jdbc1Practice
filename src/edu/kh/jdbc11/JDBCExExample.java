package edu.kh.jdbc11;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleDriver;

public class JDBCExExample {
	public static void main(String[] args) {

	//jdbc : java에서 db에 연결(접근)할 수 있게 해주는 java Programming API
	//											(java에 기본 내장된 인터페이스)
	
	// java.sql 패키지에서 제공
	
	//* JDBC를 이용한 애플리케이션을 만들 때 필요한 것
	// 1. java의 jdbc 관련 인터페이스
	// 2. DBMS(ORACLE)
	// 3. oracle에서 Java 애플리케이션과 연결할때 사용할 
	// JDBC를 상속받아 구현한 클래스 모음(ojdbc11.jar 라이브러리)
	// --> OracleDriver.class (JDBC 드라이버 ) 이용 
	
	//1단계 : JDBC 객체 참조 변수 선언(java.sql 패키지의 인터페이스)

	Connection conn = null;
	// DB 연결 정보를 담은 객체
	//-> DBMS 타입, 이름, IP, Port, 계정명, 비밀번호 저장
	//-> DBeaver 계정 접속 방법과 유사함 
	// * DB와 Java 사이를 연결해주는 통로 (Stream과 유사함)
	
	Statement stmt = null;
	//Connection 을 통해
	//SQL 문을 DB에 전달하여 실행하고
	//생성된 결과(ResultSet, 성공한 행의 개수)를 반환(Java)하는데 사용되는 객체
	
	
	ResultSet rs = null;
	//SELECT 질의 성공 시 반환되는데
	//조회 결과 집합을 나타내는 객체
	
	
	try {
		// 2단계 : 참조 변수에 알맞은 객체 대입
		
		// 1. DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
		//-> 객체로 만들어 두기
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//				(패키지명 + 클래스명)
		// -> () 안에 작성된 클래스의 객체를 반환
		// -> 메모리에 객체가 생성되고, JDBC 필요시 알아서 참조해서 사용
		// -> 생략해도 자동으로 메모리 로드가 진행됨(명시적으로 작성하는걸 권장)
		
		// 2. 연결 정보를 담은 Connection을 생성
		// -> DriverManager객체를 이용해서 Connection 객체를 만들어 얻어옴!
		
		String type = "jdbc:oracle:thin:@"; // JDBC 드라이버의 종류
		String ip = "localhost"; //  DB 서버 컴퓨터 IP
		// localhost == 127.0.0.1(loop back ip)
		
		String port = ":1521"; //포트번호 1521(기본값)
		
		String sid = ":XE"; //DB 이름
				
		String user = "kh";
		
		String pw = "kh1234";
		
		//DriverManager :
		// 메모리에 로드된 JDBC 드라이버를 이용해서
		// Connection 객체를 만드는 역할
		
		conn = DriverManager.getConnection(type + ip +  port + sid, user, pw);
		
		//중간 확인 

		System.out.println(conn);
		
		
		// 3. SQL 작성
		// **JAVA에서 작성되는 SQL은 마지막에 ;(세미콜론) 찍지 않기! **
		String sql = "SELECT EMP_ID, EMP_NAME, SALARY , HIRE_DATE "
				+ " FROM EMPLOYEE";
		
		//4. Statement 객체 생성
		// -> Connection 객체를 통해서 생성
		stmt = conn.createStatement();
		
		
		// 5. 생성된 Statement 객체에 
		// sql을 적재하여 실행한 후 
		// 결과를 반환 받아와서
		// rs 변수에 저장
		rs = stmt.executeQuery(sql);
		
		// executeQuery() : SELECT문 수행 메서드, ResultSet반환
		
		
		// 3단계 : SQL을 수행해서 반환 받은 결과(ResultSet)를 
		//				 	한행씩 접근해서 컬럼 값 얻어오기 
		
		while(rs.next()) {
			//rs.next() : rs가 참조하는 ResultSet 객체의
			//			첫번째 컬럼부터 순서대로 한 행씩 이동하며
			//			다음 행이 있을 경우 true 반환 / 없으면 false 반환
			
			//rs.get자료형("컬럼명")
			String empId = rs.getString("EMP_ID");
			// 현재 행의 "EMP_ID" 문자열 컬럼의 값을 얻어옴
			
			String empName = rs.getString("EMP_NAME");
			//현재 행의 "EMP_NAME" 문자열 컬럼의 값을 얻어옴
			
			int salary = rs.getInt("SALARY");
			//현재 행의 "SALARY" 숫자(정수) 컬럼의 값을 얻어옴
			
			Date hireDate = rs.getDate("HIRE_DATE");
			// -> java.sql.DAte
			// -> java.util.Date도 가능하긴 함
			// java.sql.Date의 toString()은 yyyy-mm-dd 형식으로 오버라이딩 되어있음.
					
			
			System.out.printf("사번 :%s / 이름 : %s / 급여 : %d / 입사일 : %s\n",
					empId, empName,salary,hireDate.toString());
					
					
					
					
								
					
					
				}
										
				
				
				
			}catch(ClassNotFoundException e) {
				System.out.println("JDBC 드라이버 경로가 잘못 작성되었습니");
				e.printStackTrace();
				
				
				
			}catch(SQLException e) {
				//SQLException : DB 관려뉴 최상위 예외 (== 데이터베이스에 관련된 모든 예)
				e.printStackTrace();
			
			
			}finally {
				//4.사용한 JDBC 객체 자원 반환( close() )
				//ResultSet,Statement, Connection 닫기 (생성 역순으로 닫는 것을 권장 )
				try{
					if( rs != null) rs.close();
					if(stmt != null) rs.close();
					if(conn != null) conn.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			
	
	
	
	
	}	

}
