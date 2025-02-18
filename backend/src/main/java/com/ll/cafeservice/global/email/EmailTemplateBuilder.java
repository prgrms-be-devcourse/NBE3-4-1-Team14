package com.ll.cafeservice.global.email;

import java.util.List;

public class EmailTemplateBuilder {

    public static String buildOrderEmail(String recipientEmail, List<String> orderNumbers) {
        StringBuilder orderList = new StringBuilder();
        for (String orderNumber : orderNumbers) {
            orderList.append("<li style='padding: 8px; border-bottom: 1px solid #eee;'>" + orderNumber + "</li>");
        }

        return "<html><body style='font-family: Arial, sans-serif; line-height: 1.6; background-color: #f4f4f9; padding: 20px;'>" +
            "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);'>" +
            "<h2 style='color: #333;'>안녕하세요, " + recipientEmail + "님</h2>" +
            "<p style='font-size: 16px; color: #555;'>주문 발송 메일을 전송드립니다. 아래는 귀하의 주문번호입니다:</p>" +
            "<ul style='list-style-type: none; padding-left: 0;'>" + orderList.toString() + "</ul>" +
            "<p style='font-size: 16px; color: #555;'>감사합니다!</p>" +
            "<p style='font-size: 14px; color: #888;'>프로그래머스 백엔드 4회차 14팀</p>" +
            "</div>" +
            "</body></html>";
    }
}
