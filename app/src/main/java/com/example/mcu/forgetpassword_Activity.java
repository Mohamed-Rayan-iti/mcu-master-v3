package com.example.mcu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class forgetpassword_Activity extends AppCompatActivity {
    String email;
    ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword_);


        iconBack = findViewById(R.id.forget_password_back);

        iconBack.setOnClickListener(v -> {
            Intent intent = new Intent(forgetpassword_Activity.this, login_Activity.class);
            startActivity(intent);
        });
    }



    private int createRandomCode(){
        Random random = new Random();
        return random.nextInt(9999 - 1001 + 1) + 1001;
    }

    public void sentEmailCode(View view) {

        email="moh0115011@gmail.com";
        new Thread(() -> {
            try {
                final String username = "rayan.miligy@gmail.com";
                final String password = "<R@y@n.Miligy>";
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties,
                        new javax.mail.Authenticator() {

                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("Parkly.App@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(email));
                    message.setSubject("Parkly Application - Activation Code");
                    message.setText("Dear : " + email + "\n Activation code is : " + createRandomCode() + "\n Thank your for registration");
                    Transport.send(message);


                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();

        Toast.makeText(this, "Activation code has been sent \n check your email", Toast.LENGTH_LONG).show();

    }

}


