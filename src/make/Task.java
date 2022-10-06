package make;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private Make make;
    private List<String> input = new ArrayList<>();
    private List<String> output = new ArrayList<>();
    private Runnable command = () -> {};

    Task(Make make) {
        this.make = make;
    }

    public Task inputs(String file) {
        input.add(file);
        return this;
    }

    public Task inputs(Iterable<String> files) {
        for (String file : files) {
            input.add(file);
        }
        return this;
    }

    public Task outputs(String file) {
        output.add(file);
        return this;
    }

    public Task outputs(Iterable<String> files) {
        for (String file : files) {
            output.add(file);
        }
        return this;
    }

    public Task executes(Runnable command) {
        this.command = command;
        return this;
    }

    void executeIfNotDone(Session session) {
        session.executeIfNotDone(this);
    }

    void executeDependency(Session session, String file) {
        if (output.contains(file)) {
            executeIfNotDone(session);
        }
    }

    void execute(Session session) {
        for (String file : input) {
            make.executeDependency(session, file);
        }
        List<String> inputFiles = new ArrayList<>();
        List<String> outputFiles = new ArrayList<>();
        getFiles(input, inputFiles);
        getFiles(output, outputFiles);
        long inputKey = getKey(inputFiles);
        long outputKey = getKey(outputFiles);
        if (inputKey == 0) {
            if (outputKey == 0) {
                command.run();
            }
        } else {
            if (outputKey != inputKey) {
                command.run();
                for (String file : outputFiles) {
                    new File(file).setLastModified(inputKey);
                }
            }
        }
    }

    static void getFilesFromDirectory(String folder, List<String> files) {
        for (File file : new File(folder).listFiles()) {
            if (file.isFile()) {
                files.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                getFilesFromDirectory(file.getAbsolutePath(), files);
            }
        }
    }

    static void getFiles(List<String> io, List<String> files) {
        for (String entry : io) {
            File file = new File(entry);
            if (file.exists()) {
                if (file.isFile()) {
                    files.add(entry);
                } else if (file.isDirectory()) {
                    getFilesFromDirectory(entry, files);
                }
            }
        }
    }

    static long getKey(List<String> files) {
        long key = 0;
        for (String file : files) {
            key = Math.max(key, new File(file).lastModified());
        }
        return key;
    }
}
