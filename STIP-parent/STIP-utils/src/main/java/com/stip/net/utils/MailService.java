package com.stip.net.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage alertMailMessage;

	public void sendMail(String from, String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);

	}

	public void sendAlertMail(String alert) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
		mailMessage.setText(alert);
		mailSender.send(mailMessage);

	}

	/**
	 * 发送邮件.
	 * 
	 * @return boolean - 发送结果
	 */
	public boolean sendMail2(String from, String to, String subject, String body) {
		if (from == null || to == null || subject == null || body == null) {
			return false;
		}
		try {
			// 创建基本邮件信息
			SimpleMailMessage message = new SimpleMailMessage();

			// 发送者地址，必须填写正确的邮件格式，否者会发送失败
			message.setFrom(from);

			// 邮件接收者的邮箱地址
			message.setTo(to);

			// 邮件主题
			message.setSubject(subject);

			// 邮件内容，简单的邮件信息只能添加文本信息
			message.setText(body);

			// 发送邮件，参数可以是数组
			// sender.send(SimpleMailMessage[])
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}