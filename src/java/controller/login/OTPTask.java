package controller.login;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OTPTask implements Runnable {
    private final String email;
    private final String otp;

    public OTPTask(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    @Override
    public void run() {
        MailUtil.sendOTP(email, otp);
    }
}