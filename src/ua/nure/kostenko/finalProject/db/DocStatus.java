package ua.nure.kostenko.finalProject.db;


import ua.nure.kostenko.finalProject.db.entity.Payment;

/**
 * Status entity.
 * 
 * @author A.Kostenko
 * 
 */
public enum DocStatus {
	PREPARED, SENDED;

	public static DocStatus getStatus(Payment payment) {
		int statusId = payment.getStatusId();
		return DocStatus.values()[statusId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}