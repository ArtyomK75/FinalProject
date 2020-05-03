package ua.nure.kostenko.finalProject.db.entity;

/**
 * Category entity.
 * 
 * @author A.Kostenko
 * 
 */
public class UnlockRequest extends Entity{

	private static final long serialVersionUID = -8786302708984618585L;

	private int countId;
	private int actual;
	private String date;


	public int getCountId() {
		return countId;
	}

	public void setCountId(int countId) {
		this.countId = countId;
	}

	public int getActual() {
		return actual;
	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UnlockRequest [countId=" + countId
				+ " actual=" + actual + "]";
	}

}
