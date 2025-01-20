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

        StringBuilder orderList = new StringBuilder();
        for (String orderNumber : orderNumbers) {
            orderList.append("<li>").append(orderNumber).append("</li>");
        }

        String text = "<html><body>" +
            "<h2>'" + recipientEmail + "'님에게 주문 발송 메일을 전송드립니다.</h2>" +
            "<p>아래는 귀하의 주문번호입니다:</p>" +
            "<ul>" + orderList.toString() + "</ul>" +
            "</body></html>";

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
            e.printStackTrace();
        }
    }
}
