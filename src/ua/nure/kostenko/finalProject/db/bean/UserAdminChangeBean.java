package ua.nure.kostenko.finalProject.db.bean;

import ua.nure.kostenko.finalProject.db.entity.Entity;

/**
 * Provide records for virtual table:
 *
 * @author A.Kostenko
 * 
 */
public class UserAdminChangeBean extends Entity {
	
	private static final long serialVersionUID = -5654982557199337483L;

	private int userId;

	private String userFirstName;

	private String userLastName;

	private String statusName;

	private String userLogin;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@Override
	public String toString() {
		return "UserAdminChangeBean [userId=" + userId + ", userFirstName="
				+ userFirstName + ", userLastName=" + userLastName
				+ "statusName=" + statusName
				+ "]";
	}
}
