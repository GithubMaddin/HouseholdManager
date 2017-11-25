package de.was_wichtiges.householdmanager.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jfmarten on 25.11.17.
 */
public class ByteLoader extends AsyncTask<String, String, String> {

    public static String fileMD5(String text) {
        try {
            return bytesToHex(MessageDigest.getInstance("MD5").digest(text.getBytes())).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void download(String from, String to, boolean force) {
        Log.i("ByteLoader", "Loading " + from + " to " + to);
        File toFile = new File(to);
        if (toFile.exists() && !force) {
            Log.i("ByteLoader", "Not needed");
            return;
        }
        ByteLoader loader = new ByteLoader();
        Log.i("ByteLoader", "Start loading");
        loader.execute(from, to);
    }

    @Override
    protected String doInBackground(String... strings) {
        String from = strings[0];
        String to = strings[1];
        try {
            URL url = new URL(from);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            FileOutputStream fos = new FileOutputStream(new File(to));
            InputStream is = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int len = is.read(buffer);
            while (len != -1) {
                fos.write(buffer, 0, len);
                len = is.read(buffer);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
