package ua.nure.kostenko.finalProject.db.entity;

import java.util.Date;

/**
 * Category entity.
 * 
 * @author A.Kostenko
 * 
 */
public class CreditCard extends Entity {

	private static final long serialVersionUID = 2386302708538718585L;

	private int countId;
	private long number;
	private String expiration;
	private int cvv;


	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public int getCountId() {
		return countId;
	}

	public void setCountId(int countId) {
		this.countId = countId;
	}

	@Override
	public String toString() {
		return "Credit card [number=" + number + ", getId()=" + getId() + "]";
	}

}
