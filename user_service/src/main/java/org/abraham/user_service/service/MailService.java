package org.abraham.user_service.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.abraham.user_service.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSenderImpl sender;
    private final FreeMarkerConfig freeMarkerConfig;


    public void sendRegistrationEmail(UserEntity user, String url, String companyName, String companyWebsiteUrl) throws MessagingException, IOException, TemplateException {
       var message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("User Registration");
        helper.setTo(user.getEmail());

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);  // Pass the user object (ensure it has the right properties)
        model.put("verificationUrl", url);  // Verification URL
        model.put("companyName", companyName);  // Company name (you can use this in the footer)
        model.put("companyWebsiteUrl", companyWebsiteUrl);  // Company website URL for footer or other use

        // Load the template
        Template template = freeMarkerConfig.getConfiguration().getTemplate("registration_template.ftl");

        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setText(htmlContent, true);
        sender.send(message);
    }
}
