package org.walkerljl.lotteryprinter.client.common;

/**
 * 
 * ExcelUtilException
 *
 * @author lijunlin
 */
public class ExcelUtilException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcelUtilException(String message) {
		super(message);
	}

	public ExcelUtilException(Throwable root) {
		super(root);
	}

	public ExcelUtilException(String message, Throwable root) {
		super(message, root);
	}
}
