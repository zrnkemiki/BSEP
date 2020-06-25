package ftn.bsep.pkiapp.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ftn.bsep.pkiapp.model.User;


@Service
public class EMailService {
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EMailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMail(User user, String subject, String text) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("bgkgsiit@gmail.com");
		mail.setSubject(subject);
		mail.setText(text);
		javaMailSender.send(mail);
		
	}
	
	public void sendMailWithAttachment(String emailTo, String subject, String text, String attachmentName, String pathToFile) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(emailTo);
		helper.setFrom("bgkgsiit@gmail.com");
		helper.setSubject(subject);
		helper.setText(text);
		helper.addAttachment(attachmentName, new ClassPathResource(pathToFile));
		javaMailSender.send(message);
	}

}
