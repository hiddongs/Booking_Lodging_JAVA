package com.booking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.booking.DAO.AdminDAO;
import com.booking.DAO.UserDAO;
import com.booking.member.Admin;
import com.booking.member.Member;
import com.booking.menu.AdminMenu;
import com.booking.menu.UserMenu;

public class Main {

	static BufferedReader br;
	static Member member;
	static Admin admin;
	static UserDAO userDAO;
	static boolean loginStatus;
	static AdminDAO adminDAO;

	public Main(){
		br = new BufferedReader(new InputStreamReader(System.in));
		userDAO = new UserDAO();
		adminDAO = new AdminDAO();
		callMenu();
	}

	private void callMenu(){
		while(true) {
			int menuNum = Integer.MAX_VALUE;
			while(true) {
				try {
				System.out.println("원하시는 메뉴를 입력해주세요");
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");
				System.out.println("0. 프로그램 종료");
				menuNum = Integer.parseInt(br.readLine());
				if(menuNum != 1 && menuNum != 2 && menuNum != 0) {
					System.out.println("유효하지않은 입력입니다.");
					continue;
				}else break;

				}catch (Exception e) {
					System.out.println("잘못된 입력입니다");
					continue;
				}
			}
			if(menuNum == 1) {
				
				try {
					System.out.println("로그인할 ID를 입력해주세요");
					String ID = br.readLine();
					System.out.println("비밀번호를 입력해주세요");
					String passwd = br.readLine();

					if((admin = adminDAO.adminLogin(ID, passwd)) != null) {
						loginStatus = true;
						AdminMenu adminMenu = new AdminMenu();
						adminMenu.menu(br, admin, adminDAO);

					}
//					else if((member = userDAO.login(ID, passwd)) != null) {
//						loginStatus = true;
//						System.out.println("로그인이 완료되었습니다.");
//						UserMenu userMenu = new UserMenu();
//						//						userMenu.menu(br);
//					}

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}else if(menuNum == 2) {

				String emailFormat = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

				try {
					System.out.println("회원가입 모드입니다.");
					String ID;
					while(true) { // 중복된 ID 거르기 위한 while문
						System.out.println("회원가입할 ID를 입력해주세요");
						ID = br.readLine();
						if(UserDAO.checkIDDuplicate(ID)) {
							System.out.println("중복된 ID입니다.");
							continue;
						}else {
							break;
						}
					}
					System.out.println("비밀번호를 입력해주세요");
					String passwd = br.readLine();

					System.out.println("이름을 입력해주세요");
					String name = br.readLine();

					String email = null;
					while(true) {
						System.out.println("이메일을 입력해주세요");
						email = br.readLine();
						if(!email.matches(emailFormat)) {
							System.out.println("잘못된 이메일 형식입니다.");
						}else {
							break;
						}
					}

					if(!userDAO.register(ID, passwd, name, email)) {
						System.out.println("회원가입에 실패했습니다.");
					}else {
						System.out.println("회원가입에 성공했습니다.");
					}
					continue;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}else if(menuNum == 0) {
				System.exit(0);
			}
		}
	}
}

