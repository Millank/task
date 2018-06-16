package com.crud.tasks.domain;

import com.crud.tasks.service.MailMessageType;
import org.junit.Test;

import static org.junit.Assert.*;

public class MailTest {
    @Test
    public void testGettersAfterAllArgsConstructor(){
        // Given
        Mail mail1 = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", "toCC");
        Mail mail2 = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", null);

        // When and Then
        assertEquals(MailMessageType.SIMPLE_MESSAGE, mail1.getMessageType());
        assertEquals("test_email", mail1.getReceiverEmail());
        assertEquals("test_subject", mail1.getSubject());
        assertEquals("test_message", mail1.getMessage());
        assertEquals("toCC", mail1.getToCc());

        assertNull("toCC", mail2.getToCc());

    }

    @Test
    public void testGettersAfterRequiredArgsConstructor(){
        // Given
        Mail mail = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message");

        // When and Then
        assertEquals(MailMessageType.SIMPLE_MESSAGE, mail.getMessageType());
        assertEquals("test_email", mail.getReceiverEmail());
        assertEquals("test_subject", mail.getSubject());
        assertEquals("test_message", mail.getMessage());
        assertNull("toCC", mail.getToCc());
    }

    @Test
    public void testEquals() {
        // Given
        Mail mail1a = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", "toCC");
        Mail mail1b = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", "toCC");
        Mail mail2a  = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message");
        Mail mail2b = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message");
        Mail mailDiff1 = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email_diff", "test_subject", "test_message", "toCC");
        Mail mailDiff2 = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject_diff", "test_message", "toCC");
        Mail mailDiff3 = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message_diff", "toCC");
        Mail mailDiff4  = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", "toCC_diff");

       // When and Then
        assertTrue(mail1a.equals(mail1a));
        assertTrue(mail1a.equals(mail1b));
        assertTrue(mail2a.equals(mail2a));
        assertTrue(mail2a.equals(mail2b));
        assertFalse(mail1a.equals(mail2a));
        assertFalse(mail2a.equals(mail1a));

        assertFalse(mail1a.equals(mailDiff1));
        assertFalse(mail1a.equals(mailDiff2));
        assertFalse(mail1a.equals(mailDiff3));
        assertFalse(mail1a.equals(mailDiff4));
        assertFalse(mail1a.equals(null));
    }

    @Test
    public void testHashCode() {
        // Given
        Mail mail1a = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", "toCC");
        Mail mail1b = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message", "toCC");
        Mail mail2a  = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message");
        Mail mail2b = new Mail(MailMessageType.SIMPLE_MESSAGE, "test_email", "test_subject", "test_message");

        // When and Then
        assertTrue(mail1a.hashCode() == mail1b.hashCode());
        assertTrue(mail2a.hashCode() == mail2b.hashCode());
    }
}