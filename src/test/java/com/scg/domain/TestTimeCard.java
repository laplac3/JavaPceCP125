package com.scg.domain;

import org.junit.Test;
import com.scg.util.Name;

import org.junit.Before;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.scg.domain.TimeCard;

import static org.junit.Assert.*;

public class TestTimeCard {

	private LocalDate billableDay = LocalDate.of(2021, 1, 1);
	private LocalDate nonBillableDay = LocalDate.of(2021, 1, 2);
	private int expectedTotalBillableHours = 8;
	private int expectedNonTotalBillableHours = 8;
	private int expectedTotalHours = 16;	
	private TimeCard jamesTimeCard;
	private Consultant jamesStewart = new Consultant( new Name("Stewart","James"));
	private LocalDate startDate = LocalDate.of(2021, 1, 1);
	private List<ConsultantTime> consultingHoursExpected = new ArrayList<ConsultantTime>(){
		{ add(new ConsultantTime(billableDay, new ClientAccount("Expeditors", jamesStewart.getName()), Skill.PROJECT_MANAGER, expectedTotalBillableHours));
		  add(new ConsultantTime(nonBillableDay, NonBillableAccount.BUSINESS_DEVELOPMENT,Skill.PROJECT_MANAGER, expectedNonTotalBillableHours));
		}	
	};
	
	@Before
	public void init() {
		jamesTimeCard = new TimeCard(jamesStewart,startDate);
		jamesTimeCard.addConsultantTime(new ConsultantTime(billableDay, 
				new ClientAccount("Expeditors", jamesStewart.getName()), Skill.PROJECT_MANAGER, expectedTotalBillableHours));
		jamesTimeCard.addConsultantTime(new ConsultantTime(nonBillableDay, 
				NonBillableAccount.BUSINESS_DEVELOPMENT,Skill.PROJECT_MANAGER, expectedNonTotalBillableHours));
	}
	
