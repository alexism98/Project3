import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageUserUI {

    public JFrame view;

    public JButton btnLoad = new JButton("Load User");
    public JButton btnSave = new JButton("Save User");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JTextField(20);
    public JTextField txtFullName = new JTextField(20);
    public JTextField txtUserType = new JTextField(20);


    public ManageUserUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Manage User Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        line1.add(txtUsername);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Password "));
        line2.add(txtPassword);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Full Name "));
        line3.add(txtFullName);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("User Type "));
        line4.add(txtUserType);
        view.getContentPane().add(line4);


        btnLoad.addActionListener(new LoadButtonListerner());

        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            String username = txtUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                return;
            }

            try {

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_USER;
                msg.data = username;

//                msg = StoreManager.getInstance().getNetworkAdapter().send(msg, "localhost", 1000);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Customer NOT exists!");
                }
                else {
                    UserModel user = gson.fromJson(msg.data, UserModel.class);
                    txtUsername.setText(user.mUsername);
                    txtPassword.setText(user.mPassword);
                    txtFullName.setText(user.mFullname);
                    txtUserType.setText("" + user.mUserType);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();
            Gson gson = new Gson();
            String username = txtUsername.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty!");
                return;
            }

            user.mUsername = username;

            String password = txtPassword.getText();
            if (password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty!");
                return;
            }

            user.mPassword = password;

            String name = txtFullName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                return;
            }

            user.mFullname = name;

            String type = txtUserType.getText();
            if (type.length() == 0) {
                JOptionPane.showMessageDialog(null, "User Type cannot be empty!");
                return;
            }

            try {
                user.mUserType = Integer.parseInt(type);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "User Type is invalid!");
                return;
            }

            try {
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_USER;
                msg.data = gson.toJson(user);

//                msg = StoreManager.getInstance().getNetworkAdapter().send(msg, "localhost", 1000);

                if (msg.code == MessageModel.OPERATION_FAILED)
                    JOptionPane.showMessageDialog(null, "User is NOT saved successfully!");
                else
                    JOptionPane.showMessageDialog(null, "User is SAVED successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}