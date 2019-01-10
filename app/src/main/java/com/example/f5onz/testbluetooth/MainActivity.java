package com.example.f5onz.testbluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private Set<BluetoothDevice> devices;
    public static BluetoothAdapter blueAdapter;
    public BluetoothBroadcastReceiver bluetoothReceiver = new BluetoothBroadcastReceiver();
    public int click;
    public int click2;
    public int blue;
    String str="";

    ConnectBT connectBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        str = (String) getIntent().getSerializableExtra("string");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        activerBluetooth();
        blue=0;
            ajoutListenerBtnBlue();
            ajoutListenerBtnMenu();
        click=0;
        click2=0;
    }

    private void ajoutListenerBtnBlue() {
        final ImageButton btn_blue = (ImageButton) findViewById(R.id.btn_blue);
        btn_blue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(blue==1){
                btn_blue.setBackgroundColor(Color.rgb(0,162,232)); //pas appuyé
                    blue=0;
                }else{
                    btn_blue.setBackgroundColor(Color.rgb(0,108,155)); //appuyé
                    ajoutListenerBtnCmd();
                    activerBluetooth();
                    blue=1;
                }
            }
        });
    }

    private void ajoutListenerBtnMenu() {
        ImageButton btn_program = (ImageButton) findViewById(R.id.btn_program);
        btn_program.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProgramActivity.class);
                intent.putExtra("string", str);
                startActivity(intent);
            }
        });
        ImageButton btn_web = (ImageButton) findViewById(R.id.btn_web);
        btn_web.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //accés site
				//Modifier ligne suivante avec bonne URL
                String url = "https://infodb.iutmetz.univ-lorraine.fr/~demmer3u/site2/accueil-logoff.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.EMPTY.parse(url));
                startActivity(i);
            }
        });
    }

    private void ajoutListenerBtnCmd(){
        ajoutListenerUp();
        ajoutListenerDown();
        ajoutListenerLeft();
        ajoutListenerRight();
        ajoutListenerSuivi();
        ajoutListenerObs();
    }
    private void ajoutListenerObs() {
        final Button btn_obstacle = (Button) findViewById(R.id.btn_obstacle);
        btn_obstacle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(connectBT!=null){
                    if(click == 1){
                        connectBT.commande("W");
                        connectBT.commande("W");
                        click=0;
                        btn_obstacle.setBackgroundColor(Color.rgb(0,162,232));
                    }
                    else{
                        connectBT.commande("O");
                        click=1;
                    btn_obstacle.setBackgroundColor(Color.rgb(0,108,155));}
                }else{
                    activerBluetooth();
                }
            }
        });
    }
    private void ajoutListenerSuivi() {
        final Button btn_suivi = (Button) findViewById(R.id.btn_suivi);
        btn_suivi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(connectBT!=null){
                    if(click2 == 1){
                        connectBT.commande("W");
                        connectBT.commande("W");
                        click2=0;
                        btn_suivi.setBackgroundColor(Color.rgb(0,162,232));
                    }
                    else{
                        connectBT.commande("S");
                        click2=1;
                        btn_suivi.setBackgroundColor(Color.rgb(0,108,155));}
                }else{
                    activerBluetooth();
                }
            }
        });
    }
    private void ajoutListenerDown() {
       final ImageButton btn_down = (ImageButton) findViewById(R.id.btn_down);
        btn_down.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //appuyé
                        connectBT.commande("B");
                        btn_down.setBackgroundColor(Color.rgb(0,108,155));
                        break;
                    case MotionEvent.ACTION_UP:
                        //relaché
                        connectBT.commande("Z");
                        btn_down.setBackgroundColor(Color.rgb(0,162,232));
                        break;
                }
                return true;
            }
        });
    }
    private void ajoutListenerRight() {
        final ImageButton btn_right = (ImageButton) findViewById(R.id.btn_right);
        btn_right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //appuyé
                        connectBT.commande("R");
                        btn_right.setBackgroundColor(Color.rgb(0,108,155));
                        break;
                    case MotionEvent.ACTION_UP:
                        //relaché
                        connectBT.commande("Z");
                        btn_right.setBackgroundColor(Color.rgb(0,162,232));
                        break;
                }
                return true;
            }
        });
    }
    private void ajoutListenerLeft() {
        final ImageButton btn_left = (ImageButton) findViewById(R.id.btn_left);
        btn_left.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //appuyé
                        connectBT.commande("L");
                        btn_left.setBackgroundColor(Color.rgb(0,108,155));
                        break;
                    case MotionEvent.ACTION_UP:
                        //relaché
                        connectBT.commande("Z");
                        btn_left.setBackgroundColor(Color.rgb(0,162,232));
                        break;
                }
                return true;
            }
        });
    }
    private void ajoutListenerUp() {
        final ImageButton btn_up = (ImageButton) findViewById(R.id.btn_up);
        btn_up.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //appuyé
                        connectBT.commande("F");
                        btn_up.setBackgroundColor(Color.rgb(0,108,155));
                        break;
                    case MotionEvent.ACTION_UP:
                        //relaché
                        connectBT.commande("Z");
                        btn_up.setBackgroundColor(Color.rgb(0,162,232));
                        break;
                }
                return true;
            }
        });
    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE_ENABLE_BLUETOOTH)
            return;
        if (resultCode == RESULT_OK) {
            // L'utilisateur a activé le bluetooth
        }else{
            //l'utilisateur n'as pas activé le bluetooth
        }


    }
    private void activerBluetooth(){
        //vérifie la présence de bluetooth sur le téléphone
        blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter == null)
            Toast.makeText(MainActivity.this, "Pas de Bluetooth",
                    Toast.LENGTH_SHORT).show();
        else activateBluetooth();

        //blueAdapter.startDiscovery();

        devices = blueAdapter.getBondedDevices();
        if (devices.size() > 0) {
            //Il y a des appareils jumelés
            for (Iterator<BluetoothDevice> it = devices.iterator(); it.hasNext(); ) {
                BluetoothDevice f = it.next();
                if (f.getName().equals("HC-05")) {
                    connectBT = new ConnectBT(f.getAddress());
                    if(connectBT.getBtConnected()==true){
                    Toast.makeText(MainActivity.this, "Connexion établie !", Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(MainActivity.this, "Connexion échouée !", Toast.LENGTH_SHORT).show();}
                } else{
                    //Toast.makeText(MainActivity.this, "Vous devez jumeler votre téléphone avec le robot !", Toast.LENGTH_SHORT).show();
                    }
                }
            } else{
            //ajouter fenetre qui dit de jumeler avec le robot
            //Toast.makeText(MainActivity.this, "Vous devez jumeler votre téléphone avec le robot !", Toast.LENGTH_SHORT).show();
        }
    }
    private void activateBluetooth(){
        if (!blueAdapter.isEnabled()) {
            Intent enableBlueTooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth, REQUEST_CODE_ENABLE_BLUETOOTH);
        }
    }
    private void desactivateBluetooth(){
        blueAdapter.disable();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        desactivateBluetooth();
        //blueAdapter.cancelDiscovery();
    }
}
