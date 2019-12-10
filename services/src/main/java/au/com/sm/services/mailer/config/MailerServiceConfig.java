package au.com.sm.services.mailer.config;

import au.com.sm.client.mailgun.MailGunClient;
import au.com.sm.client.sendgrid.SendGridClient;
import au.com.sm.services.mailer.manager.MailerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailerServiceConfig {

    @Autowired
    private MailGunClient mailGunClient;

    @Autowired
    private SendGridClient sendGridClient;

    @Bean
    public MailerManager getMailerManager() {

        // LinkedHashSet should still keep the order of the clients
        MailerManager manager = new MailerManager();
        manager.addProvider(mailGunClient);
        manager.addProvider(sendGridClient);
        return manager;
    }
}
