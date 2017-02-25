package com.scg.util;

import java.util.Comparator;

import com.scg.domain.TimeCard;

public class TimeCardConsultantComparator implements Comparator<TimeCard> {

	@Override
	public int compare(TimeCard firstTimeCard, TimeCard secondTimeCard) {
		int diff = 0;
		if ( firstTimeCard != secondTimeCard) {
			diff = firstTimeCard.compareTo(secondTimeCard);
		}
		return diff;
	}

}
