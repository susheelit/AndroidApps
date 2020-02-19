package com.aob.aobsalesman.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Belal on 10/30/2015.
 */

//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendMail(Context context, String email, String subject, String message){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
       // Toast.makeText(context,"Message Sent", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO code application logic here
       // Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
            }
        });

        // -- Create a new message --
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        try {
            msg.setFrom(new InternetAddress(Config.EMAIL));
        } catch (MessagingException e) {
        //    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email, false));
        } catch (MessagingException e) {
        //    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            msg.setSubject(subject);
            msg.setText(message);
            msg.setSentDate(new Date());
        } catch (MessagingException e) {
       //     Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        try {
            Transport.send(msg);
        } catch (MessagingException e) {
        //    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

       // System.out.println("Message sent.");
        return null;
    }
    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
   /* public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    *//**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     *//*

    *//**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to Email address of the receiver.
     * @param from Email address of the sender, the mailbox account.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @param file Path to the file to be attached.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     *//*
    public static MimeMessage createEmailWithAttachment(String to,
                                                        String from,
                                                        String subject,
                                                        String bodyText,
                                                        File file)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);

        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(file.getName());

        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        return email;
    }*/

}