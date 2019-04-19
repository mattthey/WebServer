package fileWorker;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Md5Executor implements IExecutable {
    public String dirHash = "";
    public ArrayList<String> filesHashes;


    private byte[] createChecksum(String filename) throws Exception {
        InputStream fis =  new FileInputStream(filename);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead = fis.read(buffer);

        while (numRead != -1) {
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
            numRead = fis.read(buffer);
        }

        fis.close();
        return complete.digest();
    }

    public String process(File f)
    {
        StringBuilder hash = new StringBuilder();
        try
        {
            byte[] bytes = createChecksum(f.getPath());

            for (byte b: bytes)
            {
                hash.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            if (dirHash.equals("")){
                dirHash = hash.toString();
            } else
                {
                    StringBuilder tempHash = new StringBuilder();
                    for (int i=0; i<dirHash.length(); i++)
                    {
                        tempHash.append(dirHash.charAt(i) ^ hash.charAt(i));
                    }
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return hash.toString();
    }
}