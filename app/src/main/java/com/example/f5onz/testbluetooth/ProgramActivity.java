package com.example.f5onz.testbluetooth;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProgramActivity extends AppCompatActivity {
    String str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        str = (String) getIntent().getSerializableExtra("string");
        ajoutListenerBtnMenu();
    }
    private void ajoutListenerBtnMenu() {
        ImageButton btn_main = (ImageButton) findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgramActivity.this, MainActivity.class);
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
}
