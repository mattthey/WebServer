package fileWorker;

import java.io.File;
import java.util.ArrayList;

public class FileWorker {
    private final String dirPath;
    private boolean isRecursive;

    public void setIsRecursive(boolean newValue) {
        isRecursive = newValue;
    }

    public boolean getIsRecursive() {
        return isRecursive;
    }

    public FileWorker(String path) {
        dirPath = path;
    }

    public void execute(IExecutable command) {
        File folder = new File(dirPath);
        ArrayList<File> files = new ArrayList<File>();
        listFilesForFolder(folder, files, command);

    }

    private void listFilesForFolder(final File folder, ArrayList<File> files, IExecutable command) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() && isRecursive ) {
                listFilesForFolder(fileEntry, files, command);
                continue;
            }
            if (fileEntry.isFile())
                command.process(fileEntry);
        }
    }

}