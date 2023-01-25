public class ToDo extends Task {
    public Task.Type type;

    public ToDo(String task) {
        super(task);
        type = Type.TODO;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toCommand() {
        return "todo " + super.taskStr + (super.done ? "\nmark last": "");
    }
}
