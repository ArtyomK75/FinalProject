package ua.nure.kostenko.finalProject.db.entity;

/**
 * Category entity.
 * 
 * @author A.Kostenko
 * 
 */
public class Count extends Entity {

	private static final long serialVersionUID = 2386302708984618585L;

	private int userId;
	private String count;
	private int isBlockedId;

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

	public int getIsBlockedId() {
		return isBlockedId;
	}

	public void setIsBlockedId(int isBlockedId) {
		this.isBlockedId = isBlockedId;
	}

	@Override
	public String toString() {
		return "Count [count=" + count + ", getId()=" + getId() + "]";
	}

}
