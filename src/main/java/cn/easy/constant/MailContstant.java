package cn.easy.constant;

import cn.easy.business.constant.Code;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by zhangrui on 2018/5/10.
 * 邮件发送功能 org.apache.commons.mail
 *
 */
@Component
@Scope("prototype")
@PropertySource(value = "classpath:business/read.properties", encoding = "UTF-8")
public class MailContstant {
    private final Logger logger = Logger.getLogger(MailContstant.class);
    //默认企业邮箱
    @Value("${MAIL_HOST:smtp.exmail.qq.com}")
    private String HOST;

    @Value("${MAIL_FROM}")
    private String FROM;
    @Value("${MAIL_FROM_DESCRIPTION}")
    private String FROM_DESCRIPTION;
    @Value("${MAIL_FROM_PWD}")
    private String FROM_PASSWORD;

    //默认端口465
    @Value("${SMTP_PORT:465}")
    private static int SMTP_PORT = 465;

    public Code sendMail(String toAddress, String htmlMsg, String subject) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(HOST);// 设置使用发电子邮件的邮件服务器，这里以qq邮箱为例（其它例如：【smtp.163.com】，【smtp.sohu.com】）
        try {
            // 收件人邮箱
            email.addTo(toAddress);
            // 邮箱服务器身份验证
            email.setAuthentication(FROM, FROM_PASSWORD);
            // 发件人邮箱
            email.setFrom(FROM, FROM_DESCRIPTION);
            email.setSmtpPort(SMTP_PORT);
            //设置字符编码
            email.setCharset("UTF-8");
            //开启ssl
            email.setSSLOnConnect(true);
            // 邮件主题
            email.setSubject(subject);
            // 邮件内容
            email.setHtmlMsg(htmlMsg);
            // 发送邮件
            email.send();
        } catch (EmailException ex) {
            logger.error("邮件发送失败" + ex.getMessage());
            ex.printStackTrace();
            return Code.MAIL_SEND_ERROR;
        }
        logger.info("邮件发送成功,目标邮箱" + toAddress + "!");
        return Code.SUCCESS;
    }

    //带附件
    public Code sendMail(String toAddress, String htmlMsg, String subject, EmailAttachment attachment) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(HOST);// 设置使用发电子邮件的邮件服务器，这里以qq邮箱为例（其它例如：【smtp.163.com】，【smtp.sohu.com】）
        try {
            // 收件人邮箱
            email.addTo(toAddress);
            // 邮箱服务器身份验证
            email.setAuthentication(FROM, FROM_PASSWORD);
            // 发件人邮箱
            email.setFrom(FROM, FROM_DESCRIPTION);
            email.setSmtpPort(SMTP_PORT);
            //设置字符编码
            email.setCharset("UTF-8");
            //开启ssl
            email.setSSLOnConnect(true);
            // 邮件主题
            email.setSubject(subject);
            // 邮件内容
            email.setHtmlMsg(htmlMsg);
            //添加附件
            email.attach(attachment);
            // 发送邮件
            email.send();
        } catch (EmailException ex) {
            logger.error("邮件发送失败" + ex.getMessage());
            ex.printStackTrace();
            return Code.MAIL_SEND_ERROR;
        }
        return Code.SUCCESS;
    }

}
