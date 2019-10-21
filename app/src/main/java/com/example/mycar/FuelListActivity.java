package com.example.mycar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycar.Adapters.FuelAdapter;
import com.example.mycar.Model.FuelDao;


public class FuelListActivity extends AppCompatActivity{
    private FuelAdapter adapter;
    private RecyclerView rvFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_list);

        rvFuel = findViewById(R.id.rvFuel);

        adapter = new FuelAdapter();
        rvFuel.setLayoutManager( new LinearLayoutManager(this));
        rvFuel.setAdapter(adapter);
    }


    public void updateFuel(View v, String fuelId){
        Intent intencao = new Intent( this, FormActivity.class );
        intencao.putExtra("fuelId", fuelId);
        startActivityForResult(intencao, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == 200){
                int position = data.getIntExtra("objectPosition", -1);
                adapter.notifyItemChanged( position );
                rvFuel.smoothScrollToPosition( position );
            }else if (resultCode == 201){
                Toast.makeText(this, "Compromisso inserido com sucesso", Toast.LENGTH_LONG).show();
                int position = FuelDao.getInstance().getList().size()-1;
                adapter.notifyItemInserted( position );
                rvFuel.smoothScrollToPosition( position );
            }else if (resultCode == 202){
                Toast.makeText(this, "Compromisso excluído com sucesso", Toast.LENGTH_LONG).show();
                int position = data.getIntExtra("posicaoDoObjetoExcluido", -1);
                adapter.notifyItemRemoved( position );
            }
        }
    }

    public void addFuel(View v){
        Intent intencao = new Intent( this, FormActivity.class );
        startActivityForResult(intencao, 1);
    }
}
