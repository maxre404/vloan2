package com.tg.vloan.config;

import androidx.collection.ArrayMap;

/**
 * Created by frcx-hb on 2022/12/1 22:23.
 */
public final class Configurator {
    private static final ArrayMap<String,Object> HIPPIUS_CONFIG = new ArrayMap<>();

    private Configurator(){
    }

    static Configurator getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static final Configurator instance = new Configurator();
    }

    void putConfig(String key,Object object){
        HIPPIUS_CONFIG.put(key,object);
    }

    <T> T getConfig(String key){
        return (T)HIPPIUS_CONFIG.get(key);
    }

}
