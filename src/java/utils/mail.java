/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author admin
 */
public class mail {

    // Method to send OTP via email
    public static void sendOTP(String to, String otp) {
        String subject = "Your OTP Code";
        String content = "Your OTP code is: " + otp + ". This code is valid for 5 minutes.";

        // Email credentials
        String username = "esmartlearnisp@gmail.com";
        String password = "cpqt hzfi czfh zupm"; // Application password

        // Properties for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Mail session
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.addHeader("Content-type", "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);

            // Send email
            Transport.send(message);
            System.out.println("OTP sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
