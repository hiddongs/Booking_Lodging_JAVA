package com.booking.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.booking.member.Grade;
import com.booking.member.Member;
import com.dbutil.DBUtil;

public class UserDAO {

	public Member login(String ID, String passwd) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM USER WHERE USER_ID = ? AND PASSWORD = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();
			

			if(rs.next()) {
				// 회원 ID는 하나이기 떄문에 do-while 사용 X
				String name = rs.getString("NAME");
				String email = rs.getString("EMAIL");
				int point = rs.getInt("POINT");
				int cash = rs.getInt("CASH");
				Date reg_date = rs.getDate("REG_DATE");
				boolean status = rs.getInt("STATUS") == 0 ? false : true;
				Enum grade = Grade.valueOf(rs.getString("LEVEL_ID"));
				return new Member(name, email, point, cash, reg_date, status, grade);
			}else {
				System.out.println("잘못된 ID나 잘못된 passwd입니다");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return null;
	}



	public static boolean checkIDDuplicate(String ID) { // 증복 아이디 확인
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs =  null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(ID) FROM MEMBER WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return false;
	}

	public boolean register(String ID, String passwd, String name, String email) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {	
			conn = DBUtil.getConnection();
			sql = "INSERT INTO MEMBER (ID,PASSWORD, NAME, EMAIL) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int count = pstmt.executeUpdate(sql);
			return count == 1 ? true :false; //false 는 ID로 인한 이유가 아닌 다른 이유로 발생할것(한번 중복을 걸렀기때문)
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return false;
	}

}
