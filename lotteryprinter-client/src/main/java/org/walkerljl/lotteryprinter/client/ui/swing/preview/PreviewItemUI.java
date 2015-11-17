package org.walkerljl.lotteryprinter.client.ui.swing.preview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.walkerljl.lotteryprinter.client.common.PrintUtils;
import org.walkerljl.lotteryprinter.client.common.SystemProperties;
import org.walkerljl.lotteryprinter.client.ui.swing.ItemAction;
import org.walkerljl.lotteryprinter.client.ui.swing.MainUI;

/**
 * 
 * PreviewItemUI
 *
 * @author lijunlin
 */
public class PreviewItemUI extends JDialog implements ActionListener, ItemAction {
	private static final long serialVersionUID = 1L;

	private JButton closeButton = new JButton("关闭");
	private JPanel buttonPanel = new JPanel();
	private PreviewCanvas canvas;

	public PreviewItemUI(MainUI mainUI, PrintUtils printUtil) {
		super(mainUI, "打印预览", false);
		this.canvas = new PreviewCanvas(printUtil);
		this.setLayout();
	}

	private void setLayout() {
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(canvas, BorderLayout.CENTER);

		closeButton.setMnemonic('N');
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setBounds((int) ((SystemProperties.SCREEN_WIDTH - 800) / 2),
				(int) ((SystemProperties.SCREEN_HEIGHT - 600) / 2), 800, 600);
	}

	@Override
	public void action() {
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == closeButton)
			closeAction();
	}

	private void closeAction() {
		this.setVisible(false);
		this.dispose();
	}

	// 内部类
	class PreviewCanvas extends JPanel {
		private static final long serialVersionUID = 1L;

		private PrintUtils printUtil;

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
}