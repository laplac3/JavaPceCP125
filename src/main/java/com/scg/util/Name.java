package com.scg.util;

public final class Name {
	
	private String lastName;
	private String firstName;
	private String middleName;
	
	public Name(String lastName, String firstName, String midleName) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = midleName;
	}

	public Name(String lastName, String firstName) {
		this(lastName,firstName,"");
	}
	
	public Name() {
		
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMidleName() {
		return middleName;
	}

	public void setMidleName(String midleName) {
		this.middleName = midleName;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder(); 
		str = str.append(lastName).
								append(", ").
								append(firstName).
								append(" ").
								append(middleName);
		return str.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
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
		Name other = (Name) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		return true;
	}

}
