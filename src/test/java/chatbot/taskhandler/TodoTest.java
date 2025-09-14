package chatbot.taskhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for ToDo.
 */
public class TodoTest {
    @Test
    public void testToDoCreation() {
        ToDo todo = new ToDo("Read a book");
        assertEquals("[T] [ ] Read a book", todo.toString());
        assertEquals("T | 0 | Read a book", todo.formatData());
    }
}
