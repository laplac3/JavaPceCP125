package com.scg.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

/**
 * @author neil
 * Utility class for processing time cards list.
 */
public class TimeCardListUtil {
	
	public static void sortByStartDate(List<TimeCard> timeCards) {
		class StartDate {
			int orderByDate (TimeCard t1, TimeCard t2 ) {
				return t1.getWeekStartingDay().compareTo(t2.getWeekStartingDay());
			}
		}
			StartDate date = new StartDate();			
			Collections.sort(timeCards, date::orderByDate);
	}
	
	public static void sortByConsultantName(List<TimeCard> timeCards) {
		Collections.sort(timeCards, (t1,t2)-> t1.getConsultant().getName().compareTo(t2.getConsultant().getName()));
	}
	
	public static List<TimeCard> getTimeCardsForDateRange(List<TimeCard> timeCards, DateRange dateRange ) {
		List<TimeCard> dateInRange =timeCards.stream()
				.filter(t->dateRange.isInRange(t.getWeekStartingDay()))
				.collect(Collectors.toList());
		return dateInRange;
	}
	
	public static List<TimeCard> getTimeCardsForConsultant(List<TimeCard> timeCards, Consultant consultant) {
		List<TimeCard>  haveConsultant = timeCards.stream()
				.filter(e->consultant.equals(e.getConsultant()))
				.collect(Collectors.toList());
		return haveConsultant;
	}
}

