package com.example.mycar.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.R;
import com.example.mycar.Model.Fuel;
import com.example.mycar.Model.FuelDao;

public class FuelAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ConstraintLayout rootXML = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fuel_item, parent, false
        );

        FuelViewHolder drawer = new FuelViewHolder(rootXML);

        return drawer;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Fuel f = FuelDao.getInstance().getList().get(position);
        FuelViewHolder drawer = (FuelViewHolder) holder;

        drawer.updateDrawer(f);
    }

    @Override
    public int getItemCount() {
        return FuelDao.getInstance().getList().size();
    }
}
