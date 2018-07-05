package com.enna1.dotanews;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtils {
    public static String convertStream(InputStream is){
        String res = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte [] bytes = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(bytes)) != -1){
                baos.write(bytes, 0, len);
                baos.flush();
            }
            res = new String(baos.toByteArray(), "utf-8");
            res = baos.toString();
            baos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
