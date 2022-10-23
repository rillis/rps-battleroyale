package com.rpsb.gui;

import com.rpsb.config.Configs;
import com.rpsb.materials.Material;
import com.rpsb.materials.UpdateLoop;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Ring extends JFrame {
    private final JPanel contentPane;
    private final JPanel ring;
    private final JPanel bar;

    private final Map<Integer, JLabel> bars = new HashMap<>();

    public Ring(){
        setSize(Configs.WINDOW_WIDTH, Configs.WINDOW_HEIGHT);
        setLayout(null);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(0, 0, Configs.WINDOW_WIDTH, Configs.WINDOW_HEIGHT);
        setContentPane(contentPane);

        ring = new JPanel();
        ring.setLayout(null);
        ring.setBounds(0, 0, Configs.WINDOW_WIDTH, Configs.RING_HEIGHT);
        contentPane.add(ring);

        bar = new JPanel();
        bar.setLayout(null);
        bar.setBounds(0, ring.getY()+ring.getHeight()+1, Configs.WINDOW_WIDTH, Configs.BAR_HEIGHT);
        contentPane.add(bar);

        for(int type = 0; type < 3; type++){
            JLabel label = new JLabel();
            label.setBounds((bar.getWidth()/3)*type, 0, bar.getWidth()/3, bar.getHeight());
            label.setBackground(Material.COLORS_MAP.get(type));
            label.setText("33%");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            bars.put(type, label);
            bar.add(label);
        }



        setVisible(true);
    }

    private boolean loop = true;
    public void start(){
        new Thread(() -> {
            while(loop){
                ring.removeAll();

                Map<Integer,Integer> counter = new HashMap<>();

                for(Material material : Material.materials){
                    JLabel label = new JLabel();
                    label.setLocation(material.getX(), material.getY());
                    label.setSize(Configs.MATERIAL_WIDTH, Configs.MATERIAL_HEIGHT);
                    label.setIcon(material.getIcon());

                    counter.put(material.getType(), counter.getOrDefault(material.getType(), 0) + 1);

                    ring.add(label);
                }

                int lastX = 0;
                for(int type = 0; type < 3; type++){

                    if(counter.getOrDefault(type, 0) == Configs.QUANTITY_ENTITIES*3){
                        JOptionPane.showMessageDialog(null, "The " + Material.NAMES_MAP.get(type) + " won!");
                        System.exit(0);

                    }

                    double perc = (double) counter.getOrDefault(type, 0) / (double) (Configs.QUANTITY_ENTITIES*3);
                    int x = lastX;
                    int w = (int) (perc*Configs.WINDOW_WIDTH);
                    lastX += w;
                    bars.get(type).setBounds(x, 0, w, bar.getHeight());
                    bars.get(type).setText(((int) (perc*100))+"%");
                }

                repaint();
                revalidate();
                try {
                    Thread.sleep(1000 / Configs.FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void pause(){
        loop = false;
    }
    public void resume(){
        loop = true;
    }
}
