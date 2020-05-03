package ua.nure.kostenko.finalProject.db.entity;

/**
 * Order entity.
 * 
 * @author A.Kostenko
 * 
 */
public class Payment extends Entity {

	private static final long serialVersionUID = 5692708766041889396L;

	private String date;
	private int userId;
	private int countId;
	private String countReceiver;
	private float sum;
	private int statusId;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCountId() {
		return countId;
	}

	public void setCountId(int countId) {
		this.countId = countId;
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

	@Override
	public String toString() {
		return "Order [count ID=" + countId + ", user Id=" + userId + ", count receiver="
				+ countReceiver + ", statusId=" + statusId
				+ ", sum=" + sum +", getId()=" + getId() + "]";
	}

}
