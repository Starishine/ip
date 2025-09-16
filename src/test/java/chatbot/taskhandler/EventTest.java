package chatbot.taskhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import chatbot.exceptions.LeoException;

public class EventTest {

    // Test valid Event creation with correct dates
    @Test
    public void testEventCreationValid() throws LeoException {
        String name = "Team Meeting";
        String startDate = "2025-09-20";
        String endDate = "2025-09-21";

        Event event = new Event(name, startDate, endDate);

        assertEquals("[E] [ ] Team Meeting (from: Sep 20 2025 00:00 to: Sep 21 2025 00:00)", event.toString());
        assertEquals("E | 0 | Team Meeting | 2025-09-20 | 2025-09-21", event.formatData());
    }

    // Test invalid date format for start date
    @Test
    public void testEventCreationInvalidStartDate() {
        String name = "Team Meeting";
        String invalidStartDate = "2025-13-20";  // Invalid month

        assertThrows(LeoException.class, () -> {
            new Event(name, invalidStartDate, "2025-09-21");
        });
    }

    // Test invalid date format for end date
    @Test
    public void testEventCreationInvalidEndDate() {
        String name = "Team Meeting";
        String invalidEndDate = "2025-09-32";  // Invalid day

        assertThrows(LeoException.class, () -> {
            new Event(name, "2025-09-20", invalidEndDate);
        });
    }

    // Test Event creation with correct dates but reversed start and end dates
    @Test
    public void testEventCreationReversedDates() throws LeoException {
        String name = "Team Meeting";
        String startDate = "2025-09-21";  // Start after the end
        String endDate = "2025-09-20";

        Event event = new Event(name, startDate, endDate);

        assertEquals("[E] [ ] Team Meeting (from: Sep 21 2025 00:00 to: Sep 20 2025 00:00)", event.toString());
        assertEquals("E | 0 | Team Meeting | 2025-09-21 | 2025-09-20", event.formatData());
    }
}

