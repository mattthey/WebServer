package fileWorker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

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

    public String getFiles()
    {
        File folder = new File(dirPath);
        return searchFiles(folder);
    }

    private String searchFiles(File folder)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (File f : folder.listFiles())
        {
            if (f.isDirectory() && isRecursive )
            {
                stringBuilder.append(searchFiles(f));
                continue;
            }
            if (f.isFile())
                stringBuilder.append(folder.getPath()).append("/").append(f.getName()).append("\n");
        }

        return stringBuilder.toString();

    }

    public void execute(IExecutable command)
    {
        File folder = new File(dirPath);
        LinkedList<File> files = new LinkedList <File>();
        listFilesForFolder(folder, files, command);
    }

    public synchronized void listFilesForFolder(final File folder, LinkedList<File> files, IExecutable command)
    {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory() && isRecursive ) {
                listFilesForFolder(fileEntry, files, command);
                continue;
            }
            if (fileEntry.isFile())
                command.process(fileEntry);
        }
    }

    public static byte[] getBytesFromFile () throws IOException
    {
        Path path = Paths.get("myfile.bin");
        byte[] data = Files.readAllBytes(path);
        return data;
    }

    public static void write (byte[] bytes) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream("myfile.bin");
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {}
    }

}