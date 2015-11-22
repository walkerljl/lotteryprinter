package org.walkerljl.lotteryprinter.client.schedule;

/**
 * 
 * ScheduleException
 *
 * @author lijunlin
 */
public class ScheduleException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ScheduleException(String message) {
		super(message);
	}

	public ScheduleException(Throwable root) {
		super(root);
	}

	public ScheduleException(String message, Throwable root) {
		super(message, root);
	}
}
