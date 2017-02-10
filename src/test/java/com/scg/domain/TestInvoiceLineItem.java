package com.scg.domain;

import org.junit.Test;
import com.scg.util.Name;

import org.junit.Before;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.Calendar;

import com.scg.domain.TimeCard;

public class TestInvoiceLineItem {

	private LocalDate date1 = LocalDate.of(2006, 03, 06);
	private Consultant consultant1 = new Consultant(new Name("Coder","Carl"));
	private Skill skill1 = Skill.SOFTWARE_ENGINEER;
	private int hours1 = 8;
	private int charge1 = skill1.getRate() * hours1;
	private InvoiceLineItem line1;
	
	@Before
	public void init() {
		line1 = new InvoiceLineItem(date1,consultant1,skill1,hours1);
	}
	@Test
	public void test() {
		//get charge
		assertEquals(charge1, line1.getCharge(),0);
		
		//toString
		//DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
		//String dateF = this.date1.format(dateFormat);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, date1.getMonth().getValue()-1);
		cal.set(Calendar.DAY_OF_MONTH, date1.getDayOfMonth());
		cal.set(Calendar.YEAR, date1.getYear());
		String consultantStr = consultant1.toString();
		String padSkil = TimeCard.pad(consultantStr, " ", 28);
		String hoursPad = "";
		String hoursStr = Integer.toString(hours1);
		String skillStr = skill1.getTitle();
		if ( hoursStr.length() == 1)
			hoursPad = TimeCard.pad(skillStr," ",25);
		else
			hoursPad = TimeCard.pad(skillStr," ",24);
		String chargeStr = Integer.toString(charge1);
		String chargerPad = TimeCard.pad(chargeStr + ".00", " ", 10);
		String consultantPad = TimeCard.pad( "", " ", 2);
		String expected = String.format("%1$tm/%1$td/%1$ty %2$s %3$s %4$s %5$s %6$s %7$s %8$s %9$s.00",
				cal, consultantPad, consultantStr, padSkil, skillStr, hoursPad, hoursStr,  chargerPad, chargeStr );
		String actual = line1.toString();
		assertEquals(expected,actual);
	}

}
