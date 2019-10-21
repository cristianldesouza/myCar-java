package com.example.mycar.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.R;
import com.example.mycar.FuelListActivity;
import com.example.mycar.Model.Fuel;

import java.text.DateFormat;


public class FuelViewHolder extends RecyclerView.ViewHolder {
    private TextView tvDescription;
    private TextView tvDate;
    private ImageView ivBrand;
    private ConstraintLayout clFather;
    private String fuelId;


    public FuelViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FuelListActivity) v.getContext()).updateFuel(v, fuelId);
            }
        });


        tvDescription = itemView.findViewById(R.id.tvDescription);
        tvDate = itemView.findViewById(R.id.tvDate);
        ivBrand = itemView.findViewById(R.id.ivBrand);
        clFather = (ConstraintLayout) itemView;

    }

    public void updateDrawer(Fuel f){
        fuelId = f.getId();

        tvDescription.setText( "Abastecidos " + f.getLitersFuelled() + " litros. Km veículo " + f.getCurrentKm() );

        DateFormat formatter = android.text.format.DateFormat.getDateFormat( tvDescription.getContext() );
        String formatedDate = formatter.format( f.getDate().getTime() );
        tvDate.setText( formatedDate );

        DateFormat formatador = android.text.format.DateFormat.getDateFormat( tvDescription.getContext() );
        String dataFormatada = formatador.format( f.getDate().getTime() );
        tvDate.setText( dataFormatada );

        switch (f.getBrand()) {
            case "Ipiranga":
                ivBrand.setImageResource(R.mipmap.ipiranga_agoravai_foreground);
                break;
            case "Shell":
                ivBrand.setImageResource(R.mipmap.shell_logo_foreground);
                break;
            case "Texaco":
                ivBrand.setImageResource(R.mipmap.texaco_agoravai_foreground);
                break;
            case "Petrobras":
                ivBrand.setImageResource(R.mipmap.petrobras_agoravai_foreground);
                break;
            default:
                ivBrand.setImageResource(R.mipmap.desconhecido_foreground);
                break;
        }
    }
}
