package org.walkerljl.lotteryprinter.client.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import org.walkerljl.lotteryprinter.client.pojo.Lottery;
import org.walkerljl.lotteryprinter.client.pojo.Task;

/**
 * 客户端打印工具
 * 
 * @author lijunlin
 */
public class PrintUtils implements Printable
{
	/** 指定打印输出格式 */
    private DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
    /** 设置打印属性 */
    private PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
	/** 字体颜色*/
	private Color color = Color.black;
	/** 字体样式*/
	private Font font;
	/** 任务*/
	private Task task;
	/** 打印内容总页数*/
	private int pageCount = 0;
	/** 页面打印坐标*/
	private Position[] positions;
	/** 当前打印文件名*/
	private String currentFileName;
	
	/**
	 * 构造函数
	 */
	public PrintUtils()
	{
		//加载配置文件中的参数
		loadPropertiesArgument();
	}
	
	/**
	 * 加载配置文件中的参数
	 */
	private void loadPropertiesArgument()
	{
		PropertiesUtils util = null;
		//加载字体大小配置文件
		util = new PropertiesUtils("./config/font-size.properties");
		int fontSize  =  Integer.parseInt(util.getValue("fontSize"));
		this.font = new Font("Courier", Font.PLAIN, fontSize);
		
		//加载打印坐标配置文件
		util = new PropertiesUtils("./config/coord.properties");
		//设置打印坐标
		positions = new Position[14];
		positions[0] = new Position(util.getValue("topVersionX"), util.getValue("topVersionY"));
		
		positions[1] = new Position(util.getValue("lottery1X"), util.getValue("lottery1Y"));
		positions[2] = new Position(util.getValue("lottery2X"), util.getValue("lottery2Y"));
		positions[3] = new Position(util.getValue("lottery3X"), util.getValue("lottery3Y"));
		positions[4] = new Position(util.getValue("lottery4X"), util.getValue("lottery4Y"));
		positions[5] = new Position(util.getValue("lottery5X"), util.getValue("lottery5Y"));
		positions[6] = new Position(util.getValue("lottery6X"), util.getValue("lottery6Y"));
		
		positions[7] = new Position(util.getValue("lottery7X"), util.getValue("lottery7Y"));
		positions[8] = new Position(util.getValue("lottery8X"), util.getValue("lottery8Y"));
		positions[9] = new Position(util.getValue("lottery9X"), util.getValue("lottery9Y"));
		positions[10] = new Position(util.getValue("lottery10X"), util.getValue("lottery10Y"));
		positions[11] = new Position(util.getValue("lottery11X"), util.getValue("lottery11Y"));
		positions[12] = new Position(util.getValue("lottery12X"), util.getValue("lottery12Y"));
		
		positions[13] = new Position(util.getValue("bottomVersionX"), util.getValue("bottomVersionY"));
	}
	
	/**
	 * Graphic指明打印的图形环境；PageFormat指明打印页格式（页面大小以点为计量单位，
	 * 1点为1英寸的1/72，1英寸为25.4毫米。A4纸大致为595×842点）；pageIndex指明页号
	*/
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)throws PrinterException 
	{
		 Graphics2D g2 = (Graphics2D)graphics;
		 if (pageIndex > this.pageCount)
			 return Printable.NO_SUCH_PAGE;
		 //设置打印颜色、大小
	     g2.setPaint(this.color);
	     g2.setFont(this.font);
		 //转换坐标，确定打印边界 
	     g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	     drawCurrentPageText(g2);
	     return Printable.PAGE_EXISTS;  
	}

	/**
	 * 打印指定页的具体内容
	 * @param g2
	 * @param pageFormat
	 * @param pageIndex
	 */
	private void drawCurrentPageText(Graphics2D g2) 
	{  	
		StringBuilder mark = new StringBuilder();
		mark.append("版号：").append(task.getId()).append(",            文件名：").append(this.currentFileName);
		//顶部版号打印
		g2.drawString(mark.toString(), positions[0].getX(), positions[0].getY());
		
		Lottery[] lotteries = task.getLotteries();
		//循环打印
		for(int i=0; i<lotteries.length; i++)
		{
			Position position = positions[i+1];
			if(lotteries[i] != null)
			{
				g2.drawString(lotteries[i].getSerialNumber(), position.getX(), position.getY()-font.getSize());
				g2.drawString(lotteries[i].getPassCode(), position.getX(), position.getY()+8.9f*2.83f-font.getSize());
			}
		}
		
		//底部版号打印
		g2.drawString(mark.toString(), positions[13].getX(), positions[13].getY());
	}
	
	/**
	 * 打印
	 * @param printService 打印服务
	 * @param lotteries 打印内容
	 */
	public void process(PrintService printService, Task task)
	{					
		//设定打印内容
	    this.task = task;
        //创建打印作业
        DocPrintJob job = printService.createPrintJob();
        //设置纸张大小为A4
        aset.add(MediaSizeName.ISO_A3);
        //aset.add(new Copies(1));
        DocAttributeSet das = new HashDocAttributeSet();
        //选择竖打
        das.add(OrientationRequested.PORTRAIT); 
        //指定打印内容
        Doc doc = new SimpleDoc(this, flavor, das);
        //不显示打印对话框，直接进行打印工作
        try
        {
            job.print(doc, aset);
        }
        catch(PrintException ex)
        {
            throw new RuntimeException("执行作业打印出错！", ex);
        }
        
	}
	
	/**
	 * 获取所有打印服务
	 * @return
	 */
	public PrintService[] getAllPrintService()
	{   
		return PrintServiceLookup.lookupPrintServices(flavor, aset);
	}

	public void setCurrentFileName(String currentFileName) {
		this.currentFileName = currentFileName;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
