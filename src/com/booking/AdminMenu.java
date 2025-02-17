package com.booking;

import java.io.BufferedReader;
import java.io.IOException;

import com.booking.member.Admin;

public class AdminMenu { // 어드민 메뉴 카테고리

	static Admin admin;
	static int answer;

	public void menu(BufferedReader br, Admin admin) {
		AdminMenu.admin = admin;
		try {
			while(true) {
				answer = Integer.parseInt(br.readLine());
				System.out.println("관리자 메뉴입니다.");
				System.out.println("원하시는 항목을 골라주세요");
				System.out.println("1.숙소 등록 관리");
				System.out.println("2.회원 관리 페이지");
				System.out.println("3.문의 관리 페이지");
				System.out.println("4.쿠폰 관리 페이지");
				System.out.println("0.로그아웃");
				if(answer == 1) {
					accommodationManagement(br);
				}else if(answer == 2) {
					userManagement(br);
				}else if(answer == 3) {
					qnaManagement(br);
				}else if(answer == 4) {
					couponManagement(br);
				}else if(answer == 0){
					
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

	private void accommodationManagement(BufferedReader br) {

	}

	private void qnaManagement(BufferedReader br) {

	}

	private void userManagement(BufferedReader br) {

	}

	private void couponManagement(BufferedReader br) {

	}



}
