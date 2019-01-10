package com.example.f5onz.testbluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by f5onz on 13/01/2018.
 */

public class ConnectBT extends AsyncTask<Void,Void,Void>{
    private boolean ConnectSuccess = true;

    String address=null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket =null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public boolean getBtConnected(){
        return ConnectSuccess;
    }
    public ConnectBT(String address){
        this.address=address;
        try{
            if(btSocket == null || !isBtConnected){
                myBluetooth = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();
            }
        }catch(IOException e){
            ConnectSuccess = false;
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
        if(!ConnectSuccess){
            //affiche connexion etablie
        }
        else{
            isBtConnected = true;
        }
        progress.dismiss();
    }
    public void commande(String a){
        if(btSocket!=null){
            try{
                System.out.println(a);
                btSocket.getOutputStream().write(a.getBytes());

                btSocket.getOutputStream().flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
