package br.edu.ufam.icomp.devtitans.qualityair.sensors;

import android.content.Context;

import br.edu.ufam.icomp.devtitans.qualityair.R;

public class SensorReading {
    private SensorType type; // sensor type
    private SensorReadingStatus status;
    private int value; // read value
    private long timestamp; // time millis

    public SensorReading(){
        this.type = SensorType.INVALID;
        this.status = SensorReadingStatus.INVALID;
        this.value = -1;
        this.timestamp = -1;
    }
    public SensorReading(SensorType type, int value, long timestamp) {
        this.type = type;
        this.status = SensorReadingStatus.getStatus(type, value);
        this.value = Math.max(value, -1);
        this.timestamp = timestamp;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public SensorReadingStatus getStatus() {
        return status;
    }

    public void setStatus(SensorReadingStatus status) {
        this.status = status;
    }

    public int getValue() {
        return value;
    }
    public double getValueExact(){
        switch(type){
            case PM_10:
            case PM_25:
            case DHT_TMP:
            case DHT_HUM:
                return ((double)getValue())/100.0;
            default:
                return ((double)getValue())/100000.0;
        }
    }

    public String getValueExactUnit(Context context){
        if(value<0) return "Atualizando...";

        String val = String.valueOf(getValueExact());

        switch(type){
            case PM_10:
            case PM_25:
                val += " " + context.getString(R.string.mp_unit); break;
            case GAS_LPG:
            case GAS_SMOKE:
            case GAS_CO:
                val += " " + context.getString(R.string.gas_unit); break;
            case DHT_TMP:
                val += " C°"; break;
            case DHT_HUM:
                val += " " + context.getString(R.string.humidity_unit); break;
        }

        return val;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
