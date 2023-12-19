package br.edu.ufam.icomp.devtitans.qualityair.sensors;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;

import br.edu.ufam.icomp.devtitans.qualityair.R;

public enum SensorType {
    PM_10(0), PM_25(1),
    GAS_LPG(2), GAS_CO(3), GAS_SMOKE(4),
    DHT_TMP(5), DHT_HUM(6),
    INVALID(7);

    private final static int[] nameIds = {
        R.string.sensor_pm10_name,
        R.string.sensor_pm25_name,
        R.string.sensor_gas_lpg_name,
        R.string.sensor_gas_co_name,
        R.string.sensor_gas_smoke_name,
        R.string.sensor_dht_tmp_name,
        R.string.sensor_dht_humidity_name,
        R.string.nan
    };
    private final static int[] descIds = {
            R.string.sensor_pm10_desc,
            R.string.sensor_pm25_desc,
            R.string.sensor_gas_lpg_desc,
            R.string.sensor_gas_co_desc,
            R.string.sensor_gas_smoke_desc,
            R.string.sensor_dht_tmp_desc,
            R.string.sensor_dht_humidity_desc,
            R.string.nan
    };

    
    private final int value;

    private SensorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName(Context context){
        return context.getString(nameIds[getValue()]);
    }

    public String getDescription(Context context){
        return context.getString(descIds[getValue()]);
    }

}
