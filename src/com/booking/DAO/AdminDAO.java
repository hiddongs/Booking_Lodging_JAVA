package com.booking.DAO;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.booking.member.Admin;
import com.dbutil.DBUtil;

public class AdminDAO {


	public Admin checkAdmin(String ID, String passwd) {

		Connection conn = null;
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM ADMIN WHERE ID = ? AND PASSWD = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery(sql);

			if(rs.next()) {
				String email = rs.getString("EMAIL");
				String name = rs.getString("NAME");
				return new Admin(ID, passwd, email, name);
			}else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return null;
	}

	public int selectAccommodation(BufferedReader br) { // 숙소 목록을 띄우는 메서드

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT ACCOMMODATION_ID, ACCOMMODATION_NAME FROM ACCOMMODATION GROUP BY ACCOMMODATION_ID, ACCOMMODATION_NAME";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				List<Integer> idList = new ArrayList<>();
				do {
					int accommodation_id = rs.getInt("ACCOMMODATION_ID");
					idList.add(accommodation_id);
					String accommodation_name = rs.getString("ACCOMMODATION_NAME");
					System.out.printf("숙소번호 : %d , 숙소이름 : %s\n" , accommodation_id, accommodation_name);
				}while(rs.next());
				while(true) {
					try {
						int answer = Integer.parseInt(br.readLine());
						if(!idList.contains(answer)) {
							System.out.println("잘못된 입력입니다");
							continue;
						}else {
							break;
						}
					} catch (Exception e) {
						System.out.println("잘못된 입력입니다");
						continue;
					}
				}
			}else {
				System.out.println("등록된 숙소가 없습니다.");
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return -1;
	}

	public void accommodation_management(int accommodation_id, int answer) { // 1. 영업정지 2. 영업재개
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		if(answer == 1) {
			System.out.println("영업 정지 메뉴입니다");
			
		}else if(answer == 2) {
			System.out.println("영업 재개 메뉴입니다");
		}
		
	}

	public void insert_accommodation(String accommodation_name, String accommodation_address,
			String accommodation_description, String location_name, int accommodation_price,
			int accommodation_allowed_num) {
		
	}
}
