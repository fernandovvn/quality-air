package br.edu.ufam.icomp.devtitans.qualityair.utils;

public class Sensors {

    public static int getPm25(){
        return Algorithm.randomRange(0,300);
    }

    public static int getPm10(){
        return Algorithm.randomRange(0,300);
    }

    public static int getGas(){
        return Algorithm.randomRange(0,2000);
    }

    public static int getHumidity(){
        return Algorithm.randomRange(0,100);
    }

}
