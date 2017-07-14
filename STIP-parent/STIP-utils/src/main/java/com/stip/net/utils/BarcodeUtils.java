package com.stip.net.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swetake.util.Qrcode;

@Controller
@Scope("request")
@RequestMapping("/yg/barcode")
public class BarcodeUtils extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品二维码
	 */
	@RequestMapping(value = "/getGoodsBarcode.do", method = RequestMethod.GET)
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, java.io.IOException {

		String goodsId = Base64Coder.encodeString(req.getParameter("goodsId"));
		String type = "type=" + ConstantUtils.GOODS_BARCODE;
		String content = type + "&" + goodsId;

		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);

			byte[] contentBytes = content.getBytes("utf-8");

			BufferedImage bufferImgage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics2D = bufferImgage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, 200, 200);
			graphics2D.setColor(Color.BLACK);
			int pixoff = 10;
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] matrix = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix.length; j++) {
						if (matrix[j][i]) {
							graphics2D.fillRect(j * 4 + pixoff, i * 4 + pixoff, 4, 4);
						}
					}
				}
			} else {
				//
			}
			graphics2D.dispose();

			bufferImgage.flush();

			// 禁止图像缓存。
			resp.setHeader("Pragma", "no-cache");
			resp.setHeader("Cache-Control", "no-cache");
			resp.setDateHeader("Expires", 0);
			resp.setContentType("image/jpeg");
			// 将图像输出到Servlet输出流中。
			ServletOutputStream sos = resp.getOutputStream();
			ImageIO.write(bufferImgage, "png", sos);
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
