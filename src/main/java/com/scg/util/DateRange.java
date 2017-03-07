package com.scg.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author neil
 * Checks to see if invoice is with in a give range.
 */
public final class DateRange implements Serializable {
	
	/**
	 * Version Id.
	 */
	private static final long serialVersionUID = -3608492595733074615L;
	/**
	 * Serial fields.
	 */
	private static final ObjectStreamField[] serialPersistentFields = {
			new ObjectStreamField("startDate", LocalDate.class),
			new ObjectStreamField("endDate", LocalDate.class)
	};
	/**
	 * The startDate as LocalDate.
	 */
	private java.time.LocalDate startDate;
	/**
	 * The endDate as LocalDate.
	 */
	private java.time.LocalDate endDate;

	
	/**
	 * Constructor for DataRange given two dates.
	 * @param startDate - The start date for this range.
	 * @param endDate - The end date for this range.
	 */
	public DateRange(final LocalDate startDate, final LocalDate endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Constructor for DataRange give a month and a year.
	 * @param month - The month for which this range should be correct.
	 * @param year - The year for which this range should be correct.
	 */
	public DateRange(final Month month, final int year) {
		this(LocalDate.of(year, month.getValue(),1), LocalDate.of(year, month.getValue(),
				LocalDate.of(year, month, 1).isLeapYear() && month == Month.FEBRUARY ? month.maxLength() : month.minLength()));	
	}


	/**
	 * Constructor for DataRange given two string representations.
	 * @param start - Start date as a string.
	 * @param end - End date as a string.
	 */
	public DateRange(final String start, final String end) {
		this(LocalDate.parse(start), LocalDate.parse(end));
	}
	
	@Override
	public String toString() {
		return "DateRange [startDate=" + startDate.toString() + ", endDate=" + endDate.toString() + "]";
	}

	/**
	 * Getter for the start date for this range.
	 * @return The start date for this range.
	 */
	public java.time.LocalDate getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Getter for the end date of this range.
	 * @return The end date for this range.
	 */
	public java.time.LocalDate getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Returns true if the specific date is with in range. The range includes the upper and lower bounds.
	 * @param date - The date in which is or is not with in this range.
	 * @return Boolean true if it is with in range.
	 */
	public boolean isInRange( java.time.LocalDate date ) {
		return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0 ? true : false;
	}
	
	/**
	 * Reads the object fields from stream.
	 * @param ois the stream to read the object from.
	 * @throws ClassNotFoundException if the read object's class can't be loaded.
	 * @throws IOException if any I/O exceptions occur.
	 */
	private void readObject(final ObjectInputStream ois )
		throws ClassNotFoundException, IOException {
		ObjectInputStream.GetField fields = ois.readFields();
		startDate = (LocalDate) fields.get("startDate", LocalDate.now());
		endDate = (LocalDate) fields.get("endDate", LocalDate.now());
	}
	
	/**
	 * Writes the object fields from stream.
	 * @param oos the stream to write the object to.
	 * @throws IOException if any I/O exceptions occur.
	 */
	private void writeObject( final ObjectOutputStream oos ) 
		throws IOException {
		ObjectOutputStream.PutField fields = oos.putFields();
		fields.put("startDate", startDate);
		fields.put("endDate", endDate);
		oos.writeFields();
	}
}
