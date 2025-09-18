package chatbot.taskhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import chatbot.exceptions.LeoException;

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
                    + "Please use YYYY-MM-DD or YYYY-MM-DD HHMM format. Invalid input: 'Sunday'", e.getMessage());
        }

    }

    @Test
    public void testLoadDataFromFile() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();
        String filePath = tempFile.getAbsolutePath();
        String taskData = "T | 0 | Read a book\n"
                + "D | 1 | Finish project | 2025-12-31 1800\n"
                + "E | 0 | Meeting | 2025-08-30 | 2025-08-31";

        FileWriter writer = new FileWriter(filePath);
        writer.write(taskData);
        writer.close();

        TaskManager manager = new TaskManager(filePath);

        assertEquals(3, manager.getTodoList().size());
        assertEquals("[T] [ ] Read a book", manager.getTodoList().get(0).toString());
        assertEquals("[D] [X] Finish project (by: Dec 31 2025 18:00)", manager.getTodoList().get(1).toString());
        assertEquals("[E] [ ] Meeting (from: Aug 30 2025 00:00 to: Aug 31 2025 00:00)",
                manager.getTodoList().get(2).toString());
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
        assertEquals("[T] [X] Read a book", manager.getTodoList().get(0).toString());
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
        assertEquals("[T] [X] Read a book", manager.getTodoList().get(0).toString());

        String[] unmarkCommand = {"unmark", "1"};
        manager.unmarkTask(unmarkCommand);
        assertEquals("[T] [ ] Read a book", manager.getTodoList().get(0).toString());
    }

    @Test
    public void deleteTaskTest() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);
        assertEquals(1, manager.getTodoList().size());

        String[] deleteCommand = {"delete", "1"};
        manager.deleteTask(deleteCommand);
        assertEquals(0, manager.getTodoList().size());
    }

    @Test
    public void testSortDeadlineTask() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task deadline1 = new Deadline("Submit assignment", "2023-10-01 1800");
        Task deadline2 = new Deadline("Project meeting", "2023-09-15 1400");
        Task todo = new ToDo("Read a book");
        manager.addTask(deadline1);
        manager.addTask(deadline2);
        manager.addTask(todo);

        String sortedDeadlines = manager.sortDeadlineTask();
        String expectedOutput = "Here are the deadline tasks due soon:\n"
                + "2. [D] [ ] Project meeting (by: Sep 15 2023 14:00)\n"
                + "1. [D] [ ] Submit assignment (by: Oct 1 2023 18:00)";
        assertEquals(expectedOutput, sortedDeadlines);
    }

    @Test
    public void testSortDeadlineTaskNoDeadlineTasks() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);

        String result = manager.sortDeadlineTask();
        assertEquals("YAY no dues yet!", result);
    }

    @Test
    public void testUpdateTask() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task deadline = new Deadline("Submit assignment", "2025-10-01 1200");
        manager.addTask(deadline);

        String input = "edit 1 /name Submit project /by 2025-11-01 1200";
        String result = manager.updateTask(input);
        System.out.println(result);

        assertTrue(result.contains("Submit project"));
        assertTrue(result.contains("by: Nov 1 2025 12:00"));
    }

    @Test
    public void testUpdateTaskNonIntegerIndex() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);

        // Test non-integer task number
        String input = "edit abc /name New task";
        try {
            manager.updateTask(input);
        } catch (LeoException e) {
            assertEquals("UH-OH!!! Task number must be an integer. Use edit <integer>", e.getMessage());
        }
    }

    @Test
    public void testUpdateTaskNoFields() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);

        // Test no fields to update
        String input = "edit 1 some random text";
        try {
            manager.updateTask(input);
        } catch (LeoException e) {
            assertEquals("UH-OH!!! No fields to update. "
                    + "Use /name, /by, /from, or /to to specify fields.", e.getMessage());
        }
    }

    @Test
    public void testUpdateTaskInvalidTaskNumber() throws IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);

        String input = "edit 1 /name Submit project";
        try {
            manager.updateTask(input);
        } catch (LeoException e) {
            assertEquals("UH-OH!!! Invalid task number.", e.getMessage());
        }
    }

    @Test
    public void testFindTasks() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo1 = new ToDo("Read a book");
        Task todo2 = new ToDo("Write a book");
        Task deadline = new Deadline("Submit assignment", "2023-10-01 1800");
        manager.addTask(todo1);
        manager.addTask(todo2);
        manager.addTask(deadline);

        String[] findCommand = {"find", "book"};
        String result = manager.findTasks(findCommand);

        String expectedOutput = "Here are the matching tasks in your list:\n"
                + "1. [T] [ ] Read a book\n"
                + "2. [T] [ ] Write a book";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testFindTasksNoMatch() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        manager.addTask(todo);

        String[] command = {"find", "workshop"};
        String result = manager.findTasks(command);
        assertEquals("No matching tasks found.", result);
    }

    @Test
    public void testPrintList() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());
        Task todo = new ToDo("Read a book");
        Task deadline = new Deadline("Submit assignment", "2023-10-01 1800");
        manager.addTask(todo);
        manager.addTask(deadline);

        String result = manager.printList();
        String expectedOutput = "Here are the tasks in your list:\n"
                + "1. [T] [ ] Read a book\n"
                + "2. [D] [ ] Submit assignment (by: Oct 1 2023 18:00)";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testPrintListEmpty() throws LeoException, IOException {
        File tempFile = File.createTempFile("tempfile", ".txt");
        tempFile.deleteOnExit();

        TaskManager manager = new TaskManager(tempFile.getAbsolutePath());

        String result = manager.printList();
        assertEquals("Your task list is empty.", result);
    }

}
