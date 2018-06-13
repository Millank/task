package com.crud.tasks.Controller;

import com.crud.tasks.controller.InvalidTaskException;
import org.junit.Test;
import  com.crud.tasks.controller.InvalidTaskException.InvalidTaskReason;

import static org.junit.Assert.assertEquals;

public class InvalidTaskExceptionTest {
    @Test
    public void testUseInvalidTaskReason() {
        // Given
        InvalidTaskReason invalidTaskReason = InvalidTaskReason.values()[0];
        InvalidTaskException invalidTaskException = new InvalidTaskException(invalidTaskReason);
        try {
            // When
            throw invalidTaskException;
        } catch (InvalidTaskException e) {
            // Then
            assertEquals(invalidTaskReason.toString(), e.getMessage());
        }
    }
}


