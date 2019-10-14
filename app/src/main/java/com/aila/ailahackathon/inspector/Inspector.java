package com.aila.ailahackathon.inspector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;

import com.aila.ailahackathon.R;

import java.util.Map;

public class Inspector extends AppCompatActivity {
    private static final String TAG = "Inspector";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector);
    }

    public Map getTotalMinum(){
//        Misal
        int beratBadan = 45;
//        inisialisasi dalam Mili Liter
        int takaranAirMinum = 0;
        int gelasKecil = 250;
        int gelasBesar = 400;

        if (beratBadan >= 45){
            takaranAirMinum = 1900;
        }else if(beratBadan >= 50){
            takaranAirMinum = 2100;
        }else if(beratBadan >= 55){
            takaranAirMinum = 2300;
        }else if(beratBadan >= 60){
            takaranAirMinum = 2500;
        }else if(beratBadan >= 65){
            takaranAirMinum = 2700;
        }else if(beratBadan >= 70){
            takaranAirMinum = 2900;
        }else if(beratBadan >= 75){
            takaranAirMinum = 3200;
        }else if(beratBadan >= 80){
            takaranAirMinum = 3500;
        }else if(beratBadan >= 85){
            takaranAirMinum = 3700;
        }else if(beratBadan >= 90){
            takaranAirMinum = 3900;
        }else if(beratBadan >= 95){
            takaranAirMinum = 4100;
        }else if(beratBadan >= 100){
            takaranAirMinum = 4200;
        }

        int jumlahGelasBesar = takaranAirMinum/gelasBesar;
        int jumlahGelasKecil = takaranAirMinum/gelasKecil;

        Map<String,Object> minumPerhari = new ArrayMap<>();
        minumPerhari.put("gelas_besar",jumlahGelasBesar);
        minumPerhari.put("gelas_kecil",jumlahGelasKecil);

        return minumPerhari;
    }
}
