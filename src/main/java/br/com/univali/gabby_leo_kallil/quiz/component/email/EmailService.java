package br.com.univali.gabby_leo_kallil.quiz.component.email;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class EmailService {


    private final JavaMailSender emailSender;
    private final String mail = "naoresponda@vamostreinar.com";

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendWelcome(String to, String password){
        String subject = "Bem vindo ao Vamos Treinar";
        String message = "<h3>Bem vindo ao Vamos Treinar</h3>\r\n" +
                "<p>Para acessar o site utilize o seu endereço de e-mail a seguinte senha: " +
                password +"</p>\r\n" +
                "<p>Link para a plataforma:</p>\r\n" +
                /*"<p><a href='https://play.google.com/store/apps/details?id=br.com.espacosaude.vamostreinar.letstrain&hl=pt_BR&gl=US'>android</a></p>\r\n" +*/
                "<p><a href='https://vamostreinar.com'>site</a></p>\r\n" +
                /*"<p>IOS - em construção</p>\r\n" +*/
                "<p>&nbsp;</p>";

        send(to, subject, message);
    }

    public void sendResetPasswordEmail(String to, String code) {
        String subject = "Vamos Treinar - redefinição de senha";
        String message = "<p>Informe o c&oacute;digo de acesso:</p>\r\n" +
                "<p><strong>"+code+"</strong></p>\r\n" +
                "<p>&nbsp;</p>";

        send(to, subject, message);
    }


    private void send(String to, String subject, String message) throws MailException{
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, CharEncoding.UTF_8);
            messageHelper.setFrom(mail);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
        };
        emailSender.send(messagePreparator);
    }

}