/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Notifications;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author TT99-PC01
 */
public class Notification {

    public static final String SERVER_KEY = "AIzaSyCED3nXwVcfDd9_XCi7s-_4CtjZAUiUeOg";
 
    public Notification() {
    }

    /**
     * @param args the command line arguments
     */
    public static void sendNotification(int id, String title, String content) {
        try {
            // Prepare JSON containing the FCM message content. What to send and where to send.
            JSONObject jFcmData = new JSONObject();
            JSONObject data = new JSONObject();

            data.put("title", title);
            data.put("content-text", content);
            data.put("id", id);
            // Where to send FCM message.
            jFcmData.put("to", "/topics/info");
            // What to send in FCM message.
            jFcmData.put("data", data);

            System.out.println(jFcmData);
            // Create connection to send FCM Message request.
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jFcmData.toString().getBytes());

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);
            System.out.println("Check your device/emulator for notification or logcat for "
                    + "confirmation of the receipt of the FCM message.");
        } catch (IOException e) {
            System.out.println("Unable to send FCM message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server "
                    + "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
        } catch (JSONException ex) {
            Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
