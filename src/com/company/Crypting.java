package com.company;

import javax.swing.*;

public abstract class Crypting {
    public abstract String crypt(String input_, Key key);
    public abstract String decrypt(String input_, Key key);
    public abstract void create_key();

    public abstract static class Key {
        public JButton button;
    };
}
