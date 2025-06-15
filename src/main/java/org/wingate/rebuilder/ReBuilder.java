package org.wingate.rebuilder;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class ReBuilder {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            try{
                System.out.println("Re::UI::Builder is about to start...");
                FlatLightLaf.setup();
                MainFrame mf = new MainFrame();
                mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mf.setTitle("Re::UI::Builder");
                mf.setSize(1900, 1000);
                mf.setLocationRelativeTo(null);
                mf.setVisible(true);
                System.out.println("Re::UI::Builder has started!");
            }catch(Exception _){
                System.exit(1);
            }
        });
    }
}
