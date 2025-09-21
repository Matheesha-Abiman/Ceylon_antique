package lk.ijse.backend.service.impl;

import jakarta.mail.internet.MimeMessage;
import lk.ijse.backend.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MailSendServiceImpl implements MailSendService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private String loginTemplate(String userName) {
        return String.format("""
        <html>
          <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #faf9f7; margin: 0; padding: 0;">
            <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px;
                        border: 1px solid #e6e2df; box-shadow: 0 4px 10px rgba(0,0,0,0.05); overflow: hidden;">
              
              <div style="background: linear-gradient(135deg, #8B4513, #d9b08c); padding: 15px; text-align: center; color: #fff;">
                <h2 style="margin: 0; font-size: 22px;">🔐 Login Alert</h2>
                <p style="margin: 0; font-size: 14px;">Ceylon Antique Marketplace</p>
              </div>
              
              <div style="padding: 25px; color: #333;">
                <p style="font-size: 16px;">Dear <strong>%s</strong>,</p>
                <p style="font-size: 15px;">We noticed a login to your <strong>Ceylon Antique Marketplace</strong> account.</p>
                
                <div style="margin: 20px 0; text-align: center;">
                  <span style="display: inline-block; padding: 10px 20px; background: #fef3e6; border: 1px solid #f0c087;
                               color: #8B4513; border-radius: 6px; font-size: 14px;">
                    If this wasn’t you, reset your password immediately.
                  </span>
                </div>
                
                <p style="font-size: 12px; color: #888;">This is an automated email. Please do not reply.</p>
              </div>
              
              <div style="background-color: #faf3e9; padding: 12px; text-align: center; font-size: 12px; color: #666;">
                © Ceylon Antique Marketplace
              </div>
            </div>
          </body>
        </html>
        """, userName);
    }

    private String registrationTemplate(String userName) {
        String dateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format("""
        <html>
          <body style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #faf9f7; margin: 0; padding: 0;">
            <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px;
                        border: 1px solid #e6e2df; box-shadow: 0 4px 10px rgba(0,0,0,0.05); overflow: hidden;">
              
              <div style="background: linear-gradient(135deg, #d9b08c, #8B4513); padding: 15px; text-align: center; color: #fff;">
                <h2 style="margin: 0; font-size: 22px;">🎉 Welcome to Ceylon Antique!</h2>
                <p style="margin: 0; font-size: 14px;">Your journey into antiques begins today</p>
              </div>
              
              <div style="padding: 25px; color: #333;">
                <p style="font-size: 16px;">Hello <strong>%s</strong>,</p>
                <p style="font-size: 15px;">Thank you for registering with <strong>Ceylon Antique Marketplace</strong>. 
                   Your account is ready to explore rare antiques and unique collections!</p>
                
                <p style="margin: 15px 0; font-size: 14px; background: #faf3e9; padding: 10px; border-left: 4px solid #d9b08c;">
                  <strong>Registration Date &amp; Time:</strong> %s
                </p>
                
                <div style="text-align: center; margin: 20px 0;">
                  <a href="https://ceylon-antique.lk" 
                     style="text-decoration: none; padding: 12px 24px; background: #8B4513; color: #fff; 
                            border-radius: 6px; font-size: 14px;">
                     Start Exploring
                  </a>
                </div>
                
                <p style="font-size: 12px; color: #888;">This is an automated email. Please do not reply.</p>
              </div>
              
              <div style="background-color: #faf3e9; padding: 12px; text-align: center; font-size: 12px; color: #666;">
                © Ceylon Antique Marketplace
              </div>
            </div>
          </body>
        </html>
        """, userName, dateTime);
    }

    @Override
    public void sendRegisteredEmail(String name, String email) {
        sendEmail(email, "Welcome to Our App!", registrationTemplate(name));
    }

    @Override
    public void sendLoggedInEmail(String userName, String email) {
        sendEmail(email, "Login Alert", loginTemplate(userName));
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
