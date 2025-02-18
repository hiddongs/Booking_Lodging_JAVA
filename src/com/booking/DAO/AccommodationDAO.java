package com.booking.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.booking.member.Admin;
import com.dbutil.DBUtil;

public class AccommodationDAO {

	static AdminDAO adminDAO;


	public int selectAccommodation(BufferedReader br) { // 숙소 목록을 띄우는 메서드

		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		int answer = Integer.MIN_VALUE;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT ACCOMMODATION_ID, ACCOMMODATION_NAME FROM ACCOMMODATION ORDER BY ACCOMMODATION_ID";
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
						answer = Integer.parseInt(br.readLine());
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
		return answer;
	}

	public void accommodation_suspension(int accommodation_id, int answer, BufferedReader br, Admin admin) { // 1. 영업정지 2. 영업재개

		Connection conn = null;
		PreparedStatement pstmtI = null;
		PreparedStatement pstmtU = null;
		String sqlI = null;
		String sqlU = null;
		try {
			System.out.println(accommodation_id+"번 숙소 영업 정지 메뉴입니다");
			System.out.println("영업정지 사유를 입력해주세요");
			String reason = br.readLine();
			
			conn = DBUtil.getConnection();
			sqlI = "INSERT INTO AMMD_MGMT (AMMD_MGMT_ID, ADMIN_ID, ACCOMMODATION_ID, MGMT_REASON, MGMT_DETAILS)"
					+ " VALUES(AMMD_MGMT_SEQ.NEXTVAL, ? , ?, ?, '영업정지')";
			 sqlU = "UPDATE ACCOMMODATION SET ACCOMMODATION_STATUS = 0 WHERE ACCOMMODATION_ID = ?";	
			pstmtI = conn.prepareStatement(sqlI);
			pstmtU = conn.prepareStatement(sqlU);
			pstmtI.setString(1, admin.getID());
			pstmtI.setInt(2, accommodation_id);
			pstmtI.setString(3, reason);
			pstmtU.setInt(1, accommodation_id);
			
			int insert = pstmtI.executeUpdate();
			int update = pstmtU.executeUpdate();
			
			System.out.println(accommodation_id + "번 숙소 영업정지완료");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			try {conn.rollback();} catch (SQLException e1) {}
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmtU, conn);
			try {conn.commit();} catch (SQLException e) {}
		}


	}

	public void accommodation_management(BufferedReader br, AccommodationDAO accommodationDAO, Admin admin) {


		int accommodation_id = Integer.MIN_VALUE;

		while(true) {
			System.out.println("관리를 희망하는 숙소를 선택해주세요");
			accommodation_id = accommodationDAO.selectAccommodation(br);
			if(accommodation_id != 0 || accommodation_id != -1) {
				break;
			}else {
				System.out.println("잘못된 숙소번호입니다.");
				return;
			}
		}


		int answer = Integer.MIN_VALUE;

		while(true) {
			System.out.println("희망하는 처분을 골라주세요");
			System.out.println("1.숙소 영업 정지");
			System.out.println("2.숙소 영업 정지 해제");
			System.out.println("0.뒤로가기");

			try {
				answer = Integer.parseInt(br.readLine());
				if(answer != 1 && answer != 2 && answer != 0) {
					System.out.println("잘못된 입력입니다.");
					continue;
				}else {
					break;
				}
			} catch (Exception e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}
		}
		if(answer == 0) {
			return;
		}else if(answer == 1) {
			accommodationDAO.accommodation_suspension(accommodation_id, answer, br, admin);
		}

	}

}
