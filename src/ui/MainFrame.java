package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Uttarakhand Bus Management System");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new SearchRoutePanel());
        setVisible(true);
    }
}
