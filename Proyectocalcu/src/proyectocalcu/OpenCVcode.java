/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalcu;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author maxhp
 */
public class OpenCVcode extends JFrame {
    
    private JLabel pantalla;
    
    private JButton btnCapture;
    
    private VideoCapture capture;
    private Mat image;
    
    public OpenCVcode(){
        
        setLayout(null);
        
        pantalla = new JLabel();
        
        pantalla.setBounds(0,0,640,560);
        add(pantalla);
        
        btnCapture = new JButton("Capture");
        btnCapture.setBounds(300,480,80,40);
        add(btnCapture);
        
        btnCapture.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                
            }
        });
        
        setSize(new Dimension(640,560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void StartCamera(){
        capture = new VideoCapture(0);
        image = new Mat();
        
        while (true){
            
        }
    }
    
    public static void main(String [] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run(){
                OpenCVcode camara = new OpenCVcode();
            }
        });
        
    }
            
}
