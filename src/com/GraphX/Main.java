package com.GraphX;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    public static void main(String[] args) {
        try {
            setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI",Font.PLAIN,14));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GraphVisualiser();
    }

}
