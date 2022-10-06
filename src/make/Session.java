package make;

import java.util.HashSet;
import java.util.Set;

class Session {
    
    private Set<Task> done = new HashSet<>();
    
    void executeIfNotDone(Task task) {
        if (!done.contains(task)) {
            done.add(task);
            task.execute(this);
        }
    }
}
