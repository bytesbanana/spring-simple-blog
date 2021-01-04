package com.bytebanana.simpleblog.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.exception.SpringSimpleBlogException;
import com.bytebanana.simpleblog.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class MailService {

	private final JavaMailSender mailSender;

	@Async
	public void sendMail(NotificationEmail notificationEmail) {

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springsimpleblog@localhost.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			boolean isHtml = true;
			messageHelper.setText(notificationEmail.getBody(), isHtml);

		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			log.error("Exception occurred when sending mail", e);
			throw new SpringSimpleBlogException(
					"Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
		}
	}

}
