package com.stip.net.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class TicketPrintUtils implements Printable {

	public static String cabserial = "HD7H389WJD93URJ99";
	public static String storageNum = "a789";
	public static String bindingDate = "2012-09-09 12:12:12";

	/**
	 * * @param Graphic指明打印的图形环境 * @param
	 * PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英寸的1/72，1英寸为25.4毫米。A4纸大致为595×842点） * @param
	 * pageIndex指明页号
	 **/
	public int print(Graphics gra, PageFormat pf, int pageIndex) throws PrinterException {
		System.out.println("pageIndex=" + pageIndex);
		Component c = null;
		// 转换成Graphics2D
		Graphics2D g2 = (Graphics2D) gra;
		// 设置打印颜色为黑色
		g2.setColor(Color.BLACK);
		// 打印起点坐标
		double x = pf.getImageableX();
		double y = pf.getImageableY();
		switch (pageIndex) {
			case 0:
				// 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）
				// Java平台所定义的五种字体系列：Serif、SansSerif、Monospaced、Dialog 和
				// DialogInput
				Font font = new Font("新宋体", Font.PLAIN, 9);
				g2.setFont(font); // 设置字体
				// BasicStroke bs_3=new BasicStroke(0.5f);
				float[] dash1 = { 2.0f };
				// 设置打印线的属性。
				// 1.线宽 2、3、不知道，4、空白的宽度，5、虚线的宽度，6、偏移量
				g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));
				// g2.setStroke(bs_3);//设置线宽
				float heigth = font.getSize2D(); // 字体高度
				System.out.println("x=" + x);
				// -1- 用Graphics2D直接输出
				// 首字符的基线(右下部)位于用户空间中的 (x, y) 位置处
				// g2.drawLine(10,10,200,300);
				Image src = Toolkit.getDefaultToolkit().getImage("D:\\Aotori\\javaPic.jpg");
				g2.drawImage(src, (int) x, (int) y, c);
				int img_Height = src.getHeight(c);
				int img_width = src.getWidth(c);

				g2.drawString("---------------------------------", 10, 20);

				g2.drawString("驾驶室流水号", 10, 30);
				g2.drawString("库位编号", 100, 30);
				g2.drawString("绑定时间", 150, 30);

				g2.drawString("---------------------------------", 10, 90);
				g2.drawImage(src, (int) x, (int) (y + 1 * heigth + img_Height + 11), c);
				return PAGE_EXISTS;
			default:
				return NO_SUCH_PAGE;
		}
	}

	public static void outPutInfor() {
		// ReadData();
		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT); // LANDSCAPE表示竖打;PORTRAIT表示横打;REVERSE_LANDSCAPE表示打印空白
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(590, 840); // 纸张大小(590, 840)表示A4纸
		p.setImageableArea(10, 10, 260, 343); // A4(595 X
		// 842)设置打印区域，其实0，0应该是72，72
		// ，因为A4纸的默认X,Y边距是72

		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new TicketPrintUtils(), pf);
		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();
		// 设置打印类
		job.setPageable(book);
		try {
			// 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
			boolean a = job.printDialog();
			if (a) {
				job.print();
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		// ReadData();
		// 通俗理解就是书、文档
		Book book = new Book();
		// 设置成竖打
		PageFormat pf = new PageFormat();
		pf.setOrientation(PageFormat.PORTRAIT); // LANDSCAPE表示竖打;PORTRAIT表示横打;REVERSE_LANDSCAPE表示打印空白
		// 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
		Paper p = new Paper();
		p.setSize(590, 840); // 纸张大小(590, 840)表示A4纸
		p.setImageableArea(10, 10, 260, 343); // A4(595 X
		// 842)设置打印区域，其实0，0应该是72，72
		// ，因为A4纸的默认X,Y边距是72

		pf.setPaper(p);
		// 把 PageFormat 和 Printable 添加到书中，组成一个页面
		book.append(new TicketPrintUtils(), pf);
		// 获取打印服务对象
		PrinterJob job = PrinterJob.getPrinterJob();
		// 设置打印类
		job.setPageable(book);
		try {
			// 可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
			boolean a = job.printDialog();
			if (a) {
				job.print();
			}
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

}