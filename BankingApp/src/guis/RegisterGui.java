package guis;

import com.mysql.cj.log.Log;
import db_sys.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGui extends BaseFrame{
    public RegisterGui() {
        super("Banking App Register");
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

        passwordLabel.setBounds(20,220,getWidth()-50,24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        //create password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20,270,getWidth()-50,40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        //re-type password label
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(20,320,getWidth()-50,24);
        confirmPasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 19));
        add(confirmPasswordLabel);

        //create re-type password field
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(20,360,getWidth()-50,40);
        confirmPasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(confirmPasswordField);

        //create register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20,460,getWidth()-50,40);
        passwordField.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();

                String password = String.valueOf(passwordField.getPassword());

                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

                if (validateUserInput(username, password, confirmPassword)) {
                    //attempt to register the user to the DB
                    if(MyJDBC.register(username, password)) {
                        //register success and dispose
                        RegisterGui.this.dispose();

                        //login gui
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);

                        //result dialog
                        JOptionPane.showMessageDialog(loginGui, "Registered Successfully!");
;                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this,"Error:Username already taken!");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this,"Invalid Username or Password!");
                }
            }
        });
        add(registerButton);

        //create login Label
        JLabel loginLabel = new JLabel("<html><a href=\"#\"> Have an account? Sign-in Here</a></html>");
        loginLabel.setBounds(0,510,getWidth()-10,30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);
    }


    private boolean validateUserInput(String username, String password,String confirmPassword) {

        if(username.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            JOptionPane.showMessageDialog(null, "Please enter a valid username/password");
            return false;
        }

        if(username.length() <6) return  false;
        if(!(password.equals(confirmPassword))) return false;
        return true;

    }
}
