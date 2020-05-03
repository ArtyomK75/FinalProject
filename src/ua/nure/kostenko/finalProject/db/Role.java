package ua.nure.kostenko.finalProject.db;

import ua.nure.kostenko.finalProject.db.entity.User;

import java.util.*;

/**
 * Role entity.
 * 
 * @author A.Kostenko
 * 
 */

public enum Role {
	ADMIN, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}

	public static Map<Integer, String> getMap() {
		Map<Integer, String> map = new HashMap<>();
		for (Role role: Role.values()) {

			map.put(role.getId(), role.getName().toUpperCase());

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
