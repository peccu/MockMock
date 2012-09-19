package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

public class MailListHtmlBuilder implements HtmlBuilder
{
    private ArrayList<MockMail> mailQueue;

    public void setMailQueue(ArrayList<MockMail> mailQueue)
    {
        this.mailQueue = mailQueue;
    }

    public String build()
    {
        String output =
                    "<div class=\"container\">\n";

        if(mailQueue == null || mailQueue.size() == 0)
        {
            output += "  <h2>Hmm, no mails yet. That makes me sad :'(</h2>\n";
        }
        else
        {
            String mailText = mailQueue.size() == 1 ? "mail" : "mails";
            output += "  <h1>I've got " + mailQueue.size() + " " + mailText + " for you. Nice!</h1>\n";
            output += "  <table class=\"table table-striped\">\n";
            output += "    <thead>\n";
            output += "      <th>From</th>\n";
            output += "      <th>To</th>\n";
            output += "      <th>Subject</th>\n";
            output += "    </thead>\n";
            output += "    <tbody>\n";
            for (MockMail mail : mailQueue)
            {
                output += buildMailRow(mail);
            }
            output += "    </tbody>\n";
            output += "  </table>\n";
        }

        output += "</div>\n";

        return output;
    }

    private String buildMailRow(MockMail mail)
    {
        StringFromHtmlBuilder fromBuilder = new StringFromHtmlBuilder();
        fromBuilder.setMockMail(mail);
        String fromOutput = fromBuilder.build();

        StringRecipientHtmlBuilder recipientBuilder = new StringRecipientHtmlBuilder();
        recipientBuilder.setMaxLength(27);
        recipientBuilder.setMockMail(mail);
        recipientBuilder.setRecipientType(MimeMessage.RecipientType.TO);
        String toOutput = recipientBuilder.build();

        return
            "<tr>\n" +
            "  <td>" + fromOutput + "</td>\n" +
            "  <td>" + toOutput + "</td>\n" +
            "  <td><a title=\"" + StringEscapeUtils.escapeHtml4(mail.getSubject()) + "\" href=\"/view/" + mail.getId() + "\">" + StringEscapeUtils.escapeHtml4(mail.getSubject()) + "</a></td>\n" +
            "</tr>";
    }
}