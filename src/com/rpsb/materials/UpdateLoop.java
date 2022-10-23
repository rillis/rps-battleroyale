package com.rpsb.materials;

import com.rpsb.config.Configs;

public class UpdateLoop {
    private static boolean loop = true;
    public static void start(){
        new Thread(() -> {
            while(loop){
                try {
                    Thread.sleep(1000 / Configs.TICKS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(Material material : Material.materials){
                    material.update();
                }
            }
        }).start();
    }
    public static void pause(){
        loop = false;
    }
    public static void resume(){
        loop = true;
    }
}
