package com.booking.menu;

import java.io.BufferedReader;
import java.io.IOException;

import com.booking.DAO.AccommodationDAO;
import com.booking.DAO.AdminDAO;
import com.booking.DAO.QnADAO;
import com.booking.member.Admin;

public class AdminMenu { // 어드민 메뉴 카테고리

	static Admin admin;
	static int answer;
	static AdminDAO adminDAO;
	static AccommodationDAO accommodationDAO;

	public void menu(BufferedReader br, Admin admin, AdminDAO adminDAO) {

		AdminMenu.admin = admin;
		AdminMenu.adminDAO = adminDAO;

		try {
			while(true) {
				System.out.println("관리자 메뉴입니다.");
				System.out.println("원하시는 항목을 골라주세요");
				System.out.println("1.숙소 관리");
				System.out.println("2.문의 관리 페이지");
				System.out.println("3.쿠폰 관리 페이지");
				System.out.println("0.로그아웃");
				answer = Integer.parseInt(br.readLine());
				if(answer == 1) {
					accommodationAdmin(br);
				}else if(answer == 2) {
					qnaManagement(br);
				}else if(answer == 3) {
					couponManagement(br);
				}else if(answer == 0){
					return;
				}else {
					System.out.println("잘못된 입력입니다");
					continue;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void accommodationAdmin(BufferedReader br) {

		accommodationDAO = new AccommodationDAO();
		int answer = Integer.MIN_VALUE;

		while(true) {
			System.out.println("숙소관리 메뉴입니다.");
			System.out.println("원하시는 메뉴를 선택해주세요");
			System.out.println("1. 숙소 관리");
			System.out.println("2. 숙소 등록");
			System.out.println("0. 뒤로가기");
			try {
				answer = Integer.parseInt(br.readLine());
				if(answer != 1 && answer != 2 && answer != 0) {
					System.out.println("유효하지않은 입력값입니다.");
					continue;
				}
				break;
			} catch (Exception e) {
				System.out.println("잘못된 입력값입니다.");
				continue;
			}
		}
		if(answer == 1) accommodationDAO.accommodation_management(br, accommodationDAO, admin);
		else if(answer == 2 ) accommodation_insert(br);
		else if (answer == 0) return;
	}

	private void accommodation_insert(BufferedReader br) {
		String accommodation_name = null;
		String accommodation_address = null;
		String accommodation_description = null;
		int accommodation_price = 0;
		String location_name = null;
		int accommodation_allowed_num = 0;
		while(true) {
			try {
				System.out.println("숙소 등록 메뉴입니다.");
				System.out.println("숙소의 이름을 입력해주세요");
				accommodation_name = br.readLine();
				System.out.println("숙소의 주소를 입력해주세요");
				accommodation_address = br.readLine();
				System.out.println("숙소에 대한 설명을 입력해주세요");
				accommodation_description = br.readLine();

				while(true) {
					try {
						System.out.println("숙소의 가격을 입력해주세요");
						accommodation_price = Integer.parseInt(br.readLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("잘못된 입력입니다. 숙소 가격은 숫자만 입력해주세요");
						continue;
					}
				}
				System.out.println("큰 지역구 이름을 선택해주세요");
				location_name = br.readLine();
				System.out.println("숙소를 추천하는 계절을 입력해주세요");
				String recommendation_season = br.readLine();


				while(true) {
					try {
						System.out.println("수용가능한 숙소의 인원수를 입력해주세요");
						accommodation_allowed_num = Integer.parseInt(br.readLine());
						break;
					} catch (NumberFormatException e) {
						System.out.println("잘못된 입력입니다. 숙소 수용가능인원수는 숫자만 입력해주세요");
						continue;
					}
				}

			} catch (IOException | NumberFormatException e) {
				System.out.println("잘못된 입력입니다.");
				continue;
			}

		}
	}



	private void qnaManagement(BufferedReader br) {
		int answer = Integer.MIN_VALUE;
		QnADAO qnADAO = new QnADAO();
		while(true) {
			System.out.println("문의 관련 페이지 입니다.");
			System.out.println("1.미답변 QnA 답변하기");
			System.out.println("2.답변한 QnA 수정하기");
			System.out.println("3.QnA 전체보기");
			System.out.println("0. 뒤로가기");
			try {
				answer = Integer.parseInt(br.readLine());
				if(answer != 1 && answer != 2 && answer != 3 && answer != 0) {
					System.out.println("유효하지않은 입력입니다.");
					continue;
				}else {
					break;
				}
			} catch (Exception e) {
				System.out.println("비정상적인 입력입니다.");
				continue;
			}
		}
		
		if(answer == 1) {
			qnADAO.answerToQNA(br,admin);
		}
	}
	private void couponManagement(BufferedReader br) {

	}
}
