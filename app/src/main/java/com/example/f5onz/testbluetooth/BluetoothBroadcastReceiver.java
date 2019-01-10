package com.example.f5onz.testbluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by f5onz on 07/12/2017.
 */

public final class BluetoothBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "test1",
                Toast.LENGTH_SHORT).show();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            Toast.makeText(context, "test2",
                    Toast.LENGTH_SHORT).show();
            System.out.println("TEST");
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Toast.makeText(context, "New Device = " + device.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}

