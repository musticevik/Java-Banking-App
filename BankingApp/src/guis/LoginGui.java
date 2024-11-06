package guis;

import db_sys.MyJDBC;
import db_sys.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGui extends BaseFrame{

    public LoginGui() {
        super("Banking Application");
    }

    @Override
    protected void addGuiComponents() {
        //banking app label
        JLabel bankingAppLabel = new JLabel("Banking Application");

        //setting the labels location
        bankingAppLabel.setBounds(0,20,super.getWidth(),40);

        //font changes
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        //center text
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(bankingAppLabel);


        //username label
        JLabel usernameLabel = new JLabel("Username:");

        usernameLabel.setBounds(20,120,getWidth()-30,24);

        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);


        //create username field

        JTextField usernameField = new JTextField();
        usernameField.setBounds(20,160,getWidth()-50,40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        //create password label
        JLabel passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(20,230,getWidth()-50,24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        //create password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20,270,getWidth()-50,40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        //create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20,460,getWidth()-50,40);
        passwordField.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();

                String password = String.valueOf(passwordField.getPassword());

                User user = MyJDBC.validateLogin(username, password);

                if(user != null) {
                    LoginGui.this.dispose();

                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    JOptionPane.showMessageDialog(bankingAppGui, "Login Successful");
                } else {
                    JOptionPane.showMessageDialog(LoginGui.this, "Invalid Username or Password");
                }
            }
        });
        add(loginButton);

        //create register Label
        JLabel registerLabel = new JLabel("<html><a href=\"#\"> Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0,510,getWidth()-10,30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginGui.this.dispose();

                new RegisterGui().setVisible(true);
            }
        });
        add(registerLabel);




    }
}
