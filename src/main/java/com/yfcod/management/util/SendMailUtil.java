package com.yfcod.management.util;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class SendMailUtil {
    private static final String sendMailAddress = "yfcodedream@163.com";
    private static final String sendMailNickName = "YFCodeDream";
    private static final String sendMailNickNameEncoding = "UTF-8";

    private static final String sendMailTitleHeader = "厦门大学考试管理信息系统 - ";

    private static final Logger logger = Logger.getLogger(SendMailUtil.class);

    public static void sendMail(String receiveMailAddress,
                                String subTitle,
                                String tempFilePath,
                                String fileSuffix) {
        logger.info("sending mail -----");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.163.com");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties);
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(new InternetAddress(
                    sendMailAddress,
                    sendMailNickName,
                    sendMailNickNameEncoding
            ));
            mimeMessage.setRecipient(
                    MimeMessage.RecipientType.TO,
                    new InternetAddress(
                            receiveMailAddress,
                            sendMailNickName,
                            sendMailNickNameEncoding
                    )
            );
            mimeMessage.setSubject(
                    sendMailTitleHeader + subTitle,
                    sendMailNickNameEncoding
            );
            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(
                    "厦门大学考试管理信息系统导出：" + subTitle + "，已发送，请查收",
                    "text/html;charset=UTF-8"
            );

            MimeBodyPart excelTempFilePart = new MimeBodyPart();
            DataHandler excelTempFileHandler = new DataHandler(
                    new FileDataSource(tempFilePath)
            );
            excelTempFilePart.setDataHandler(excelTempFileHandler);
            excelTempFilePart.setFileName(subTitle + fileSuffix);

            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(excelTempFilePart);

            mimeMessage.setContent(multipart);

            mimeMessage.setSentDate(new Date());
            mimeMessage.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(
                    "smtp.163.com",
                    sendMailAddress,
                    "CLVLHXNUIMQJSWNB");
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

            logger.info(Calendar.getInstance().getTime() + " : # Send mail to " + " success -----");
        } catch (MessagingException | UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
