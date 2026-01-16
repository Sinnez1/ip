public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markTask() {
        this.isDone = true;
        System.out.println(Pinggu.DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(" [" + this.getStatusIcon() + "] " + this.description);
        System.out.println(Pinggu.DIVIDER);
    }

    public void unmarkTask() {
        this.isDone = false;
        System.out.println(Pinggu.DIVIDER);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(" [" + this.getStatusIcon() + "] " + this.description);
        System.out.println(Pinggu.DIVIDER);
    }
}
