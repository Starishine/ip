# Leo User Guide

Leo is a friendly chatbot that helps you manage your tasks efficiently. Whether you need to track simple to-dos, set deadlines, or organize events, Leo has got you covered!

![Leo chatbot interface](/docs/Ui.png "Leo chatbot interface")

## Quick Start

1. Launch the Leo application
2. Start typing commands in the input field
3. Press Enter or click the Send button to interact with Leo
4. Type `bye` to exit the application

## Features

### Adding Tasks

Leo supports three types of tasks: ToDo, Deadline, and Event.

#### Adding a ToDo task
Adds a simple task without any date/time.

**Format:** `todo DESCRIPTION`

**Example:** `todo read book`

**Expected outcome:**
```
Got it! I've added this task: [T][ ] read book
Now you have 1 tasks in the list.
```

#### Adding a Deadline task
Adds a task with a due date.

**Format:** `deadline DESCRIPTION /by DATE [TIME]`

**Date formats supported:**
- `YYYY-MM-DD` (time defaults to 00:00)
- `YYYY-MM-DD HHMM` (24-hour format)

**Example:** `deadline submit assignment /by 2025-09-16 2359`

**Expected outcome:**
```
Got it! I've added this task: [D][ ] submit assignment (by: Sept 16 2025 23:59)
Now you have 2 tasks in the list.
```

#### Adding an Event task
Adds a task with both start and end dates.

**Format:** `event DESCRIPTION /from START_DATE [TIME] /to END_DATE [TIME]`

**Example:** `event team meeting /from 2025-10-10 1400 /to 2024-10-10 1600`

**Expected outcome:**
```
Got it! I've added this task: [E][ ] team meeting (from: Oct 10 2025 14:00 to: Oct 10 2025 16:00)
Now you have 3 tasks in the list.
```

### Managing Tasks

#### Listing all tasks
Shows all tasks in your list with their completion status.

**Format:** `list`

**Expected outcome:**
```
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] submit assignment (by: Sept 16 2025 23:59)
3. [E][ ] team meeting (from: Oct 10 2025 14:00 to: Oct 10 2025 16:00)
```

#### Marking tasks as done
Marks a task as completed.

**Format:** `mark TASK_NUMBER`

**Example:** `mark 1`

**Expected outcome:**
```
Marked as done: [T][X] read book
```

#### Unmarking tasks
Marks a task as not completed.

**Format:** `unmark TASK_NUMBER`

**Example:** `unmark 1`

**Expected outcome:**
```
Marked as not done: [T][ ] read book
```

#### Deleting tasks
Permanently removes a task from your list.

**Format:** `delete TASK_NUMBER`

**Example:** `delete 3`

**Expected outcome:**
```
Removed Task: [D][ ] submit assignment (by: Sept 16 2025 23:59)
Now you have 2 tasks in the list.
```

### Finding Tasks

#### Searching for tasks
Finds tasks that contain the specified keyword (case-insensitive).

**Format:** `find KEYWORD`

**Example:** `find meeting`

**Expected outcome:**
```
Here are the matching tasks in your list:
3. [E][ ] team meeting (from: Oct 10 2025 14:00 to: Oct 10 2025 16:00)
```

### Organizing Tasks

#### Viewing upcoming deadlines
Shows all incomplete deadline tasks sorted by due date.

**Format:** `due`

**Expected outcome:**
```
Here are the deadline tasks due soon:
2. [D][ ] submit assignment (by: Sept 16 2025 23:59)
```

### Editing Tasks

#### Updating task details
Modify existing tasks by changing their name, dates, or other attributes.

**Format:** `edit TASK_NUMBER /FIELD VALUE [/FIELD VALUE ...]`

**Available fields:**
- `/name` - Change task description
- `/by` - Change deadline (for Deadline tasks)
- `/from` - Change start date (for Event tasks)
- `/to` - Change end date (for Event tasks)

**Examples:**
- `edit 1 /name finish reading book`
- `edit 2 /by 2024-12-20 1800`
- `edit 3 /name project meeting /from 2024-12-11 1000`

**Expected outcome:**
```
Task updated: [T][ ] finish reading book
```

## Data Storage

Leo automatically saves your tasks to a file (`data/leo.txt`) every time you make changes. Your tasks will be restored when you restart the application.

## Error Handling

Leo will show helpful error messages when:
- Commands are in the wrong format
- Task numbers are invalid
- Required information is missing
- Date formats are incorrect

**Example error messages:**
- `UH-OH!!! Invalid task number.`
- `UH-OH!!! The dueDate format is invalid. Please use YYYY-MM-DD or YYYY-MM-DD HHMM format.`
- `UH-OH!!! Cannot create task: Description cannot be empty for 'todo'.`

## Command Summary

| Command | Format | Purpose |
|---------|--------|---------|
| `todo` | `todo DESCRIPTION` | Add a simple task |
| `deadline` | `deadline DESCRIPTION /by DATE [TIME]` | Add a task with deadline |
| `event` | `event DESCRIPTION /from START /to END` | Add an event with start and end times |
| `list` | `list` | Show all tasks |
| `mark` | `mark TASK_NUMBER` | Mark task as done |
| `unmark` | `unmark TASK_NUMBER` | Mark task as not done |
| `delete` | `delete TASK_NUMBER` | Remove a task |
| `find` | `find KEYWORD` | Search for tasks |
| `due` | `due` | Show upcoming deadlines |
| `edit` | `edit TASK_NUMBER /FIELD VALUE` | Update task details |
| `bye` | `bye` | Exit the application |

## Tips

1. **Task numbers** start from 1 and correspond to the order shown in `list`
2. **Date formats** must be exact: `YYYY-MM-DD` or `YYYY-MM-DD HHMM`
3. **Time format** uses 24-hour notation (e.g., 1400 for 2:00 PM)
4. **Keywords** in `find` are case-insensitive and match partial words
5. **Multiple fields** can be edited in a single `edit` command
6. Leo will automatically save your changes, so you don't lose your tasks!