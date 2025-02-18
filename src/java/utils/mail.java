/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
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
    // Email credentials
    protected static final String username = "esmartlearnisp@gmail.com";
    protected static final String password = "cpqt hzfi czfh zupm"; // Application password
    
    // Method to send OTP via email
    public static void sendOTP(String to, String otp) {
        String subject = "Your OTP Code";
        String content = "Your OTP code is: " + otp + ". This code is valid for 5 minutes.";        

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
    
    private boolean sendEmail(String toEmail, String subject, String content) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setFrom(new InternetAddress(username));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
    
    public boolean sendMailVerify(String toEmail, String code) {
        String subject = "Email Verification";
        String content = "Registered successfully. Please verify your account using this code: " + code;
        return sendEmail(toEmail, subject, content);
    }

    public boolean sendMailResetPassword(String toEmail, String code) {
        String subject = "Password Reset Request";
        String content = "You have requested to reset your password. Use this code to reset your password: " + code
                + "\n\nIf you didn't request this, please ignore this email.";
        return sendEmail(toEmail, subject, content);
    }

    public boolean sendPasswordChangeConfirmation(String toEmail, String password) {
        String subject = "Password Change Confirmation";
        String content = "Your password has been successfully changed.\n\n"
                + "Use: " + password + " to sign in!";
        return sendEmail(toEmail, subject, content);
    }
    public boolean sendEmailChangeConfirmation(String toEmail,String newEmail) {
        String subject = "Email Change Confirmation";
        String content = "Your email has been successfully changed.\n\n"
                + "Use: " + newEmail + " to sign in!";
        return sendEmail(toEmail, subject, content);
    }

    public boolean sendPasswordForUser(String toEmail, String password) {
        String subject = "You have been created an account!";
        String content = "Use: " + password + " to sign in!";        
        return sendEmail(toEmail, subject, content);
    }
}
