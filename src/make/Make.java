package make;

import java.util.ArrayList;
import java.util.List;

public class Make {
    
    private List<Task> tasks = new ArrayList<>();
    
    public Task define() {
        Task task = new Task(this);
        tasks.add(task);
        return task;
    }
    
    public void execute() {
        tasks.get(0).executeIfNotDone(new Session());
    }
    
    void executeDependency(Session session, String file) {
        for (Task task : tasks) {
            task.executeDependency(session, file);
        }
    }
}
