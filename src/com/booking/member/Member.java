package com.booking.member;

import java.util.Date;

public class Member {
	
	String name;
	String email;
	int point;
	int cash;
	Date reg_date;
	boolean status;
	Enum Grade;
	
	public Member(String name, String email, int point, int cash, Date reg_date, boolean status, Enum grade) {
		super();
		this.name = name;
		this.email = email;
		this.point = point;
		this.cash = cash;
		this.reg_date = reg_date;
		this.status = status;
		Grade = grade;
	}

}
