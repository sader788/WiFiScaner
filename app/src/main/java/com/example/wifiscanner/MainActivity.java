package com.example.wifiscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Element[] nets;
    private WifiManager manager;
    private List<ScanResult> wifiList;

    Activity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.scanningButton).setOnClickListener(this);


       // adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
       // listView.setAdapter(adapter);
       // scanWifi();
    }


    @Override
    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 0);
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_WIFI_STATE}, 0);
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CHANGE_WIFI_STATE}, 0);


        detectWifi();
    }

    public void detectWifi(){
        // сканирование сетей
        this.manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.manager.startScan();
        this.wifiList = this.manager.getScanResults();

        System.out.println(wifiList.size());

        if(!this.manager.isWifiEnabled())
        {
            Toast.makeText(this, "Wifi is disabled ... You need to enable it", Toast.LENGTH_LONG).show();
            this.manager.setWifiEnabled(true);
        }

        // разбор полученных данных
        this.nets = new Element[wifiList.size()];
        for (int i = 0; i< wifiList.size(); i++){

            String Name = wifiList.get(i).SSID;
            String BSSID = wifiList.get(i).BSSID;
            String secLvl = wifiList.get(i).capabilities;
            int freq = wifiList.get(i).frequency;
            int lvl = wifiList.get(i).level;


            nets[i] = new Element(Name, BSSID, secLvl , lvl, freq);
        }

        //Создаём адаптер и присваиваем адаптер списку
       AdapterElements adapterElements = new AdapterElements(this);
       ListView netList = (ListView) findViewById(R.id.listItem);
       netList.setAdapter(adapterElements);
       netList.setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(i);
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(Element.class.getSimpleName(), nets[i]);
        startActivity(intent);
    }

    // внутренний класс который заполняет список
    class AdapterElements extends ArrayAdapter<Object> {
        Activity context;

        public AdapterElements(Activity context) {
            super(context, R.layout.maket, nets);
            this.context = context;
        }

        // Заполнение пункта списка данными
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.maket, null);


            ((TextView) (item.findViewById(R.id.wifiName))).setText(nets[position].getName());

            return item;
        }
    }
}