package com.scg.domain;

import java.io.Serializable;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamField;

import com.scg.util.Name;

/**
 * @author neil
 *A consultant.
 */
public class Consultant implements Comparable<Consultant>, Serializable {
	
	/**
	 * Version Id
	 */
	private static final long serialVersionUID = 4072449537156214365L;
	
	/**
	 * The serialization fields
	 */
//	private static final ObjectStreamField[] serialPersistenceFields = {
//			new ObjectStreamField( "name", String.class),
//			new ObjectStreamField("hashCode", int.class)
//	};
	
	/**
	 * Name of the consultant.
	 */
	private Name name;
	
	/**
	 * The hash code.
	 */
	private int hashCode;
	/**
	 * Creates a new instance of consultant
	 * @param name - The name of the consultant to set.
	 */
	public Consultant(Name name) {
		this.name = name;
		this.hashCode = hashCode();
	}
	
	/**
	 * Creates a new instance of consultant.
	 */
	public Consultant() {
		
	}

	/**
	 * Getter for the name of consultant.
	 * @return Returns the name of the consultant.
	 */
	public Name getName() {
		return name;
	}

	/**
	 * Setter for the name of the consultant.
	 * @param name - The name of the consultant name.
	 */
	public void setName(Name name) {
		this.name = name;
	}

	public String toString() {
		return name.toString();
	}
	
	public int getHashCode() {
		return this.hashCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Consultant other = (Consultant) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Consultant other) {
		int diff = 0;
		if ( this != other ) {
			diff = name.compareTo(other.getName());
		}
		return diff;
	}
	
	
	/**
	 * @return
	 */
	private Object writeReplace() {
		return new SerializationProxy(this);
	}
	
	private void readObject(ObjectInputStream ois) throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required");
	}
	
	private static class SerializationProxy implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 706379947643817124L;
		private final String x;
		private final String y;
		private final String z;

		SerializationProxy(final Consultant consultant){
			final Name name = consultant.getName();
			x = name.getLastName();
			y= name.getFirstName();
			z = name.getMidleName();
		}
		
		/**
		 * Creates and returns an instance of the inclosed class.
		 * @return A Consultant. 
		 */
		private Object readResolve() { 
			return new Consultant(new Name(x,y,z)); 
			}
	}
	
}
