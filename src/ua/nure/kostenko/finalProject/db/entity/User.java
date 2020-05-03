package ua.nure.kostenko.finalProject.db.entity;

/**
 * User entity.
 * 
 * @author A.Kostenko
 * 
 */
public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;
	
	private String login;
	
	private String password;
	
	private String firstName;
	
	private String lastName;

	private int roleId;

	private int statusId;

	private String locale;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "User [login=" + login  
				+ ", firstName=" + firstName 
				+ ", lastName=" + lastName
				+ ", roleId=" + roleId
				+ ", statusID=" + statusId+ "]";
	}
	
}
