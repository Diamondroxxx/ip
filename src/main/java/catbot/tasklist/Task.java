package catbot.tasklist;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import catbot.CatBotException;
import catbot.ui.ConsoleColors;

/**
 * The parent class of all tasks.
 */
public abstract class Task {
    protected boolean isDone;
    final String description;

    /**
     * Enum to express the type of task.
     */
    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * Constructor for a CatBot.TaskList.Task
     * @param description is the description of the task
     */
    public Task(String description) {
        this.description = description;
        isDone = false;
    }

    /**
     * Creates a CatBot.TaskList.Task of a certain type based on a given command
     * @param command is the input typed in by the user
     * @return the relevant CatBot.TaskList.Task subclass
     * @throws CatBotException if the input is malformed
     */
    public static Task fromCommand(String command) throws CatBotException {
        String[] cmd = command.split(" ", 2);
        if (cmd.length == 1) {
            throw new CatBotException("That's the wrong format!");
        }
        String[] temp;
        switch (cmd[0].toLowerCase(Locale.ROOT)) {
        case "todo":
            return new ToDoTask(cmd[1].strip());

        case "deadline":
            temp = cmd[1].split("/by", 2);
            if (temp.length != 2) {
                throw new CatBotException("That's the wrong format!");
            }
            try {
                LocalDateTime by = LocalDateTime.parse(temp[1].strip());
                return new DeadlineTask(temp[0].strip(), by);
            } catch (DateTimeParseException e) {
                throw new CatBotException("Dates should be in the format yyyy-MM-ddTHH:mm");
            }

        case "event":
            temp = cmd[1].split("/from|/to", 3);
            if (temp.length != 3) {
                throw new CatBotException("That's the wrong format!");
            }
            try {
                LocalDateTime from = LocalDateTime.parse(temp[1].strip());
                LocalDateTime to = LocalDateTime.parse(temp[2].strip());
                return new EventTask(temp[0].strip(), from, to);
            } catch (DateTimeParseException e) {
                throw new CatBotException("Dates should be in the format yyyy-MM-ddTHH:mm");
            }

        default:
            throw new CatBotException("I don't know what you mean >@w@<");
        }
    }

    /**
     * Setter for done
     * @param done is whether the task is marked as done
     */
    public void setDone(boolean done) {
        this.isDone = done;
    }

    /**
     * Internal method for getting the icon for a marked task
     * @return a string that should be placed in the slot indicating whether this task is marked
     */
    protected String getStatusIcon() {
        return isDone ? ConsoleColors.GREEN + "✓" + ConsoleColors.RESET : " ";
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String toCommand();
}
