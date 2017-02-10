package com.scg.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.NonBillableAccount;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Name;

/**
 * @author neil
 * 
 *Encapsulates a time card capable of storing consultant's billable and nonbillable hours for a week.
 */
public final class TimeCard {
	
	private Consultant consultant;
	private java.time.LocalDate weekStartingDay;
	private int totalBillableHours;
	private int totalNonBillableHours;
	private int totalHours;
	private List<ConsultantTime> consultingHours = new ArrayList<>();

	/**
	 * Creates a new instance of TimeCard.
	 * @param consultant - The Consultant that this time card's information stores.
	 * @param weekStartingDay - The starting date of the time card.
	 */
	public TimeCard(final Consultant consultant, final LocalDate weekStartingDay) {
		this.consultant = consultant;
		this.weekStartingDay = weekStartingDay;
	} 
	
	/**
	 * Getter for the Consultant.
	 * @return Returns the value of the consultant.
	 */
	public Consultant getConsultant() {
		return this.consultant;
	}
	
	/**
	 * Getter for the total billable hours.
	 * @return Returns the total billable hours.
	 */
	public int getTotalBillableHours() {
		return this.totalBillableHours;
	}
	
	/**
	 * Getter for the nonBillable hours.
	 * @return Returns the nonBillable hours.
	 */
	public int getTotalNonBillableHours() {
		return this.totalNonBillableHours;
	}
	
	/**
	 * Getter for the Consulting hours.
	 * @return Returns the value of consutlingHours. 
	 */
	public List<ConsultantTime> getConsultingHours() {
		return consultingHours;
	}
	
	/**
	 * Adds consultant time.
	 * @param consultantTime - The consultant time to add.
	 */
	public void addConsultantTime(ConsultantTime consultantTime) {
		consultingHours.add(consultantTime);	
		totalHours += consultantTime.getHours();
		if (consultantTime.getAccount().isBillable())
			totalBillableHours += consultantTime.getHours();
		else
			totalNonBillableHours += consultantTime.getHours();
		}
	

	
	/**
	 * Getter for the total hours.
	 * @return Returns the total hours.
	 */
	public int getTotalHours() {
		return this.totalHours;
	}
	
	/**
	 * Getter for the starting day for the week.
	 * @return Returns the starting day for the week.
	 */
	public java.time.LocalDate getWeekStartingDay() {
		return this.weekStartingDay;
	}
	
	/**
	 * Getter for consultantBillabelHours.
	 * @param clientName - Name of the client to extract hours for.
	 * @return Returns a list of billable hours for.
	 */
	public List<ConsultantTime> getBillableHoursForClients(String clientName){
		List<ConsultantTime> consultanBillableHours = new ArrayList<>();
		for ( ConsultantTime a : consultingHours ) 
			if ( a.getAccount().getName().equals(clientName) && a.getAccount().isBillable() )
				 consultanBillableHours.add(a);
		return consultanBillableHours;
	}
	
	public String toString() {
		return "consultant " + consultant.getName().toString();
	}

	public static String pad(String strToPad, String charToPadWith, int maxNumb) {
		int padNumb = maxNumb - strToPad.length();
		String pad = new String(new char[padNumb]).replace("\0", charToPadWith);
		if (padNumb <= 0 )
				throw new IllegalArgumentException(); 
		return pad;
	}
	
	public static String stringBillableOrNon(String strFinal, List<ConsultantTime> consultingHours, boolean bool){
			for (ConsultantTime a : consultingHours) {
				if ( a.getAccount().isBillable() == bool) {
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
						hoursPad = pad(dateStr," ",14);
					else
						hoursPad = pad(dateStr," ",13);
					String skillPad = pad(hours + hoursPad, " ",8);
					String datePad = pad(name, " ",31);
					String str = String.format(fmt, name, datePad, dateStr,  hoursPad,hours, skillPad, skill);
					strFinal = strFinal.concat(str);
				}
			}
		return strFinal;
			
	}
	/**
	 * Creates a string representation of the time card suitable to print.
	 * @return Returns this time card as a formatted string. 
	 */
	public String toReportString() {
		
		String strFinal = "";
		//header
		String divider = pad("","=",73); 
		String fmt1 = "%s %n";
		String str1 = String.format(fmt1, divider);
		
		//consultant and starting day line
		Name name = consultant.getName();
		String namePad = pad(name.toString()," ",33 );
		String fmt2 ="Consultant: %s %2$s";
		String str2 = String.format(fmt2, name, namePad);
		
		LocalDate weekStarting = weekStartingDay;
		String mes2 ="Week Starting:";
		String fmt3 ="%s %2$tb %2$td, %2$tY %n";
		String str3 = String.format(fmt3, mes2, weekStarting);

		
		//billable time headers
		String msg = "Billable Time:";
		String msg1 = "Account";
		String msg2 = "Date";
		String msg3 = "Hours";
		String msg4 = "Skill";
		String fmt4 = "%n%s %n%2$s %3$30s %4$13s %5$7s %n";
		String str4 = String.format(fmt4,msg,msg1,msg2,msg3, msg4);
		
		String divider2 = new String(new char[30]).replace("\0", "-");
		String divider3 = new String(new char[10]).replace("\0", "-");
		String divider4 = new String(new char[5]).replace("\0", "-");
		String divider5 = new String(new char[17]).replace("\0", "-");
		String fmt5 = "%s    %2$s   %3$s   %4$s %n";
		String str5 = String.format(fmt5, divider2, divider3, divider4,divider5);

		strFinal= strFinal.concat(str1).concat(str2).concat(str3).concat(str4).concat(str5);

		//billable time 
		strFinal = stringBillableOrNon(strFinal, consultingHours, true);
		
		//nonbillable time
		String msg5 = "Non-Billable Time:";
		String msg6 = "Account";
		String msg7 = "Date";
		String msg8 = "Hours";
		String msg9 = "Skill";
		String fmt6 = "%n%s %n%2$s %3$30s %4$13s %5$7s %n";
		String str6 = String.format(fmt6,msg5,msg6,msg7,msg8, msg9);
	
		String fmt7 = "%s    %2$s   %3$s   %4$s %n";
		String str7 = String.format(fmt7, divider2, divider3, divider4,divider5);
		strFinal = strFinal.concat(str6).concat(str7);
		
		strFinal = stringBillableOrNon(strFinal, consultingHours, false);
			
		String msg10 = "Summary:";
		String msg11 = "Total Billable:";
		int bHours = totalBillableHours;
		String msg12 = "Total Non-Billable:";
		int nBHours = totalNonBillableHours;
		String msg13 = "Total hours:";
		int tHours = totalHours;
		String fmt8 = "%n%s %n%2$s %3$36s %n%4$s %5$32s %n%6$s %7$39s%n";
		String str8 = String.format(fmt8, msg10, msg11, bHours, msg12, nBHours, msg13, tHours);
		
		strFinal = strFinal.concat(str8).concat(str1);		
		return strFinal;
	}
}
