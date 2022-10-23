package com.rpsb.utils;

import com.rpsb.Main;

import javax.swing.*;
import java.awt.*;

public class Utils {
    public static int randomBinary(){
        return (int) Math.round(Math.random());
    }
    public static int randomBinaryWalk(){
        return randomBetween(0, 2) - 1;
    }

    public static int normalize(int min, int max, int number){
        return Math.max(min, Math.min(max, number));
    }

    public static int randomBetween(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static ImageIcon createFromPathAndResize(String path, int width, int height){
        return new ImageIcon(new ImageIcon(Main.class.getResource(path)).getImage().getScaledInstance(width, height, Image.SCALE_FAST));
    }
}
