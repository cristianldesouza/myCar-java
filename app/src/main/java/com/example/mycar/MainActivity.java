package com.example.mycar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mycar.Model.Fuel;
import com.example.mycar.Model.FuelDao;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onResume(){
        super.onResume();
        TextView tvCurrentAutonomy = findViewById(R.id.currentAutonomy);

        if (this.getAutonomy() == -1) {
            tvCurrentAutonomy.setText("Abasteça!");
        } else {
            tvCurrentAutonomy.setText(this.getAutonomy() + "");
        }
    }

    public void btnClicked(View v) {
        Intent refuellingList = new Intent(this, FuelListActivity.class);

        startActivity(refuellingList);

    }

    private double getAutonomy() {
        ArrayList<Fuel> fuels = FuelDao.getInstance().getList();

        if (fuels.size() <= 1) {
            return -1;
        } else {
            double firstKm = fuels.get(0).getCurrentKm();
            double lastKm = fuels.get(fuels.size()-1).getCurrentKm();

            double totalKm = lastKm - firstKm;

            double litersFuelled = 0;


            for (int i = 0; i < fuels.size() - 1; i++) {
                litersFuelled = litersFuelled + fuels.get(i).getLitersFuelled();
            }

            String result = (totalKm/litersFuelled) + "";

            int ponto = result.indexOf(".");

            return (Double.parseDouble(result.substring(0, ponto + 3)));
        }

    }
}
