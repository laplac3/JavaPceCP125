package com.scg.domain;

import org.junit.Test;
import com.scg.util.Name;
import org.junit.Before;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class TestConsultantTime {

	private java.time.LocalDate date= LocalDate.now();
	private ClientAccount account= new ClientAccount("account",new Name("last","first"));;
	private int hours = 8;
	private int differentHours =5;
	private Skill projectManager = Skill.PROJECT_MANAGER;
	private Skill softwareEngineer = Skill.SOFTWARE_ENGINEER;
	private Skill softwareTester= Skill.SOFTWARE_TESTER;
	private Skill systemArchitect= Skill.SYSTEM_ARCHITECT;
	private Skill unknownSkill = Skill.UKNOWN_SKILL;
	private final int prime = 31;
	private int expectedResult = 1;
	ConsultantTime consultant1;
	ConsultantTime consultant2;
	ConsultantTime nullConsultant = null;
	ConsultantTime expectedConsultantTime1;
	
	@Before
	public void init() {
		consultant1 = new ConsultantTime(date, account, projectManager, hours);
		expectedConsultantTime1 = new ConsultantTime( date, new ClientAccount("account",new Name("last","first"))
				,projectManager,hours);
	    consultant2 = new ConsultantTime( LocalDate.of(1984, 10, 2), new ClientAccount("different account", 
	    		new Name("diffenentLastName","differentFirstName")),softwareEngineer,differentHours);
	}
	
	@Test
	public void test() {
		
		//test getters
		assertEquals(account,consultant1.getAccount());
		assertEquals(date,consultant1.getDate());
		assertEquals(hours,consultant1.getHours(),0);
		assertEquals(projectManager,consultant1.getSkillType());
		
		//testString
		String str = "ConsultantTime [hours=" + hours + "]";
		//assertEquals(str, consultant2.toString());

		//testEquals

		//assertTrue(consultant1.equals(expectedConsultantTime1));
		assertFalse(consultant1.equals(consultant2));
		assertFalse(consultant1.equals(null));

		//test HashCode
		expectedResult = prime * expectedResult +  account.hashCode();
		expectedResult = prime * expectedResult + date.hashCode();
		expectedResult = prime * expectedResult + hours;
		expectedResult = prime * expectedResult + projectManager.hashCode();
		assertEquals(expectedResult,consultant1.hashCode(),0); 
		
		//Test toString
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
		String date = consultant1.getDate().format(formatter);
		String account = consultant1.getAccount().getName();
		String skill =consultant1.getSkillType().getTitle();
		String hourss = Integer.toString(consultant1.getHours());
		String ExpectedToStringTest = String.format("%s %2$s %3$s %4$s%n",account,date,skill,hourss );
		assertEquals(ExpectedToStringTest,consultant1.toString());
	}

}
