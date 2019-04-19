package fileWorker;

import java.io.File;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args)  {
        String s = "123\r\n\r\n";
        System.out.println(s.substring(s.length() - 4).equals("\r\n\r\n"));
    }
}