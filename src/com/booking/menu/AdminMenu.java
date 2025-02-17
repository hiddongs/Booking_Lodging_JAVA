package com.booking.menu;

import java.io.BufferedReader;
import java.io.IOException;

import com.booking.DAO.AdminDAO;
import com.booking.member.Admin;

public class AdminMenu { // 어드민 메뉴 카테고리

	static Admin admin;
	static int answer;
	static AdminDAO adminDAO;

	public void menu(BufferedReader br, Admin admin, AdminDAO adminDAO) {

		AdminMenu.admin = admin;
		AdminMenu.adminDAO = adminDAO;

		try {
			while(true) {
				answer = Integer.parseInt(br.readLine());
				System.out.println("관리자 메뉴입니다.");
				System.out.println("원하시는 항목을 골라주세요");
				System.out.println("1.숙소 관리");
				System.out.println("2.문의 관리 페이지");
				System.out.println("3.쿠폰 관리 페이지");
				System.out.println("0.로그아웃");
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
		while(true) {
			System.out.println("숙소관리 메뉴입니다.");
			System.out.println("원하시는 메뉴를 선택해주세요");
			System.out.println("1. 숙소 관리");
			accommodation_management(br);
			System.out.println("2. 숙소 등록");
			accommodation_insert(br);

		}
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
			adminDAO.insert_accommodation(accommodation_name, accommodation_address ,
					accommodation_description, location_name, accommodation_price, accommodation_allowed_num);

		}

	}

	private void accommodation_management(BufferedReader br) {
		int accommodation_id = Integer.MIN_VALUE;
		while(true) {
			System.out.println("관리를 희망하는 숙소를 선택해주세요");
			accommodation_id = adminDAO.selectAccommodation(br);
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
				if(answer != 1 || answer != 2 || answer != 0) {
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
		}
		adminDAO.accommodation_management(accommodation_id, answer);
	}

	private void qnaManagement(BufferedReader br) {

	}
	private void couponManagement(BufferedReader br) {

	}
}
