package cn.edu.aiit.gradution.util;

import cn.edu.aiit.gradution.pojo.dto.Mail;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component("sender")
@Data
@ConfigurationProperties(prefix = "spring.mail")
public class MailSender {
	@Autowired
	private JavaMailSender sender;
	private String username;
	@Async
	public void sendSimpleMail(Mail mail) {
		// 构建一个邮件对象
		SimpleMailMessage message = new SimpleMailMessage();
		// 设置邮件主题
		message.setSubject(mail.getSubject());
		// 设置邮件发送者，这个跟application.yml中设置的要一致
		message.setFrom(username);
		message.setTo(mail.getTolist());
		// 设置邮件发送日期
		message.setSentDate(mail.getTime());
		// 设置邮件的正文
		message.setText(mail.getContext());
		// 发送邮件
		sender.send(message);
	}
}
