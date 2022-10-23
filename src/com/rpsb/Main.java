package com.rpsb;

import com.rpsb.config.Configs;
import com.rpsb.gui.Ring;
import com.rpsb.materials.Material;
import com.rpsb.materials.UpdateLoop;

public class Main {
    public static void main(String[] args) {
        Configs.WINDOW_WIDTH = 600;
        Configs.WINDOW_HEIGHT = 620;
        Configs.RING_HEIGHT = 600;
        Configs.BAR_HEIGHT = Configs.WINDOW_HEIGHT - Configs.RING_HEIGHT;
        Configs.TICKS = 100;
        Configs.FPS = 60;
        Configs.MATERIAL_WIDTH = 20;
        Configs.MATERIAL_HEIGHT = 20;
        Configs.MAX_WALK = 40;
        Configs.MIN_WALK = 20;
        Configs.QUANTITY_ENTITIES = 60;


        Material.init(Configs.QUANTITY_ENTITIES);

        Ring ring = new Ring();
        ring.start();

        UpdateLoop.start();
    }
}