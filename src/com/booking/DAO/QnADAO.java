package com.booking.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.booking.member.Admin;
import com.dbutil.DBUtil;

public class QnADAO {

	public void showQnASubject() {

	}

	public void answerToQNA(BufferedReader br, Admin admin) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		List<Integer> qna_id_list = new ArrayList<>();

		try { // 답변하지않은 항목들 추출
			conn = DBUtil.getConnection();
			sql = "SELECT * "
					+ "FROM QNA Q INNER JOIN QNA_SUBJECT QS "
					+ "ON Q.SUBJECT_ID = QS.SUBJECT_ID "
					+ "WHERE QNA_ANSWERED_STATUS = 0 "
					+ "ORDER BY QNA_ID";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				do {
					int qna_id = rs.getInt("QNA_ID");
					qna_id_list.add(qna_id);
					String qna_user_id = rs.getString	("USER_ID");
					String qna_subject_name = rs.getString("SUBJECT_NAME");
					String qna_content = rs.getString("QNA_CONTENT");
					Date qna_questioned_date = rs.getDate("QNA_QUESTIONED_DATE");
					System.out.printf("%d번 :\t%s\t%s\t\t%s\t\t%s\n",qna_id ,qna_user_id ,qna_subject_name,qna_content,qna_questioned_date);

				}while(rs.next());
			}else {
				System.out.println("미답변된 문의가 없습니다.");
				return;
			}

			int qna_id = Integer.MIN_VALUE;
			while(!qna_id_list.contains(qna_id)) {
				System.out.println("답변할 글의 번호를 입력해주세요");
				while(true) {
					try {
						qna_id = Integer.parseInt(br.readLine());
						break;
					} catch (Exception e) {
						System.out.println("유효하지않은 입력값입니다.");
						continue;
					}
				}
			}
			
			adminAnswerToQNA(br,qna_id,admin);



		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("유효하지않은 입력입니다.");
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);

		}

	}

	private void adminAnswerToQNA(BufferedReader br, int qna_id, Admin admin) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		System.out.println("답변할 내용을 입력해주세요.");
		try {
			String answer = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE QNA SET "
					+ "QNA_ANSWERED_STATUS = 1, "
					+ "QNA_ANSWER = answer, "
					+ "QNA_ANSWERED_DATE = SYSDATE, "
					+ "ADMIN_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, admin.getID());;
			int update = pstmt.executeUpdate();
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			try {conn.rollback();} catch (SQLException e1) {}
		}finally {
			try {conn.commit();} catch (SQLException e1) {}
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}

}
