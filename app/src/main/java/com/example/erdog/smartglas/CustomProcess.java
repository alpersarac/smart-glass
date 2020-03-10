package com.example.erdog.smartglas;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.os.Handler;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Telephony;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.time.chrono.Chronology;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CustomProcess extends AppCompatActivity {
    Thread thread=null;
 //   private static final  Log=null;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    String gelen_sms,sms;
    private LocationManager locationManager;
    private LocationListener listener;

    TextView textView;
    TableLayout tab;

    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS); //receive the address of the bluetooth device

        setContentView(R.layout.activity_custom_process);

     new ConnectBT().execute();






     arama();

     gecikme1();


    }



public void refresh_lcd(){

}

    public void wifi_status(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ag=wifiInfo.getSSID();
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write("      bagli:".toString().getBytes());
                    btSocket.getOutputStream().write(ag.toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Hata");
                }
            }

        }else{
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write("            AG-YOK".toString().getBytes());

                }
                catch (IOException e)
                {
                    msg("Hata");
                }
            }

        }
    }




    public void sendMessage(View view) {


        if (btSocket != null) {
            try {


                EditText editText = (EditText) findViewById(R.id.editText);
                String value = editText.getText().toString();
                btSocket.getOutputStream().write(value.getBytes());
                editText.setText("");
                gecikme2();

            } catch (IOException e) {
                msg("Error");
            }
        }
    }
    public void get_battery(){
        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            String testString = "";
            testString = Integer.toString(batLevel);
            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write("Batarya %".toString().getBytes());
                    btSocket.getOutputStream().write(testString.toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Hata");
                }
            }
        }
    }
    public void sms_al(String gelen_sms){
        TextView textView;

        textView=(TextView)findViewById(R.id.yaz);

        sms=gelen_sms;
        textView.setText(sms);
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Hata");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }





    public void getRecipe(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Yemek Tarifleri".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Hata");
            }
        }
    }

    public void getMsg(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("wp".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }



    public void getLocation(View view) {   // KONUM ALMA METODU
        int[] location = new int[2];

        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Elazig Firat Universitesi".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Hata");
            }
        }
    }

    public void getHotels(View view) {  //RESTAURANTLAR METODU
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Green House Restaurant ve Cafe".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Hata");
            }
        }
    }

    public void getCalls(View view) {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Gelen Aramalar".toString().getBytes());

            }
            catch (IOException e)
            {
                msg("Hata");
            }
        }
    }





    public void getReadings(View view) {

                home();

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(CustomProcess.this, "Bağlanılıyor...", "Lütfen Bekleyiniz!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Bağlantı sağlanamadı.  Tekrar deneyiniz");
                finish();
            }
            else
            {
                msg("Bağlanti Sağlandı.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    public void sistem_saati(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour, minute;
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        String giris_saati = "";
        giris_saati = ("Saat "+hour + ":" + minute);


        if (btSocket!=null)
        {
            try
            {
              //  btSocket.getOutputStream().write("             ".toString().getBytes());
                btSocket.getOutputStream().write(giris_saati.toString().getBytes());
                btSocket.getOutputStream().write("             ".toString().getBytes());


            }
            catch (IOException e)
            {
                msg("Hata");
            }
        }

        }

    public void sistem_tarihi(){
        Calendar mcurrentTime = Calendar.getInstance();
        int year, month, day;
        year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
        month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
        month=month+1;
        day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz
        String giris_tarihi="";
        giris_tarihi=("Tarih: "+day+"/"+month+"/"+year);


        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(giris_tarihi.toString().getBytes());
                btSocket.getOutputStream().write("        ".toString().getBytes());


            }
            catch (IOException e)
            {
                msg("Hata");
            }
        }

    }

    public void gecikme(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void gecikme1(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void gecikme2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void gecikme3(){
        try {
            arama();
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void home(){
        sistem_saati();
        sistem_tarihi();
        get_battery();
        wifi_status();
    }

    public void arama(){
        TelephonyManager telephone= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneStateListener=new PhoneStateListener(){


            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                // super.onCallStateChanged(state, phoneNumber);

                String number ="";
                number = phoneNumber;
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:

                        if (btSocket != null) {
                            try {

                                btSocket.getOutputStream().write(number.intern().getBytes());
                                btSocket.getOutputStream().write("         Ariyor !!!".toString().getBytes());
                            } catch (IOException e) {
                                msg("Hata");
                            }
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:

                                home();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:



                        if (btSocket != null) {
                            try {
                                btSocket.getOutputStream().write(number.getBytes());
                                btSocket.getOutputStream().write("         ile konusuluyor".toString().getBytes());
                            } catch (IOException e) {
                                msg("Hata");
                            }
                            break;
                        }
                }
            }

        };
        telephone.listen(phoneStateListener,phoneStateListener.LISTEN_CALL_STATE);

    }



}

