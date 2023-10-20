/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectocalcu;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author maxhp
 */
public class Servidor extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form Servidor
     */
    public Servidor() {
        initComponents();
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        area.setColumns(20);
        area.setRows(5);
        jScrollPane1.setViewportView(area);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void run(){
       
        try {
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Iniciado");
          
            while(true){
                Socket misocket = servidor.accept();
                
                DataInputStream recibido = new DataInputStream(misocket.getInputStream());
                
                String cadena = recibido.readUTF();
                
                area.setText(cadena);
                ArbolBinarioExp ABE = new ArbolBinarioExp(cadena);
    
                DataOutputStream respaquete = new DataOutputStream(misocket.getOutputStream());
                respaquete.writeUTF("" + ABE.EvaluaExpresion());
                
                //servidor.close();
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        Tesseract tesseract = new Tesseract();
        
        try{
            ServerSocket servidorIm = new ServerSocket(6000);
            System.out.println("Iniciado");
            
            while(true){
                Socket tusocket = servidorIm.accept();
                InputStream imgEc = tusocket.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                
                int nRead;
                byte[] data = new byte[1024];
                while((nRead = imgEc.read(data,0,data.length)) != -1){
                    buffer.write(data,0,nRead);
                    
                }
                buffer.flush();
                byte[] imageData = buffer.toByteArray();
                
                FileOutputStream outputStream = new FileOutputStream("Ecuacion.jpg");
                outputStream.write(imageData);
      
                tesseract.setDatapath("C:\\Users\\maxhp\\Downloads\\Tess4J-3.4.8-src\\Tess4J");
                String text = tesseract.doOCR(new File("Ecuacion.jpg"));
                
                ArbolBinarioExp ABE = new ArbolBinarioExp(text);
                
                DataOutputStream respaquete2 = new DataOutputStream(tusocket.getOutputStream());
                respaquete2.writeUTF("" + ABE.EvaluaExpresion());
            }
        }catch(TesseractException e){
            System.out.println(e.toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
