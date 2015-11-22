package org.walkerljl.lotteryprinter.client.common;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * 文件过滤器
 * 
 * @author lijunlin
 */
public class GenericFileFilter extends FileFilter {
	/** 文件扩展名 */
	private String[] extendArray;

	/** 构造函数 */
	public GenericFileFilter(String... extend) {
		this.extendArray = extend;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory())
			return false;

		String extendName = file.getName();
		for (String extend : extendArray) {
			if (extendName.endsWith("." + extend))
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		StringBuilder result = new StringBuilder();

		for (String extend : extendArray) {
			result.append(extend).append(",");
		}

		return result.substring(0, result.lastIndexOf(","));
	}
}
