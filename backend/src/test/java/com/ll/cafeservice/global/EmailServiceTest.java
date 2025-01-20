package com.ll.cafeservice.global;

import com.ll.cafeservice.global.email.EmailService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;
    @Test
    @DisplayName("이메일 실제 전송")
    void sendOrderEmail_shouldSendHtmlEmailToRecipientWithOrders() {
        String recipientEmail = "sj_0368@naver.com";
        List<String> orderNumbers = List.of("12345", "67890", "11223");
        emailService.sendOrderEmail(recipientEmail, orderNumbers);
    }
}

