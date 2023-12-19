package br.edu.ufam.icomp.devtitans.qualityair.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorReading;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorType;

public class ServiceData {

    private Context context;
    private final String prefName = "readings";
    public final SensorType[] allTypes = {
            SensorType.PM_10,
            SensorType.PM_25,
            SensorType.GAS_LPG,
            SensorType.GAS_CO,
            SensorType.GAS_SMOKE,
            SensorType.DHT_TMP,
            SensorType.DHT_HUM
    };

    public ServiceData(Context context) {
        this.context = context;
    }

    boolean saveReading(SensorReading reading){
        SharedPreferences sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String prefValue = reading.getType().getName(context) + ".value";
        String prefTimestamp = reading.getType().getName(context) + ".timestamp";

        if(reading.getTimestamp() < getReading(reading.getType()).getTimestamp())
            return false;

        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(prefValue, reading.getValue());
        ed.putLong(prefTimestamp, reading.getTimestamp());
        ed.apply();

        return true;
    }

    public SensorReading getReading(SensorType type){
        SharedPreferences sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String prefValue = type.getName(context) + ".value";
        String prefTimestamp = type.getName(context) + ".timestamp";

        if(!sp.contains(prefValue) ||
           !sp.contains(prefTimestamp) ) return new SensorReading();

        int value = sp.getInt(prefValue, -1);
        long timestamp = sp.getLong(prefTimestamp, -1);
        return new SensorReading(type, value, timestamp);
    }

    public ArrayList<SensorReading> getAllData(){
        ArrayList<SensorReading> ls = new ArrayList<>();

        for(int i=0;i<allTypes.length;++i)
            ls.add(getReading(allTypes[i]));

        return ls;
    }

}
