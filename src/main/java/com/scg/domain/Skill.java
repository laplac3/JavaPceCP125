package com.scg.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author neil
 *Encapsulates the concept of a billable skill.
 */
public enum Skill {
	/**
	 * Project manager skill.
	 */
	PROJECT_MANAGER(250, "Project Manager"),
	/**
	 * System architect skill.
	 */

	SYSTEM_ARCHITECT(200, "System Architect"),
	/**
	 * Software tester skill.
	 */
	SOFTWARE_TESTER(100, "Software Tester"),
	/**
	 * Software engineer.
	 */
	SOFTWARE_ENGINEER(150, "Software Engineer"),
	/**
	 * Unknown skill.
	 */
	UKNOWN_SKILL(0,"");
		
	private int rate;
	private String title;
	/**
	 * Constructor for skill type.
	 * @param rate - Sets the value of the rate pay.
	 * @param title - Sets the title of the skill.
	 */
	Skill(int rate, String title) {
		this.rate = rate;
		this.title = title;
	}
	
	/**
	 * Getter for title.
	 * @return Returns the title of this skill type.
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Getter for the rate pay.
	 * @return Returns the value of the rate pay.
	 */
	public int getRate() {
		return rate;
	}
	
}
