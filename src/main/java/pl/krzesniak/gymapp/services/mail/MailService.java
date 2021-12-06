package pl.krzesniak.gymapp.services.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.EmailDTO;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailConfiguration mailConfiguration;
    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(EmailDTO emailDTO){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(mailConfiguration.getHost());
            mimeMessageHelper.setTo(emailDTO.getRecipient());
            mimeMessage.setSubject(emailDTO.getSubject());
            mimeMessage.setText(emailDTO.getBody());
        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
