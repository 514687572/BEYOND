package com.stip.net.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 操作文件工具类
 * 
 * @author chenjunan
 * @since 20170609
 */
public class FileUtils {

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null; // 新建一个输出流
		FileOutputStream fos = null; // w文件包装输出流
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName); // 新建一个file类
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos); // 输出的byte文件
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close(); // 关闭资源
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close(); // 关闭资源
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static File BetyToFile(String filePath) {
		File file = new File(filePath);
		BufferedOutputStream stream = null;
		FileOutputStream fstream = null;
		byte[] data = new byte[(int) file.length()];
		try {
			fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
				if (null != fstream) {
					fstream.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return file;
	}

	public void writeFileContentBat(byte[] b, String fileName, String format) {
		String targetFile = this.getClass().getClassLoader().getResource("").getPath() + fileName + ".dat";
		File file = new File(targetFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(targetFile)));
			out.write(b);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] readFileFromBat(String fileName, String format) {
		String targetFile = this.getClass().getClassLoader().getResource("").getPath() + fileName + ".dat";
		byte b = 0;
		byte[] bs = new byte[1024];
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(targetFile)));
			b=in.readByte();
			
			byte n;
			int i=0;
			while ((n = in.readByte()) != -1) {
				bs[i]=n;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bs;
	}

	public static void main(String[] args) throws Exception {
		FileUtils fu = new FileUtils();
		File file = new File("E:/Google-Java编程风格指南中文版.pdf");
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
		byte[] b = new byte[(int)file.length()];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		fis.close();
		bos.close();
		fu.writeFileContentBat(bos.toByteArray(), "Google-Java编程风格指南中文版", "pdf");
	}
}
