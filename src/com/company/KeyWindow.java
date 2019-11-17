package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class KeyWindow extends JFrame {
    private JButton close_button = new JButton("Set");
    private eHandler handler = new eHandler();

    private JLabel l1 = new JLabel("Key letter: ");
    private JLabel l2 = new JLabel("Close key: ");
    private JLabel l3 = new JLabel("'m' value: ");

    private JTextField t1 = new JTextField(10);
    private JTextField[] t2 = new JTextField[8];
    private JTextField t3 = new JTextField(1);

    public static void GenerateParametres(JTextField[] t2, JTextField t3) {
        int random_max = 100;

        int sum = 0;
        int[] values = new int[8];
        int n;
        for (int i = 0; i < 8; ++i) {
            values[i] = sum + ThreadLocalRandom.current().nextInt(0, random_max);
            sum += values[i];
        }
        n = sum + ThreadLocalRandom.current().nextInt(0, random_max);

        for (int i = 0; i < 8; ++i) {
            t2[i].setText(Integer.toString(values[i]));
        }
        t3.setText(Integer.toString(n));
    }

    private int window_width = 585;
    private int window_height = 190;

    private int label_width = 100;
    private int label_height = 25;
    private int delta_width = 10;
    private int delta_height = 10;
    private int x0 = 20, y0 = 20;
    private int field_width_normal = 50;
    private int field_height_normal = 25;       // = label_height

    public KeyWindow () {
        super("Set key");

        Container container = this.getContentPane();
        container.setLayout(null);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(window_width, window_height);


        l1.setLocation(x0, y0);
        l1.setSize(label_width, label_height);

        l2.setLocation(x0, y0 + label_height + delta_height);
        l2.setSize(label_width, label_height);

        l3.setLocation(x0, y0 + 2 * label_height + 2 * delta_height);
        l3.setSize(label_width, label_height);


        t1.setLocation(x0 + label_width + delta_width, y0);
        t1.setSize(field_width_normal, field_height_normal);

        for (int i = 0; i < 8; ++i) {
            t2[i] = new JTextField(1);
            t2[i].setLocation(x0 + label_width + delta_width + i * (field_width_normal + 5), y0 + label_height + delta_height);
            t2[i].setSize(field_width_normal, field_height_normal);
        }

        t3.setLocation(x0 + label_width + delta_width, y0 + 2 * label_height + 2 * delta_height);
        t3.setSize(field_width_normal, field_height_normal);

        close_button.setSize(Architecture.button_width, Architecture.button_height);
        close_button.setLocation((window_width - Architecture.button_width) / 2, y0 + 3 * label_height + 3 * delta_height);
        close_button.addActionListener(handler);

        container.add(close_button);

        container.add(t1);
        for (int i = 0; i < 8; ++i)
            container.add(t2[i]);
        container.add(t3);

        container.add(l1);
        container.add(l2);
        container.add(l3);

        GenerateParametres(t2, t3);
    }

    public class eHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String name = t1.getText();
            int[] close_key = new int[8];
            for (int i = 0; i < 8; ++i) {
                close_key[i] = Integer.parseInt(t2[i].getText());
            }
            int m = Integer.parseInt(t3.getText());

            MainWindow.method.creae_key(name, close_key, m);
            dispose();
        }
    }

}
