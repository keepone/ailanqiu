package com.lb.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
/**
 * User: libofeng Date: 2006-10-31 Time: 13:45:52 Description:
 * http
 */
public class HttpUtil {
    /**
  * 
  * 
  * @param urlString
  *            
  * @return 
  */
    public static String get(String urlString) {
        Logger log = Logger.getLogger("HttpUtil.get(String url)");
        StringBuffer stringBuffer = new StringBuffer();
        if (urlString.equalsIgnoreCase("")) {
            return null;
        } else if (urlString.toLowerCase().startsWith("http://")) {
        } else {
            return null;
        }

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            // OutputStreamWriter writer = new
   // OutputStreamWriter(httpConnection.getOutputStream());
            // writer.close();
            code = httpConnection.getResponseCode();
        } catch (Exception e) {
            return null;
        }
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
            }
        }
        return stringBuffer.toString();
    }
    /**
  * post
  * 
  * @param urlString
  *            
  * @param parameters
  *           
  * @return 
  */
    public static String post(String urlString, String parameters) {
        Logger log = Logger.getLogger("HttpUtil.post(String urlString, String parameters)");
        StringBuffer stringBuffer = new StringBuffer();
       
        if (urlString.equalsIgnoreCase("")) {
            return null;
        } else if (urlString.toLowerCase().startsWith("http://")) {
        } else {
            return null;
        }

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            /*
    * PrintWriter printWriter = new
    * PrintWriter(httpConnection.getOutputStream());
    * printWriter.print(parameters); printWriter.close();
    */
            OutputStreamWriter outputStreamWriter= new OutputStreamWriter(httpConnection.getOutputStream(), "8859_1");
            outputStreamWriter.write(parameters);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {
            return null;
        }
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
            }
        }
        return stringBuffer.toString();
    }
}