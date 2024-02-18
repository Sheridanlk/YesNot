package com.example.yesnot.sampledata;

import com.example.yesnot.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class AnswerManager {
    public static String HeadsOrTails(int number){
        if (number % 2 == 0) {
            return "Heads";
        } else {
            return "Tails";
        }
    }
    public static String isEven(int number) {
        if (number % 2 == 0) {
            return "Yes";
        } else {
            return "No";
        }
    }
    public static String  ApiAnswer(int UpLim, int type){
        int clicks = MainActivity.PreferenceManager.getClicks();
        String url = "https://www.random.org/integers/?num=1&min=1&max=" + UpLim + "&col=1&base=10&format=plain&rnd=new";
        String reply = null;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reply = buffer.readLine();
            }
            else {
                return "error";
            }
        }
        catch (Throwable cause){
            cause.printStackTrace();

        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        if (reply != null){
            if (clicks < 1000) {
                clicks += 1;
            }
            else {
                clicks = 0;
            }

            MainActivity.PreferenceManager.saveClicks(clicks);
            switch (type) {
                case 1:
                    return isEven(Integer.parseInt(reply));
                case 2:
                    return HeadsOrTails(Integer.parseInt(reply));
                case 3:
                    return reply;
                default:
                    return "";
            }
        }
        else {
            return "error";
        }

    }
    public static String RandomAnswer(int lim, int type){
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int clicks = MainActivity.PreferenceManager.getClicks();
        Random random = new Random();
        int number = (random.nextInt(lim) + 1) + clicks;
        clicks = 0;
        MainActivity.PreferenceManager.saveClicks(clicks);
        switch (type){
            case (1):
                return isEven(number);
            case (2):
                return HeadsOrTails(number);
            case (3):
                return String.valueOf(number);
            default:
                return "";
        }
    }
}
