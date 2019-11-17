package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.*;

public class MainWindow extends JFrame {
    private JButton convert_button = new JButton("");
    private JButton add_key_button = new JButton("Add key");

    private JRadioButton shifr_button = new JRadioButton("Satchel");
    private JRadioButton deshifr_button = new JRadioButton("Decrypting");

    private JTextArea input_message = new JTextArea(10, 10);
    private JTextArea output_message = new JTextArea(10, 10);

    public static eHandler main_handler;
    public static LinkedList<Satchel.Key> key_list = new LinkedList<Satchel.Key>();
    public static Container main_container;

    private Satchel.Key current_key;

    public static Crypting method;

    public MainWindow (String s) {
        super(s);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Architecture.main_window_width, Architecture.main_window_height);

        main_handler = new eHandler();

        input_message.setLocation(Architecture.indentation, Architecture.indentation);
        input_message.setSize(Architecture.message_width, Architecture.message_height);
        input_message.setLineWrap(true);
        input_message.setWrapStyleWord(true);

        output_message.setLocation(Architecture.message_width + 2 * Architecture.indentation, Architecture.indentation);
        output_message.setSize(Architecture.message_width, Architecture.message_height);
        output_message.setLineWrap(true);
        output_message.setWrapStyleWord(true);

        convert_button.setLocation(Architecture.indentation, Architecture.message_height + Architecture.indentation + 20);
        convert_button.setSize(Architecture.button_width, Architecture.button_height);
        convert_button.addActionListener(main_handler);
        convert_button.setText("...");

        add_key_button.setLocation(Architecture.indentation, Architecture.message_height + Architecture.indentation + 60);
        add_key_button.setSize(Architecture.button_width, Architecture.button_height);
        add_key_button.addActionListener(main_handler);

        shifr_button.setLocation(2 * Architecture.indentation + Architecture.button_width, Architecture.message_height + Architecture.indentation + 20);
        shifr_button.setSize(2 * Architecture.button_width , Architecture.button_height);
        shifr_button.setSelected(true);

        deshifr_button.setLocation(2 * Architecture.indentation + Architecture.button_width, Architecture.message_height + Architecture.indentation + 60);
        deshifr_button.setSize(2 * Architecture.button_width, Architecture.button_height);

        ButtonGroup group = new ButtonGroup();
        group.add(shifr_button);
        group.add(deshifr_button);

        main_container = this.getContentPane();
        main_container.setLayout(null);
        main_container.add(input_message);
        main_container.add(convert_button);
        main_container.add(output_message);
        main_container.add(add_key_button);
        main_container.add(shifr_button);
        main_container.add(deshifr_button);

    }

    public class eHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == convert_button && shifr_button.isSelected()) {
                String input_text, output_text;
                input_text = input_message.getText();
                output_text = Satchel.crypt(input_text, current_key);
                output_message.setText(output_text);
            }
            if (actionEvent.getSource() == convert_button && deshifr_button.isSelected()) {
                String input_text, output_text;
                input_text = input_message.getText();
                output_text = Satchel.decrypt(input_text, current_key);
                output_message.setText(output_text);
            }
            if (actionEvent.getSource() == add_key_button) {
                KeyWindow tmp_window = new KeyWindow();
                tmp_window.setLocationRelativeTo(null);
            }

            for (Iterator<Satchel.Key> it = key_list.iterator(); it.hasNext(); ) {
                Satchel.Key key = it.next();
                if (actionEvent.getSource() == key.button) {
                    current_key = key;
                    convert_button.setText("(" + key.name + ")->");
                    break;
                }
            }
        }
    }
}
