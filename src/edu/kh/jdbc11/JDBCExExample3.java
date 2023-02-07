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

public class JDBCExExample3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//부서명을 입력받아 같은 부서에 있는 사원의
		//사원명,부서명 급여 조회 
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.out.println("부서명 : ");
			String input = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String sid = ":XE";
			String port = ":1521";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type + ip + port + sid , user, pw);
			
			String sql = "SELECT EMP_NAME,NVL(DEPT_TITLE,'부서없음')AS DEPT_TITLE,SALARY "
					+ " FROM EMPLOYEE "
					+ " JOIN DEPARTMENT ON(DEPT_ID = DEPT_CODE) "
					+ " WHERE NVL(DEPT_TITLE,'부서없음') = '" + input + "'";
			
			//중요!
			//java에서 작성되는 sql에서
			//문자열()String변수를 추가(이어쓰기) 할 경우
			//'' (db 문자열 리터럴)이 누락되지 않도록 신경쓸것 
			
			//만약 '' 미작성시 String 값은 컬럼명으로 인식되어 
			// 한 식별자 오류가 발생한다.
			
			//Statement 객체를 이용하여 
			//위에서 만들었던 sql구문을  
			//executeQuery통해서 db에 전달하여 실행한 후에 
			//ResultSet 반환 받아서 돌아온 후  rs에다가 대입
			
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			List<Eemp> list = new ArrayList<>();
			
			//조회결과 (rs)를 List 에 롬겨닮기 
			
			while(rs.next()) { //다음행으로 이동해서 해당 행에 데이터가 있으면 true	 반환
				//ResultSet에 담았던 애들을 하나씩 while문 안에서 저장한 다음
				//Eemp 객체로 만들어줌 
				//그 친구들을 List에 넣는다.
				
				//현재 행에 존재하는 컬럼값 받아오기 
				String  empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				//Eemp 객체를 생성하여 컬럼 값 담기
				Eemp eemp= new Eemp(empName,deptTitle,salary); 
						
				//생성된 Eemp 객체를 List추가 
				list.add(eemp);
			}
			
			//List에 추가된 Eemp객체가 만약 없다면 "조회결과없습니다 "
			//있다면 순차적 출력 
			
			if(list.isEmpty()) { //List가 비어있을경우 
				System.out.println("조회결과가 없습니다 ");
				
			}else {
				
				//향상된 for문
				for(Eemp eemp : list)
					System.out.println(eemp);
			}
			
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null)rs.close();
				if(stmt != null)stmt.close();
				if(conn != null)conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
				
			}
		}
		
	}

}
