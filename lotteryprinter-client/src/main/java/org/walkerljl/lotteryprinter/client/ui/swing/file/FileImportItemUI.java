package org.walkerljl.lotteryprinter.client.ui.swing.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.walkerljl.lotteryprinter.client.common.GenericFileFilter;
import org.walkerljl.lotteryprinter.client.common.MessageUtils;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;


/**
 * 菜单_文件UI
 * 
 * @author lijunlin
 */
public class FileImportItemUI implements ItemAction {
	/** 文件选择器 */
	private JFileChooser fileChooser = new JFileChooser(
			SystemProperties.USER_DIR);
	/** 当前电脑的桌面路径 */
	private FileSystemView fsv = FileSystemView.getFileSystemView();
	/** 过滤器 */
	private GenericFileFilter filter = new GenericFileFilter(new String[] {
			"xlsx", "xls" });
	private MainUI mainUI;

	public FileImportItemUI(MainUI mainUI) {
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
			File file = new File(filePath);
			String fileName = file.getName();

			if (this.mainUI.getFileNameMap().containsKey(fileName)) {
				int value = MessageUtils.confirm("此数据文件已经加载，是否需要重新添加?");
				if (value == JOptionPane.YES_OPTION) {
					// 重新加载Excel数据文件
					// this.mainUI.getLotteryPrinterService().loadExcel(filePath);
				}
			} else {
				this.mainUI.getFileNameMap().put(fileName, null);
				// 加载Excel文件
				this.mainUI.getLotteryPrinterService().loadExcel(filePath);
			}
		}
	}
}