package org.walkerljl.lotteryprinter.client.ui.swing.reprint;

import java.text.DecimalFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.walkerljl.lotteryprinter.client.MainUI;
import org.walkerljl.lotteryprinter.client.common.ExcelUtils;
import org.walkerljl.lotteryprinter.client.common.GenericFileFilter;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;

/**
 * 
 * ExcelHandleItemUI
 *
 * @author lijunlin
 */
public class ExcelHandleItemUI implements ItemAction {
	/** Excel文件头 */
	private static String[] header = { "task_id" };
	/** 文件选择器 */
	private JFileChooser fileChooser = new JFileChooser(
			SystemProperties.USER_DIR);
	/** 当前电脑的桌面路径 */
	private FileSystemView fsv = FileSystemView.getFileSystemView();
	/** 过滤器 */
	private GenericFileFilter filter = new GenericFileFilter(new String[] {
			"xlsx", "xls" });
	private MainUI mainUI;

	/**
	 * 构造函数
	 * 
	 * @param mainUI
	 */
	public ExcelHandleItemUI(MainUI mainUI) {
		this.mainUI = mainUI;
		this.fileChooser.setCurrentDirectory(this.fsv.getHomeDirectory());
		this.fileChooser.setFileFilter(this.filter);
		this.fileChooser.setDialogTitle("请选择Excel数据文件");
		this.fileChooser.setApproveButtonText("确定");
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	@Override
	public void action() {
		String filePath = null;
		int result = this.fileChooser.showOpenDialog(this.mainUI);
		if (JFileChooser.APPROVE_OPTION == result) {
			filePath = this.fileChooser.getSelectedFile().getPath();
			List<List<Object[]>> excelDataList = ExcelUtils.readExcel(filePath,
					header);

			// 调用业务处理函数处理
			Set<Long> idSet = new LinkedHashSet<Long>();
			for (List<Object[]> dataOfSheet : excelDataList) {
				for (Object[] dataOfRow : dataOfSheet) {
					idSet.add(Long.parseLong(toConvert(dataOfRow[0])));
				}
			}

			this.mainUI.getLotteryPrinterService().addTaskByIds(idSet);
		}
	}

	private String toConvert(Object data) {
		// 类型转换，处理科学计数法的问题
		DecimalFormat df = new DecimalFormat("0");
		String result = df.format(Double.parseDouble(data.toString().trim()));

		return result;
	}

}
