package br.edu.ufam.icomp.devtitans.qualityair.sensors;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.utils.Algorithm;

public enum SensorReadingStatus {
    //sdasd
    GOOD(0), MODERATE(1), BAD(2), VERY_BAD(3), TERRIBLE(4), INVALID(5);

    //STATIC
    private final static int[] nameIds = {
        R.string.status_good,
        R.string.status_moderate,
        R.string.status_bad,
        R.string.status_very_bad,
        R.string.status_terrible,
        R.string.nan
    };
    private static final int[][] typeVals = {
        {50*100, 100*100, 150*100, 250*100}, //PM_10 (*100)
        {25*100, 50*100, 75*100, 125*100}, //PM_25 (*100)
        {700*100000, 1000*100000}, //GAS_LPG (*1000)
        {10*100000, 50*100000, 200*100000, 400*100000}, //GAS_CO (*100)
        {10*100000, 50*100000, 200*100000, 400*100000}, //GAS_SMOKE (*100)
        {34*100, 38*100, 39*100}, //DHT_TMP (*100)
        {50*100, 60*100, 80*100} //DHT_HUM (*100)
    };

    //Members
    private final int value;


    private SensorReadingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SensorReadingStatus fromIndex(int i){
        switch(i){
            case 0: return SensorReadingStatus.GOOD;
            case 1: return SensorReadingStatus.MODERATE;
            case 2: return SensorReadingStatus.BAD;
            case 3: return SensorReadingStatus.VERY_BAD;
            case 4: return SensorReadingStatus.TERRIBLE;
            default: return INVALID;
        }
    }

    public static SensorReadingStatus getStatus(SensorType type, int val){
        if(val<0 || type==SensorType.INVALID) return SensorReadingStatus.INVALID;
        int i = Algorithm.lower_bound(typeVals[type.getValue()], val);
        return fromIndex(i);
    }

    public String getName(){
        return Resources.getSystem().getString(nameIds[getValue()]);
    }
}
