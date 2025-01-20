package com.ll.cafeservice.global.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderEmail(String recipientEmail, List<String> orderNumbers) {
        String subject = "프로그래머스 백엔드 4회차 14팀 주문 발송 안내";
        String text = EmailTemplateBuilder.buildOrderEmail(recipientEmail, orderNumbers);
        sendHtmlEmail(recipientEmail, subject, text);
    }

    private void sendHtmlEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("이메일 전송 실패, 이메일 : " + to, e);
        }
    }
}
