package com.example.f5onz.testbluetooth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ajoutInscritListener();
    }
    String boole = "false";
    private void ajoutInscritListener() {
        final Button btn_new = (Button) findViewById(R.id.btn_new);
        final EditText edt_login = (EditText) findViewById(R.id.login);
        final EditText edt_mail = (EditText) findViewById(R.id.mail);
        final EditText conf_mail = (EditText) findViewById(R.id.conf_mail);
        final EditText edt_mdp = (EditText) findViewById(R.id.pw);
        final EditText conf_pw = (EditText) findViewById(R.id.conf_pw);
        final TextView err_login = (TextView) findViewById(R.id.err_login);
        final TextView err_mail = (TextView) findViewById(R.id.erreur_mail);
        final TextView err_conf_mail = (TextView) findViewById(R.id.err_conf_mail);
        final TextView err_pw = (TextView) findViewById(R.id.err_pw);
        final TextView err_conf_pw = (TextView) findViewById(R.id.err_conf_pw);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                err_login.setText("");
                err_mail.setText("");
                err_conf_mail.setText("");
                err_pw.setText("");
                err_conf_pw.setText("");
                //ajouter acces bdd et vérification des données
                    if(edt_login.getText().toString()==""){
                        err_login.setText("Entrez un login !");
                        boole ="false";
                    }
                    else{
                        String res = loginDB(edt_login);
                        if(res!=""){
                            err_login.setText("Le login est déjà utilisé !");
                            boole ="false";
                        }
                        else boole ="true";
                    }

                if(edt_mail.getText().toString().equalsIgnoreCase("")){
                    err_mail.setText("Entrez un mail !");
                    boole="false";
                    if(conf_mail.getText().toString().equalsIgnoreCase("")){
                        err_conf_mail.setText("Entrez à nouveau le mail !");
                        boole="false";
                    }
                }
                else{
                    if(conf_mail.getText().toString().equalsIgnoreCase(edt_mail.getText().toString())){
                        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
                        Matcher m = p.matcher(edt_mail.getText().toString());
                        if (!m.matches()) {
                            err_mail.setText("Le mail est dans un mauvais format");
                            boole = "false";
                        }else{
                        String res = mailDB(edt_mail.getText().toString());
                        if(res!=""){
                            err_mail.setText("Le mail est déjà utilisé");
                            boole ="false";
                        }
                        else boole ="true";}
                    }
                    else{
                        err_conf_mail.setText("Les adresses mails ne correspondent pas !");
                        boole ="false";
                    }
                }
                if(edt_mdp.getText().toString().equalsIgnoreCase("")){
                    err_pw.setText("Entrez un mot de passe !");
                    boole="false";
                    if(conf_pw.getText().toString().equalsIgnoreCase("")){
                        err_conf_pw.setText("Entrez à nouveau le mot de passe !");
                        boole="false";
                    }
                }
                else{
                    if(conf_pw.getText().toString().equalsIgnoreCase(edt_mdp.getText().toString())){

                        if(edt_mdp.getText().toString().length()<8){
                            err_pw.setText("Le mot de passe doit contenir plus de 8 caractères !");
                            boole ="false";
                        }
                        else boole ="true";
                    }
                    else{
                        err_conf_pw.setText("Les mots de passe ne correspondent pas !");
                        boole ="false";
                    }
                }
                if(boole.equalsIgnoreCase("true")){
                    testDB(edt_login.getText().toString(),edt_mail.getText().toString(),edt_mdp.getText().toString());
                }

            }
        });
    }

    private String mailDB(String mail) {
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
    private String loginDB(EditText login) {
		//Modifier ligne suivante avec bonne URL
        ConnectBD classConn = new ConnectBD("https://infodb.iutmetz.univ-lorraine.fr/~demmer3u/TEST/test4.php", login);
        classConn.execute("login");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return classConn.getReponse();
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
    public void testDB(String login,String mail, String mdp){
        ConnectBD classConn = null;
        try {
			//Modifier ligne suivante avec bonne URL
            classConn = new ConnectBD("https://infodb.iutmetz.univ-lorraine.fr/~demmer3u/TEST/test3.php",login,mail,mdp);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        classConn.execute("inscription");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
