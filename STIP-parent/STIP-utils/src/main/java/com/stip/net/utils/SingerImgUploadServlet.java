package com.stip.net.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web端头像修改
 * 
 * @author AIDAN 2015-08-13
 */
public class SingerImgUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMGROOT = "/upload/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		String userWebAppPath = getWebAppPath();
		/** 检查是否有图片上传文件夹 */
		checkImageDir(userWebAppPath);

		/** 图片上传的相对路径 */
		String imgUploadPath = null;
		String imgWebAppPath = null;
		/** 图片后缀 */
		String imgFileExt = null;

		/** 图片名称:以当前日期 */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		String imgFileId = formatter.format(new Date());

		// 图片初始化高度与宽度
		String width = null;
		String height = null;

		int imgWidth = 0;
		int imgHeight = 0;

		try {
			com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
			smartUpload.initialize(getServletConfig(), request, response);
			smartUpload.upload();
			com.jspsmart.upload.Request rqest = smartUpload.getRequest();

			// 指定图片宽度和高度
			width = rqest.getParameter("width");
			if (null == width) {
				width = "400";
			}
			height = rqest.getParameter("height");
			if (null == height) {
				height = "400";
			}

			imgWidth = Integer.parseInt(width);
			imgHeight = Integer.parseInt(height);

			// 文件个数
			int fileCounts = smartUpload.getFiles().getCount();

			for (int i = 0; i < fileCounts; i++) {
				com.jspsmart.upload.File myFile = smartUpload.getFiles().getFile(i);
				if (!myFile.isMissing()) {
					imgFileExt = ImageUtil.getFileFileExt(myFile.getFileExt());
					// 组装图片真实名称
					imgFileId += i + System.currentTimeMillis() + "." + imgFileExt;
					// 图片生成路径
					imgWebAppPath = userWebAppPath + imgFileId;
					// 生成图片文件
					myFile.saveAs(imgWebAppPath);
					// 图片的相对路径
					imgUploadPath = IMGROOT + imgFileId;
					// 检查图片大小
					BufferedImage src = ImageIO.read(new File(imgWebAppPath)); // 读入文件

					int imgSrcWidth = src.getWidth(); // 得到源图宽
					int imgSrcHeight = src.getHeight(); // 得到源图长

					// 按照图片的设置大小生成
					BufferedImage newSrc = null;
					boolean reload = false;
					if (imgSrcHeight < 400 && imgSrcWidth < 400) {
						int heightTimes = (400 % imgSrcHeight > 0) && (400 % imgSrcHeight < 400) ? (400 / imgSrcHeight) + 1 : 1;
						int widthTimes = (400 % imgSrcWidth > 0) && (400 % imgSrcWidth < 400) ? (400 / imgSrcWidth) + 1 : 1;
						int times = widthTimes;
						if (heightTimes > widthTimes) {
							times = widthTimes;
						}

						if (times > 2) {
							ImageUtil.zoomInImage(imgWebAppPath, imgWebAppPath, times, imgFileExt);
							// 重新指定大小
							imgWidth = imgSrcWidth * times;
							imgHeight = imgSrcHeight * times;
							newSrc = ImageUtil.lessonImage(src, imgWidth, imgHeight);
						} else {
							imgWidth = imgSrcWidth;
							imgHeight = imgSrcHeight;
						}
					} else if (imgSrcHeight > 400 && imgSrcWidth > 400) {
						int heightTimes = (imgSrcHeight % 400 > 0) && (imgSrcHeight % 400 < 400) ? (imgSrcHeight / 400) + 1 : 1;
						int widthTimes = (imgSrcWidth % 400 > 0) && (imgSrcWidth % 400 < 400) ? (imgSrcWidth / 400) + 1 : 1;
						int times = widthTimes;
						if (heightTimes >= widthTimes) {
							times = heightTimes;
						}

						int newWidth = imgSrcWidth;
						int newHeight = imgSrcHeight;
						if (times > 1) {
							newWidth = imgSrcWidth / times; // 得到图片新宽度
							newHeight = imgSrcHeight / times; // 得到图片新高度
						}

						newSrc = ImageUtil.lessonImage(src, newWidth, newHeight);
						// 重新指定大小
						imgWidth = newWidth;
						imgHeight = newHeight;
						reload = true;
					} else if (imgSrcHeight > 400 && imgSrcWidth < 400) {
						int heightTimes = (imgSrcHeight % 400 > 0) && (imgSrcHeight % 400 < 400) ? (imgSrcHeight / 400) + 1 : 1;
						int newWidth = imgSrcWidth;
						int newHeight = imgSrcHeight;
						newWidth = imgSrcWidth; // 得到图片新宽度
						newHeight = imgSrcHeight / heightTimes; // 得到图片新高度
						newSrc = ImageUtil.lessonImage(src, newWidth, newHeight);
						// 重新指定大小
						imgWidth = newWidth;
						imgHeight = newHeight;
						reload = true;
					} else if (imgSrcHeight < 400 && imgSrcWidth > 400) {
						int widthTimes = (imgSrcWidth % 400 > 0) && (imgSrcWidth % 400 < 400) ? (imgSrcWidth / 400) + 1 : 1;
						int newWidth = imgSrcWidth;
						int newHeight = imgSrcHeight;
						newWidth = imgSrcWidth / widthTimes; // 得到图片新宽度
						newHeight = imgSrcHeight; // 得到图片新高度
						newSrc = ImageUtil.lessonImage(src, newWidth, newHeight);
						// 重新指定大小
						imgWidth = newWidth;
						imgHeight = newHeight;
						reload = true;
					}

					if (reload) {
						File f = new File(imgWebAppPath);
						ImageIO.write(newSrc, imgFileExt, f);
					}

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String str = imgUploadPath + ";" + width + ";" + height + ";" + IMGROOT;
		OutputStream out = response.getOutputStream();
		out.write(str.getBytes());
		out.flush();
		out.close();
	}

	private String getWebAppPath() {
		String webAppPath = this.getServletContext().getRealPath("/");
		String userWebAppPath = webAppPath + IMGROOT;
		return userWebAppPath;
	}

	private void checkImageDir(String userWebAppPath) {
		File file = new File(userWebAppPath);
		if (!file.exists()) {
			file.mkdir();
		}
	}
}