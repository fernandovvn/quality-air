package br.edu.ufam.icomp.devtitans.qualityair.sensorview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorReading;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsViewHolder> {

    private ArrayList<SensorReading> readings;
    private final Context context;

    public SensorsAdapter(ArrayList<SensorReading> readings, Context context){
        this.readings = readings;
        this.context = context;
    }

    public ArrayList<SensorReading> getItems(){
        return readings;
    }

    public SensorReading getItem(int position) {
        return readings.get(position);
    }

    public void setItem(int position, SensorReading newItem){
        readings.set(position, newItem);
    }

    public void setItemUpdate(int position, SensorReading newItem){
        setItem(position, newItem);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public SensorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sensor_view_holder, parent, false);
        return new SensorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorsViewHolder holder, int position) {
        SensorReading sensor = readings.get(position) ;

        holder.setName(sensor.getType().getName(context));
        holder.setDescription(sensor.getType().getDescription(context));
        holder.setSatus(sensor.getStatus());
        holder.setValue(sensor.getValueExactUnit(context));
    }

    @Override
    public int getItemCount() {
        return readings.size();
    }
}
