package chatbot.taskhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for Task.
 */
public class TaskTest {
    @Test
    public void testToDoCreation() {
        Task task = new Task("Read a book");
        assertEquals("[ ] Read a book", task.toString());
    }

    @Test
    public void testSetDone() {
        Task task = new Task("Read a book");
        task.setDone(true);
        assertEquals("[X] Read a book", task.toString());
    }

}
