package com.example.f5onz.testbluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ajoutConnexionListener();
        ajoutOublieListener();
        ajoutNouveauListener();

        //testDB();
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
    public String testDB(String mail, String mdp){
		//Modifier ligne suivante avec bonne URL
        ConnectBD classConn = new ConnectBD("https://infodb.iutmetz.univ-lorraine.fr/~demmer3u/TEST/test.php",mail,mdp);
       classConn.execute("connexion");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return classConn.getReponse();
    }

    private void ajoutConnexionListener() {
        final Button btn_co = (Button) findViewById(R.id.connect);
        final EditText edt_mail = (EditText) findViewById(R.id.mail);
        final EditText edt_mdp = (EditText) findViewById(R.id.mdp);
        final TextView erreur = (TextView) findViewById(R.id.err_login);
        btn_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ajouter acces bdd et vérification des données
                String res = testDB(edt_mail.getText().toString(),edt_mdp.getText().toString());
                if(res!= ""){
                    erreur.setText("");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("string", edt_mail.getText().toString());
                startActivity(intent);}
                else erreur.setText("Email ou Mot de passe incorrect !");

            }
        });
    }
    private void ajoutOublieListener() {
        final TextView txt_forget= (TextView) findViewById(R.id.txt_forget);
        txt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ajouter acces bdd et vérification des données
                Intent intent = new Intent(LoginActivity.this, MDPActivity.class);
                startActivity(intent);
            }
        });
    }
    private void ajoutNouveauListener() {
        final TextView txt_sub = (TextView) findViewById(R.id.txt_sub);
        txt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ajouter acces bdd et vérification des données
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
            }
        });
    }
}
