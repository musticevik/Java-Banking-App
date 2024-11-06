package guis;

import db_sys.User;

import javax.swing.*;

public abstract class BaseFrame extends JFrame{

    //store user info
    protected User user;


    public BaseFrame(String title, User user){
        this.user = user;

        initialize(title);
    }
    public BaseFrame(String title){
        initialize(title);
    }

    public void initialize(String title){
        setTitle(title);

        setSize(420,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        addGuiComponents();
    }

    protected abstract void addGuiComponents();
}
