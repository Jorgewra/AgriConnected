package com.br.agriconnected;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txVelocidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txVelocidade =(TextView)findViewById(R.id.txVelocmetro);
        verificarAcessoGPS();
    }
    private void verificarAcessoGPS(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
            calcularVelocidade();
    }
    private void calcularVelocidade(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    //metros/segundo em relação ao solo.
                    float velocidade = ((location.getSpeed()*3600)/1000);
                    txVelocidade.setText(""+velocidade);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.000001f, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
