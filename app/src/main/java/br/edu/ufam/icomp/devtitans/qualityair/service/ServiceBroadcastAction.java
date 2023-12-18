package br.edu.ufam.icomp.devtitans.qualityair.service;

public interface ServiceBroadcastAction {
    public static final String base = "br.edu.ufam.icomp.devtitans.qualityair";
    public static final String QUALITY_AIR_PM10_CHANGED = base + ".pm_10_changed";
    public static final String QUALITY_AIR_PM25_CHANGED = base + ".pm_25_changed";
    public static final String QUALITY_AIR_GAS_CHANGED = base + ".gas_changed";
    public static final String QUALITY_AIR_HUMIDITY_CHANGED = base + ".humidity_changed";
}
