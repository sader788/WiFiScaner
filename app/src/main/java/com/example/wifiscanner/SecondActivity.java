package com.example.wifiscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle args = getIntent().getExtras();
        Element element = (Element) args.getSerializable(Element.class.getSimpleName());

        TextView txtName = (TextView)  findViewById(R.id.name);
        txtName.setText(element.getName());

        TextView txtBSSID = (TextView) findViewById(R.id.BSSID);
        txtBSSID.setText(element.getBSSID());

        TextView txtSecLevel = (TextView) findViewById(R.id.secLevel);
        txtSecLevel.setText(element.getSecLevel());

        TextView txtSignLevel = (TextView) findViewById(R.id.signLevel);
        int level = element.getSignLevel();

        try {
            int i = level;
            if (i>-50){
                txtSignLevel.setText("Высокий");
            }
            else if (i<=-50 && i>-80){
                txtSignLevel.setText("Средний");
            }
            else if (i <= -80){
                txtSignLevel.setText("Низкий");
            }
        } catch (NumberFormatException e){
            Log.d("TAG", "неверный формат строки");
        }

        TextView txtFreq = (TextView) findViewById(R.id.signFreq);
        txtFreq.setText(Integer.toString((element.getFreq())));

        findViewById(R.id.backButton).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}