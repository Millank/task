package com.crud.tasks.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"admin.mail=test@test.test"})
public class AdminConfigTest {
    @Autowired
    private AdminConfig adminConfig = new AdminConfig();

    @Test
    public void testGetAdminMail() {
        // Given & When & Then
        assertEquals("test@test.test", adminConfig.getAdminMail());
    }
}