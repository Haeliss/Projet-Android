package com.example.f5onz.testbluetooth;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

public class MDPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdp);
        ajoutEnvoyerListener();
    }

    private void ajoutEnvoyerListener() {
        final Button btn_envoie = (Button) findViewById(R.id.btn_sendmail);
        final EditText edt_mail = (EditText) findViewById(R.id.mail);
        final TextView err_mail = (TextView) findViewById(R.id.err_mail);
        btn_envoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ajouter acces bdd et vérification des données
                err_mail.setText("");
                String res = testDB(edt_mail.getText().toString());
                if(res!= ""){
                    err_mail.setText("");
                    mailDB(edt_mail.getText().toString());}
                else err_mail.setText("Aucun compte utilisateur n'est lié à ce mail !");

            }
        });
    }
    public String testDB(String mail){
		//Modifier ligne suivante avec bonne URL
        ConnectBD classConn = new ConnectBD("https://infodb.iutmetz.univ-lorraine.fr/~demmer3u/TEST/test2.php",mail);
        classConn.execute("oublie");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return classConn.getReponse();
    }
    public void mailDB(String mail){
        ConnectBD classConn = null;
		//Modifier ligne suivante avec bonne URL
        classConn = new ConnectBD("https://infodb.iutmetz.univ-lorraine.fr/~demmer3u/TEST/test5.php",mail);
        classConn.execute("envoie");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
