/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalcu;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
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
    
    private boolean clicked = false;
    
    public String nombre;
    
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
                clicked = true;
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
        byte[] imageData;
        
        ImageIcon icon;
        
        while (true){
            capture.read(image);
            
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg",image,buf);
            
            imageData = buf.toArray();
            
            icon = new ImageIcon(imageData);
            
            pantalla.setIcon(icon);
            
            if(clicked){
                String name = JOptionPane.showInputDialog(this, "Introduzca el nombre de la imagen");
                if(name==null){
                    name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                }
                Imgcodecs.imwrite("images/" + name + ".jpg",image);
                
                clicked = false;
            }
        }
    }
    
    public static void main(String [] args){
        
        System.setProperty("java.library.path", "C:\\Users\\maxhp\\OneDrive\\Documentos\\NetBeansProjects\\opencv-4.8,0");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        /*Tesseract tesseract = new Tesseract();
        try{
            //Socket tusocket = new Socket("127.0.0.1",5000);
            
            //tesseract.setDatapath("C:\\Users\\maxhp\\Downloads\\Tess4J-3.4.8-src\\Tess4J");
            //String text = tesseract.doOCR(new File("C:\\Users\\maxhp\\OneDrive\\Documentos\\NetBeansProjects\\Proyectocalcu\\images\\1.jpg"));
            
           // DataOutputStream textoimagen = new DataOutputStream(tusocket.getOutputStream());
           // textoimagen.writeUTF(text);
            
            
        }catch(TesseractException e){
            System.out.println(e.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        */
        EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run(){
                OpenCVcode camara = new OpenCVcode();
                
                new Thread(new Runnable(){
                    
                    @Override
                    public void run(){
                        camara.StartCamera();
                    }
                    
                }).start();
            }
        });
        
    }
            
}
