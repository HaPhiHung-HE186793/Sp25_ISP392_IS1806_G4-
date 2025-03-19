package controller.showOrder;

import jakarta.mail.Session;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import java.util.Random;

public class MailUtilMes {

    // Method to generate a 6-digit OTP
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    // Method to send OTP via email
    public static void sendOTP(String to, String mess) {
        String subject = "Your OTP Code";
        String content = " " + mess; // Nội dung tiếng Việt

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
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B")); // Mã hóa tiêu đề
            message.setContent(content, "text/html; charset=UTF-8"); // Mã hóa nội dung

            // Send email
            Transport.send(message);
            System.out.println("OTP sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}