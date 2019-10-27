package ru.world18.a18worldlauncher;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            String params = "login="+static_vars.user.login+"&password="+static_vars.user.password;
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();
            int responseCode = con.getResponseCode();
            StringBuffer response = new StringBuffer();
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                response.append("POST request not worked:"+responseCode);
            }
            return response.toString();
        }catch(Exception e){
            return "Error";
        }
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        TextView welcomeText = (TextView) static_vars.activity.findViewById(R.id.welcomeText);
        try {
            //welcomeText.setText(result);
            JSONObject obj = new JSONObject(result);
            int id = Integer.parseInt(obj.getString("id_user"));
            String password = obj.getString("password");
            String sess_id = obj.getString("sess_id");
            if(id==0){
                welcomeText.setText(new String(password.getBytes("windows-1251"), "UTF-8"));
                //static_vars.activity.setContentView(R.layout.loading);
            }else{
                static_vars.user.id=id;
                static_vars.user.password=password;
                static_vars.user.sess_id=sess_id;
                static_vars.user.status="connected";
                static_vars.user.setLogin();
                static_vars.activity.cteateMain();
                /*static_vars.activity.setContentView(R.layout.game);
                //SharedPreferences.Editor editor = static_vars.activity.mSettings.edit();
                welcomeText = (TextView) static_vars.activity.findViewById(R.id.welcomeText);
                if(static_vars.activity.mSettings.contains("login")) {
                    welcomeText.setText("Добро пожаловать "+static_vars.activity.mSettings.getString("login", ""));
                }else{
                    welcomeText.setText("No value founded");
                }*/
            }
        }catch(Exception e){
             welcomeText.setText("Ошибка получения данных!");
        }
    }
}
