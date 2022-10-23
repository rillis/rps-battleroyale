package com.rpsb.materials;

import com.rpsb.Main;
import com.rpsb.config.Configs;
import com.rpsb.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Material {
    public static Map<Integer, Integer> COUNTERS_MAP = new HashMap<>();
    public static Map<Integer, ImageIcon> IMAGE_MAP = new HashMap<>();
    public static Map<Integer, Color> COLORS_MAP = new HashMap<>();
    public static Map<Integer, String> NAMES_MAP = new HashMap<>();
    public static ArrayList<Material> materials = new ArrayList<>();
    public static int MATERIAL_ROCK = 0;
    public static int MATERIAL_PAPPER = 1;
    public static int MATERIAL_SCISSORS = 2;
    public static int DRAW = -1;

    public static void init(int numberOfEach){
        COUNTERS_MAP.put(MATERIAL_ROCK, MATERIAL_PAPPER);
        COUNTERS_MAP.put(MATERIAL_PAPPER, MATERIAL_SCISSORS);
        COUNTERS_MAP.put(MATERIAL_SCISSORS, MATERIAL_ROCK);

        //GenImages
        IMAGE_MAP.put(MATERIAL_PAPPER, Utils.createFromPathAndResize("resources/papper.png", Configs.MATERIAL_WIDTH, Configs.MATERIAL_HEIGHT));
        IMAGE_MAP.put(MATERIAL_ROCK, Utils.createFromPathAndResize("resources/rock.png", Configs.MATERIAL_WIDTH, Configs.MATERIAL_HEIGHT));
        IMAGE_MAP.put(MATERIAL_SCISSORS, Utils.createFromPathAndResize("resources/scissors.png", Configs.MATERIAL_WIDTH, Configs.MATERIAL_HEIGHT));

        COLORS_MAP.put(MATERIAL_PAPPER, new Color(235, 222, 52));
        COLORS_MAP.put(MATERIAL_ROCK, new Color(127, 127, 127));
        COLORS_MAP.put(MATERIAL_SCISSORS, new Color(241, 69, 98));

        NAMES_MAP.put(MATERIAL_PAPPER, "Paper");
        NAMES_MAP.put(MATERIAL_ROCK, "Rock");
        NAMES_MAP.put(MATERIAL_SCISSORS, "Scissors");

        int id = 0;
        for (int type = 0; type < 3; type++) {
            for (int i = 0; i < numberOfEach; i++) {
                materials.add(new Material(type, id));
                id++;
            }
        }
    }

    int id;
    int type;
    ImageIcon icon;
    Point point;
    int xOff = 0;
    int yOff = 0;
    int xLeft = 0;
    int yLeft = 0;

    public Material(int type, int id) {
        this.id = id;
        this.type = type;
        this.icon = IMAGE_MAP.get(type);
        setPoint(Utils.randomBetween(0, Configs.WINDOW_WIDTH-Configs.MATERIAL_WIDTH), Utils.randomBetween(0, Configs.RING_HEIGHT-Configs.MATERIAL_HEIGHT));
    }

    public int getType() {
        return type;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }

    public int getId() {
        return id;
    }

    public void setPoint(int x, int y){
        point = new Point(Utils.normalize(0, Configs.WINDOW_WIDTH-Configs.MATERIAL_WIDTH, x), Utils.normalize(0, Configs.RING_HEIGHT-Configs.MATERIAL_HEIGHT, y));
    }

    public void setType(int type) {
        this.type = type;
        this.icon = IMAGE_MAP.get(type);
    }

    public Rectangle getRectangle(){
        return new Rectangle(point.x, point.y, Configs.MATERIAL_WIDTH, Configs.MATERIAL_HEIGHT);
    }

    public void walk(){
        int walkX = 0;
        int walkY = 0;
        if(xLeft == 0 && yLeft == 0){
            xOff = Utils.randomBinaryWalk();
            yOff = Utils.randomBinaryWalk();

            xLeft = Utils.randomBetween(Configs.MIN_WALK, Configs.MAX_WALK);
            yLeft = Utils.randomBetween(Configs.MIN_WALK, Configs.MAX_WALK);
        }

        if(xLeft>0){
            walkX = xOff;
            xLeft--;
        }

        if(yLeft>0){
            walkY = yOff;
            yLeft--;
        }

        setPoint(point.x + walkX, point.y + walkY);
    }

    public Material collideAny(){
        for(Material compare : materials){
            if(getId() != compare.id && collide(compare))
                return compare;
        }
        return null;
    }

    public boolean collide (Material compare) {
        Rectangle r = compare.getRectangle();
        int width = Configs.MATERIAL_WIDTH;
        int height = Configs.MATERIAL_HEIGHT;
        return getX() < r.x + r.width && getX() + width > r.x && getY() < r.y + r.height && getY() + height > r.y;
    }

    public Material[] battle(Material compare){
        if(compare.getType() == COUNTERS_MAP.get(getType()))
            return new Material[]{compare, this};
        if(getType() == COUNTERS_MAP.get(compare.getType()))
            return new Material[]{this, compare};
        return null; //Draw
    }

    public void update(){
        walk();

        Material compare = collideAny();
        if(compare != null){
            Material[] battleResult = battle(compare);
            if(battleResult != null){
                battleResult[1].setType(battleResult[0].getType());
            }
        }
    }
}
