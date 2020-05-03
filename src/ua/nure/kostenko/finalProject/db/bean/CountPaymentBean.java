package ua.nure.kostenko.finalProject.db.bean;

import ua.nure.kostenko.finalProject.db.entity.Entity;

/**
 * Provide records for virtual table:
 *
 * @author A.Kostenko
 * 
 */
public class CountPaymentBean extends Entity {
	
	private static final long serialVersionUID = -984651328199337483L;

	private int userId;

	private String count;

	private String countReceiver;

	private float sum;

	private int statusId;

	private String date;


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCountReceiver() {
		return countReceiver;
	}

	public void setCountReceiver(String countReceiver) {
		this.countReceiver = countReceiver;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CountsUserBean [userId=" + userId + ", count="
				+ count
				+ "countReceiver=" + countReceiver
				+ "sum=" + sum
				+ "statusId=" + statusId
				+ "]";
	}
}