	@Test
	public void test() {
		//test Getters
		assertEquals(jamesStewart, jamesTimeCard.getConsultant());
		assertEquals(startDate, jamesTimeCard.getWeekStartingDay());
		assertEquals(expectedTotalBillableHours, jamesTimeCard.getTotalBillableHours(),0);
		assertEquals(expectedNonTotalBillableHours, jamesTimeCard.getTotalNonBillableHours(),0);
		assertEquals(expectedTotalHours,jamesTimeCard.getTotalHours(),0);
		String expectedCOnStr = "consultant " + jamesStewart;
		assertEquals(expectedCOnStr,jamesTimeCard.toString());
		
		//test getBillableHoursForClients(String clientName)
		//expected
		List<ConsultantTime> consultanBillableHoursExpected = new ArrayList<>();
		for ( ConsultantTime a : consultingHoursExpected ) 
			if ( a.getAccount().getName().equals("Expeditors") && a.getAccount().isBillable() )
				 consultanBillableHoursExpected.add(a);
		//actual
		List<ConsultantTime> jamesConsultingTime = jamesTimeCard.getBillableHoursForClients("Expeditors");
		for ( int i=0; i< consultanBillableHoursExpected.size();i++) {
			assertEquals(consultanBillableHoursExpected.get(i).getHours(),  jamesConsultingTime.get(i).getHours());
		}
		
		//test pad
		String charToPadWith = "^";
		int maxNumb = 30;
		String strToPad = jamesStewart.toString();
		int padNumb = maxNumb - strToPad.length();
		String pad = new String(new char[padNumb]).replace("\0", charToPadWith);
		String ExpectedPaddedStr = String.format("%s%2$s", strToPad, pad);
		String actualPaddedStr = String.format("%s%2$s", strToPad, TimeCard.pad(jamesStewart.toString(),charToPadWith, maxNumb));
		assertEquals(ExpectedPaddedStr,actualPaddedStr);
		
		//test stringBillableOrNon
		//billable hours
		String strFinalExpected = "a string";
		for (ConsultantTime a : consultingHoursExpected) {
			if ( a.getAccount().isBillable() == true) {
				String fmt = "%s %2$s %3$s %4$s %5$s%6$s%7$s %n";
				String name = a.getAccount().getName();
				String hours = Integer.toString(a.getHours());
				Calendar date = Calendar.getInstance();
				date.set(Calendar.MONTH, a.getDate().getMonth().getValue()-1);
			    date.set(Calendar.DAY_OF_MONTH, a.getDate().getDayOfMonth());
			    date.set(Calendar.YEAR, a.getDate().getYear());
			    String dateStr =  String.format("%1$tm/%1$td/%1$ty", date);
				String skill = a.getSkillType().getTitle();
				String hoursPad = "";
				if ( hours.length() == 1)
					hoursPad = TimeCard.pad(dateStr," ",15);
				else
					hoursPad = TimeCard.pad(dateStr," ",14);
				String skillPad = TimeCard.pad(hours + hoursPad, " ",11);
				String datePad = TimeCard.pad(name, " ",39);
				String str = String.format(fmt, name, datePad, dateStr,  hoursPad,hours, skillPad, skill);
				strFinalExpected = strFinalExpected.concat(str);
			}
		}
		String strFinalActual = TimeCard.stringBillableOrNon("a string",jamesTimeCard.getConsultingHours(),true);
		assertEquals(strFinalExpected, strFinalActual);
		
		//nonBillable hours
		String strFinalExpected2 = "a string";
		for (ConsultantTime a : consultingHoursExpected) {
			if ( a.getAccount().isBillable() == false) {
				String fmt = "%s %2$s %3$s %4$s %5$s%6$s%7$s %n";
				String name = a.getAccount().getName();
				String hours = Integer.toString(a.getHours());
				
				Calendar date = Calendar.getInstance();
				date.set(Calendar.MONTH, a.getDate().getMonth().getValue()-1);
			    date.set(Calendar.DAY_OF_MONTH, a.getDate().getDayOfMonth());
			    date.set(Calendar.YEAR, a.getDate().getYear());
			    String dateStr =  String.format("%1$tm/%1$td/%1$ty", date);
				String skill = a.getSkillType().getTitle();
				String hoursPad = "";
				if ( hours.length() == 1)
					hoursPad = TimeCard.pad(dateStr," ",15);
				else
					hoursPad = TimeCard.pad(dateStr," ",14);
				String skillPad = TimeCard.pad(hours + hoursPad, " ",11);
				String datePad = TimeCard.pad(name, " ",39);
				String str = String.format(fmt, name, datePad, dateStr,  hoursPad,hours, skillPad, skill);
				strFinalExpected2 = strFinalExpected2.concat(str);
			}
		}
		assertEquals(strFinalExpected2, TimeCard.stringBillableOrNon("a string",jamesTimeCard.getConsultingHours(),false));
		
		//test reportToString
		String strFinalExpected3 = "";
		//header
		String divider = TimeCard.pad("","=",82); 
		String fmt1 = "%s %n";
		String str1 = String.format(fmt1, divider);
		
		//consultant and starting day line
		Name name = jamesStewart.getName();
		String namePad = TimeCard.pad(name.toString()," ",42 );
		String fmt2 ="Consultant: %s %2$s";
		String str2 = String.format(fmt2, name, namePad);
		
		LocalDate weekStarting = jamesTimeCard.getWeekStartingDay();
		String mes2 ="Week Starting:";
		String fmt3 ="%s %2$tb %2$td, %2$tY %n";
		String str3 = String.format(fmt3, mes2, weekStarting);

		
		//billable time headers
		String msg = "Billable Time:";
		String msg1 = "Account";
		String msg2 = "Date";
		String msg3 = "Hours";
		String msg4 = "Skill";
		String fmt4 = "%n%s %n%2$s %3$37s %4$13s %5$7s %n";
		String str4 = String.format(fmt4,msg,msg1,msg2,msg3, msg4);
		
		String divider2 = new String(new char[37]).replace("\0", "-");
		String divider3 = new String(new char[10]).replace("\0", "-");
		String divider4 = new String(new char[5]).replace("\0", "-");
		String divider5 = new String(new char[20]).replace("\0", "-");
		String fmt5 = "%s    %2$s   %3$s   %4$s %n";
		String str5 = String.format(fmt5, divider2, divider3, divider4,divider5);

		strFinalExpected3= strFinalExpected3.concat(str1).concat(str2).concat(str3).concat(str4).concat(str5);

		//billable time 
		strFinalExpected3 = TimeCard.stringBillableOrNon(strFinalExpected3, jamesTimeCard.getConsultingHours(), true);
		
		//nonbillable time
		String msg5 = "Non-Billable Time:";
		String msg6 = "Account";
		String msg7 = "Date";
		String msg8 = "Hours";
		String msg9 = "Skill";
		String fmt6 = "%n%s %n%2$s %3$37s %4$13s %5$7s %n";
		String str6 = String.format(fmt6,msg5,msg6,msg7,msg8, msg9);
	
		String fmt7 = "%s    %2$s   %3$s   %4$s %n";
		String str7 = String.format(fmt7, divider2, divider3, divider4,divider5);
		strFinalExpected3 = strFinalExpected3.concat(str6).concat(str7);
		
		strFinalExpected3 = TimeCard.stringBillableOrNon(strFinalExpected3, jamesTimeCard.getConsultingHours(), false);
			
		String msg10 = "Summary:";
		String msg11 = "Total Billable:";
		int bHours = expectedTotalBillableHours;
		String msg12 = "Total Non-Billable:";
		int nBHours = expectedNonTotalBillableHours;
		String msg13 = "Total hours:";
		int tHours = expectedTotalHours;
		String fmt8 = "%n%s %n%2$s %3$43s %n%4$s %5$39s %n%6$s %7$46s%n";
		String str8 = String.format(fmt8, msg10, msg11, bHours, msg12, nBHours, msg13, tHours);
		
		strFinalExpected3 = strFinalExpected3.concat(str8).concat(str1);

		assertEquals(strFinalExpected3,jamesTimeCard.toReportString());
	}
}
