package com.example.f5onz.testbluetooth;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by f5onz on 15/01/2018.
 */

public class ConnectBD extends AsyncTask<String,String,String>{
        private String reponse = null;
        private String returnString = null;
        private String resultat = "";
        private String login="";
        private String mail = "";
        private String mdp ="";

        public ConnectBD(String returnStr, String Email, String pw){
            returnString = returnStr;
            mail = Email;
            mdp = pw;
        }
        public ConnectBD(String returnStr, String Email){
            returnString = returnStr;
            mail = Email;
        }
        public ConnectBD(String returnStr, EditText Login){
            returnString = returnStr;
            login = Login.getText().toString();
        }
        public ConnectBD(String returnStr,String Login, String Email,String pw) throws NoSuchAlgorithmException {
        returnString = returnStr;
        login = Login;
        mail = Email;
        mdp = hashPassword(pw);
        }
        public String getReponse(){
            return reponse;
        }

        protected String doInBackground(String... str ) {
            // TODO Auto-generated method stub
            // Appeler la méthode pour récupérer les données JSON
            if(str[0] == "connexion"){
                try {
                    reponse = Connection(returnString);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
            else if(str[0]== "oublie"){
                reponse= Oublie(returnString);
            }
            else if(str[0]== "inscription"){
                Inscription(returnString);
            }
            else if(str[0]=="login"){
                reponse = Login(returnString);
            }
            else if(str[0]=="envoie"){
                envoie(returnString);
            }
            return reponse;
        }

    private void envoie(String returnString) {
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            //constantes
            URL url = new URL(returnString);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mail", mail);
            String message = jsonObject.toString();

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*millisecondes*/);
            conn.setConnectTimeout(15000 /* millisecondes */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);

            //header HTTP
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            //open
            conn.connect();

            //envoie
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            //fermeture
            os.flush();

            //do somehting with response
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //fermeture
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.disconnect();
        }
    }

    private String Login(String returnString) {
        InputStream is = null;
        String result = "";
        // Envoyer la requête au script PHP.
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login", login));
        // Envoie de la commande http
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        // Convertion de la requête en string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        // Parse les données JSON
        try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length()+1; i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                returnString += "\n\t" + jArray.getJSONObject(i);
                resultat = resultat + "Login : "+ json_data.getString("login");
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return resultat;
    }

    private void Inscription(String returnString) {
        OutputStream os = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            //constantes
            URL url = new URL(returnString);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", login);
            jsonObject.put("mdp", mdp);
            jsonObject.put("mail", mail);
            String message = jsonObject.toString();

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*millisecondes*/);
            conn.setConnectTimeout(15000 /* millisecondes */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);

            //header HTTP
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            //open
            conn.connect();

            //envoie
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            //fermeture
            os.flush();

            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //fermeture
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.disconnect();
        }
    }

    private String Connection(String returnString) throws NoSuchAlgorithmException {
        InputStream is = null;
        String result = "";
        // Envoyer la requête au script PHP.
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mail", mail));
        nameValuePairs.add(new BasicNameValuePair("mdp", hashPassword(mdp)));
        // Envoie de la commande http
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        // Convertion de la requête en string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        // Parse les données JSON
        try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length()+1; i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                returnString += "\n\t" + jArray.getJSONObject(i);
                resultat = resultat + "Email : "+ json_data.getString("mail");
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return resultat;
    }

    private String Oublie(String returnString) {
        InputStream is = null;
        String result = "";
        // Envoyer la requête au script PHP.
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("mail", mail));
        // Envoie de la commande http
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(returnString);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        // Convertion de la requête en string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        // Parse les données JSON
        try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length()+1; i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                returnString += "\n\t" + jArray.getJSONObject(i);
                resultat = resultat + "Email : "+ json_data.getString("mail");
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return resultat;
    }

    public String hashPassword(String pwd ) throws NoSuchAlgorithmException {
        String hash=sha1("e*?g^*~Ga7" + pwd + "9!cF;.!Y)?");
        return hash;
    }

    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}

