package chatbot.taskhandler;

import chatbot.exceptions.LeoException;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for TaskManager.
 */
public class TaskManagerTest {

    @Test
    public void testCreateTaskTestSuccess() throws LeoException, IOException {

        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = manager.createTask("todo Read a book");
        assertEquals("[T] [ ] Read a book", todo.toString());
        assertTrue(todo instanceof ToDo);

        Task deadline = manager.createTask("deadline Submit assignment /by 2023-10-01 1800");
        assertEquals("[D] [ ] Submit assignment (by: Oct 1 2023 18:00)", deadline.toString());
        assertTrue(deadline instanceof Deadline);

    }

    @Test
    public void testCreateTaskTestFailure() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();
        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());

        // Test for missing description in todo
        try {
            manager.createTask("todo");
        } catch (LeoException e) {
            assertEquals("UH-OH!!!! Cannot create task: Description cannot be empty for 'todo'.", e.getMessage());
        }
        // Test for missing description in deadline
        try {
            manager.createTask("deadline Submit assignment");
        } catch (LeoException e) {
            assertEquals("UH-OH!!!! Cannot create task: Due date cannot be empty for 'deadline'.", e.getMessage());
        }

        // Test invalid date format in deadline
        try {
            manager.createTask("deadline Submit assignment /by Sunday");
        } catch (LeoException e) {
            assertEquals("UH-OH!!! The dueDate format is invalid. "
                    + "Please use YYYY-MM-DD or YYYY-MM-DD HHMM format.", e.getMessage());
        }

    }

    @Test
    public void testLoadDataFromFile() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();
        String taskData = "T | 0 | Read a book\n" +
                "D | 1 | Finish project | 2025-12-31 1800\n" +
                "E | 0 | Meeting | 2025-08-30 | 2025-08-31";

        FileWriter writer = new FileWriter(filePath);
        writer.write(taskData);
        writer.close();

        TaskManager manager = new TaskManager(filePath);

        assertEquals(3, manager.todoList.size());
        assertEquals("[T] [ ] Read a book", manager.todoList.get(0).toString());
        assertEquals("[D] [X] Finish project (by: Dec 31 2025 18:00)", manager.todoList.get(1).toString());
        assertEquals("[E] [ ] Meeting (from: Aug 30 2025 to: Aug 31 2025)", manager.todoList.get(2).toString());
    }

    @Test
    public void markTaskTest() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);
        String[] command = {"mark", "1"};
        manager.markTask(command);
        assertEquals("[T] [X] Read a book", manager.todoList.get(0).toString());
    }

    @Test
    public void markTaskTestFailure() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);
        String[] command = {"mark", "2"};
        try {
            manager.markTask(command);
        } catch (LeoException e) {
            assertEquals("UH-OH!!! Invalid task number.", e.getMessage());
        }
    }

    @Test
    public void unmarkTaskTest() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);
        String[] markCommand = {"mark", "1"};
        manager.markTask(markCommand);
        assertEquals("[T] [X] Read a book", manager.todoList.get(0).toString());

        String[] unmarkCommand = {"unmark", "1"};
        manager.unmarkTask(unmarkCommand);
        assertEquals("[T] [ ] Read a book", manager.todoList.get(0).toString());
    }

    @Test
    public void deleteTaskTest() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);
        assertEquals(1, manager.todoList.size());

        String[] deleteCommand = {"delete", "1"};
        manager.deleteTask(deleteCommand);
        assertEquals(0, manager.todoList.size());
    }

}
