package chatbot.taskhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import chatbot.exceptions.LeoException;

public class DeadlineTest {

    // Test valid Deadline creation with correct date format
    @Test
    public void testDeadlineCreationValid() throws LeoException {
        String name = "Submit Assignment";
        String dueDate = "2025-10-01 1800";

        Deadline deadline = new Deadline(name, dueDate);

        assertEquals("[D] [ ] Submit Assignment (by: Oct 1 2025 18:00)", deadline.toString());
        assertEquals("D | 0 | Submit Assignment | 2025-10-01 1800", deadline.formatData());
    }

    // Test valid Deadline creation with only the date (no time)
    @Test
    public void testDeadlineCreationValidDateOnly() throws LeoException {
        String name = "Read Book";
        String dueDate = "2025-09-30";

        Deadline deadline = new Deadline(name, dueDate);

        assertEquals("[D] [ ] Read Book (by: Sep 30 2025 00:00)", deadline.toString());
        assertEquals("D | 0 | Read Book | 2025-09-30", deadline.formatData());
    }

    // Test invalid due date format (invalid month)
    @Test
    public void testDeadlineCreationInvalidMonth() {
        String name = "Submit Report";
        String invalidDueDate = "2025-13-01 1200"; // Invalid month

        assertThrows(LeoException.class, () -> {
            new Deadline(name, invalidDueDate);
        });
    }
}

