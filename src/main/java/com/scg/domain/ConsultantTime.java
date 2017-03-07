package com.scg.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.text.MessageFormat.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.scg.util.Name;
/**
 * @author neil
 *A consultant time, maintains the account, skill, date and hours.
 */
public final class ConsultantTime implements Serializable {
	
//	/**
//	 * The serialization fields.
//	 */
//	private static final ObjectStreamField[] serialPresistenceFields = {
//			new ObjectStreamField("date", LocalDate.class),
//			new ObjectStreamField("account", Account.class),
//			new ObjectStreamField("skillType", Skill.class),
//			new ObjectStreamField("hours",int.class)
//	};
	
	/**
	 * Date of the work.
	 */
	private java.time.LocalDate date;
	/**
	 * The account billable
	 */
	public Account account;
	/**
	 * The type of skill.
	 */
	private final Skill skillType;
	/**
	 * The hours of consulting.
	 */
	private int hours;
 
	/**
	 * Creates a new instance of ConsultantTime.
	 * @param date - The date that this instance occurred.
	 * @param account - The account to charge the hours to (Either client of Nonbillable). 
	 * @param skillType - The skill type.
	 * @param hours - The total hours for this instance.
	 * Throws a IllegalArgumentException if the hours are less or equal to zero.
	 */
	public ConsultantTime(final LocalDate date, final Account account, final Skill skillType, int hours) {
		this.date = date;
		this.account = account;
		this.skillType = skillType;
		this.hours = hours;
		if ( hours <= 0 ) {
			throw new IllegalArgumentException(); 
		}
	}
	

	/**
	 * Getter for the date.
	 * @return Returns the date of the instance.
	 */
	public java.time.LocalDate getDate() {
		return date;
	}

	/**
	 * Setter for the date of the instance.
	 * @param date - The date of the instance to set.
	 */
	public void setDate(java.time.LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter for the account.
	 * @return Returns the account for this instance.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Setter for the account.
	 * @param account - The account to set.
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * Getter for the skill type.
	 * @return Return the skill type for the instance.
	 */
	public Skill getSkillType() {
		return skillType;
	}

	/**
	 * Getter for the hours of the instance.
	 * @return Returns the hours for the instance.
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Setter for the hours of the instance.
	 * @param hours -The hours to set.
	 * @throws Throws an IllegalArgumentException if the value of hours is less or equal to zero.
	 */
	public void setHours(int hours) {
		if ( hours <= 0 ) {
			throw new IllegalArgumentException("Hours must be a positive integer."); 
		}
		this.hours = hours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + hours;
		result = prime * result + ((skillType == null) ? 0 : skillType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultantTime other = (ConsultantTime) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (hours != other.hours)
			return false;
		if (skillType != other.skillType)
			return false;
		return true;
	}

	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
		return String.format("%s %2$s %3$s %4$s%n",account.getName(),date.format(formatter),skillType.getTitle(),hours );
	}
	
//	/**
//	 * Read the Object fields from stream.
//	 * @param ois the stream to read object from.
//	 * @throws ClassNotFoundException if the read object class can't be loaded.
//	 * @throws IOException if any I/O exception occurs.
//	 */ 
//	@SuppressWarnings("unused")
//	private void readObject(final ObjectInputStream ois ) 
//		throws ClassNotFoundException, IOException {
//		ObjectInputStream.GetField fields = ois.readFields();
//		LocalDate d = (LocalDate) fields.get("date", LocalDate.now());
//		Account a = (Account) fields.get("account", new ClientAccount("foo", new Name() ) );
//		Skill s = (Skill) fields.get("skillType", Skill.UKNOWN_SKILL);
//		int h = fields.get("hours", 0 );
//	}
//	
//	private void writeObject(final ObjectOutputStream oos ) throws IOException {
//		ObjectOutputStream.PutField fields =oos.putFields();
//		fields.put("date", date );
//		fields.put("account", account);
//		fields.put("skillType", skillType);
//		fields.put("hours", hours);
//		oos.writeFields();
//	}

}
