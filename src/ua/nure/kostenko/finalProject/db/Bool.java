package ua.nure.kostenko.finalProject.db;

import ua.nure.kostenko.finalProject.db.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Role entity.
 * 
 * @author A.Kostenko
 * 
 */

public enum Bool {
	NO, YES;
	

	public static Map<Integer, String> getMap() {
		Map<Integer, String> map = new HashMap<>();
		for (Bool bool: Bool.values()) {

			map.put(bool.getId(), bool.getName().toUpperCase());

		}

		return map;

	}

	public String getName() {
		return name().toLowerCase();
	}

	public int getId() {
		return ordinal();
	}

	public static boolean getBoolById(int id){
		if (id == 1) {
			return true;
		}
		return false;
	}

	public static boolean getBoolName(String name){
		if (YES.equals(name)) {
			return true;
		}
		return false;
	}


}
