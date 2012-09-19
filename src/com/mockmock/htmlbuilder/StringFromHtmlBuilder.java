package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class StringFromHtmlBuilder implements HtmlBuilder
{
    private MockMail mockMail;

    public String build()
    {
        String output = "";
        MimeMessage mimeMessage = mockMail.getMimeMessage();

        try
        {
            Address[] addresses = mimeMessage.getFrom();
            int i = 1;
            for(Address address : addresses)
            {
                output += StringEscapeUtils.escapeHtml4(address.toString());
                if(addresses.length != i)
                {
                    output += ", ";
                }

                i++;
            }
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

        return output;
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }
}