package org.walkerljl.lotteryprinter.client.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.walkerljl.lotteryprinter.client.common.PrintUtils;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.entity.Lottery;
import org.walkerljl.lotteryprinter.client.entity.Task;

/**
 * 打印预览对话框
 * 
 * @author lijunlin
 */
public class PrintPreviewUI extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton nextButton = new JButton("下一页");
	private JButton previousButton = new JButton("上一页");
	private JButton closeButton = new JButton("关闭");
	private JPanel buttonPanel = new JPanel();
	private PreviewCanvas canvas;

	public PrintPreviewUI(Frame parent, String title, boolean modal,
			PrintUtils printUtil) {
		super(parent, title, modal);
		this.canvas = new PreviewCanvas(printUtil);
		this.setLayout();
		this.setVisible(true);
	}

	private void setLayout() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(canvas, BorderLayout.CENTER);

		nextButton.setMnemonic('N');
		nextButton.addActionListener(this);
		buttonPanel.add(nextButton);
		previousButton.setMnemonic('N');
		previousButton.addActionListener(this);
		buttonPanel.add(previousButton);
		closeButton.setMnemonic('N');
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setBounds((int) ((SystemProperties.SCREEN_WIDTH - 800) / 2),
				(int) ((SystemProperties.SCREEN_HEIGHT - 600) / 2), 800, 600);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == nextButton)
			nextAction();
		else if (src == previousButton)
			previousAction();
		else if (src == closeButton)
			closeAction();
	}

	private void closeAction() {
		this.setVisible(false);
		this.dispose();
	}

	private void nextAction() {

	}

	private void previousAction() {

	}

	class PreviewCanvas extends JPanel {
		private static final long serialVersionUID = 1L;

		private PrintUtils printUtil = null;

		public PreviewCanvas(PrintUtils printUtil) {
			this.printUtil = printUtil;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

			double xoff;
			double yoff;
			double scale;
			double px = pf.getWidth();
			double py = pf.getHeight();
			double sx = getWidth() - 1;
			double sy = getHeight() - 1;
			if (px / py < sx / sy) {
				scale = sy / py;
				xoff = 0.5 * (sx - scale * px);
				yoff = 0;
			} else {
				scale = sx / px;
				xoff = 0;
				yoff = 0.5 * (sy - scale * py);
			}
			g2.translate((float) xoff, (float) yoff);
			g2.scale((float) scale, (float) scale);

			Rectangle2D page = new Rectangle2D.Double(0, 0, px, py);
			g2.setPaint(Color.white);
			g2.fill(page);
			g2.setPaint(Color.black);
			g2.draw(page);

			try {
				printUtil.print(g2, pf, 0);
			} catch (PrinterException pe) {
				g2.draw(new Line2D.Double(0, 0, px, py));
				g2.draw(new Line2D.Double(0, px, 0, py));
			}
		}
	}

	private static String handleCell(Object data) {
		// 判断是否为null
		if (data == null)
			return "";

		// 类型转换，处理科学计数法的问题
		DecimalFormat df = new DecimalFormat("0");
		String temp = df.format(Double.parseDouble(data.toString().trim()));

		StringBuilder result = new StringBuilder();
		// 业务需求处理:每四个数字中间两个空格
		for (int i = 0; i < temp.length() / 4; i++) {
			result.append(temp.substring(i * 4, (i + 1) * 4)).append("  ");
		}

		// 去除最后两个空格,并返回
		return result.toString().substring(0, result.length() - 2);
	}

	public static void main(String[] args) {
		PrintUtils printUtil = new PrintUtils();

		Lottery[] lotteries = new Lottery[12];

		for (int i = 0; i < lotteries.length; i++) {
			String str = "12345678";
			String str2 = "123456789012";
			lotteries[i] = new Lottery(handleCell(str), handleCell(str2));
		}
		printUtil.setTask(new Task(1000000001L, lotteries));

		new PrintPreviewUI(new JFrame(), "", true, printUtil);
	}
}
