package org.abraham.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;


@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSenderImpl javaMailSender()  {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);

        var props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return sender;
    }

    @Bean
    public FreeMarkerConfig freeMarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/");

        // Optional: Set FreeMarker settings
//        Properties settings = new Properties();
//        settings.setProperty("template_exception_handler", "html_debug"); // Example exception handling
//        configurer.setFreemarkerSettings(settings);

        // Optional: Set shared variables
//        Map<String, Object> freemarkerVariables = new HashMap<>();
//        freemarkerVariables.put("xml_escape", new XmlEscape()); // Example: Add XML escape utility
//        configurer.setFreemarkerVariables(freemarkerVariables);

        return configurer;
    }
}
