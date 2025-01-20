package com.ll.cafeservice.global.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

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
            throw new EmailServiceException("이메일 전송에 실패했습니다.", e);
        }
    }
}
