package ua.nure.kostenko.finalProject.db;


import ua.nure.kostenko.finalProject.db.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Status entity.
 * 
 * @author A.Kostenko
 * 
 */
public enum UserStatus {
	VALID, BLOCKED, NEW;

	public static UserStatus getStatus(User user) {
		int status_id = user.getStatusId();
		return UserStatus.values()[status_id];
	}

	public static Map<Integer, String> getMap() {
		Map<Integer, String> map = new HashMap<>();
		for (UserStatus status: UserStatus.values()) {

			map.put(status.getId(), status.getName().toUpperCase());

		}
		return map;

	}


	public String getName() {
		return name().toLowerCase();
	}

	public int getId() {
		return ordinal();
	}

}