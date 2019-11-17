package com.company;


import javax.swing.*;

public class Satchel extends Crypting {

    public static char[] input;
    public static String output;

    @Override
    public static void create_key(String name, int[] close_key, int m) {
        Key new_key = new Key(name, close_key, m);

        new_key.button = new JButton(name);
        MainWindow.key_list.add(new_key);
        MainWindow.main_container.add(new_key.button);
        new_key.button.setSize(50, 50);
        new_key.button.setLocation(Architecture.indentation + 55 * (MainWindow.key_list.size() - 1), Architecture.message_height + Architecture.indentation + 100);
        new_key.button.addActionListener(MainWindow.main_handler);
    }

    @Override
    public String crypt(String input_, Key key) {
        input = input_.toCharArray();
        output = "";

        char source_symbol, crypt_symbol;
        for (int i = 0; i < input_.length(); ++i) {
            source_symbol = input[i];
            crypt_symbol = 0;
            for (int j = 0; j < 8; ++j) {
                if (source_symbol >= Math.pow(2, 7 - j)) {
                    source_symbol -= Math.pow(2, 7 - j);
                    crypt_symbol += key.open_key[j];
                }
            }
            output += Integer.toString(crypt_symbol) + " ";
        }

        return output;
    }

    @Override
    public String decrypt(String input_, Key key) {
        input = input_.toCharArray();
        output = "";

        int number, height;
        char decrypt_symbol;
        for (int i = 0; i < input_.length(); ++i) {
            number = 0;
            decrypt_symbol = 0;
            while (isNum(input[i])) {
                number *= 10;
                number += (int) (input[i] - '0');
                i++;
            }
            height = (number * key.d) % key.m;
            for (int j = 7; j >= 0; --j) {
                if (height >= key.close_key[j]) {
                    height -= key.close_key[j];
                    decrypt_symbol += Math.pow(2, 7 - j);
                }
            }
            output += decrypt_symbol;
        }

        return output;
    }

    private static boolean isNum(char c) {
        return (c >= '0') && (c <= '9');
    }

    private static int nod(int arg1, int arg2) {
        while (arg1 * arg2 != 0) {
            if (arg1 > arg2) {
                arg1 -= arg2;
            } else {
                arg2 -= arg1;
            }
        }
        if (arg1 != 0) {
            return arg1;
        } else {
            return arg2;
        }
    }

    public class Key extends Crypting.Key {

        private int[] open_key = new int[8];
        private int[] close_key = new int[8];
        private int m, n, d;
        public String name;

        public Key(String name, int[] close_key, int m) {
            int n, d;
            for (n = 2; nod(m, n) != 1; n++) ;
            for (d = 0; (d * n) % m != 1; d++) ;

            this.m = m;
            this.n = n;
            this.d = d;
            this.name = name;

            for (int i = 0; i < 8; ++i) {
                this.close_key[i] = close_key[i];
                this.open_key[i] = (n * close_key[i]) % m;
            }
        }
    }
}
