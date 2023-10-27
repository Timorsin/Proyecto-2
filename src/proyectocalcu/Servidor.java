
package proyectocalcu;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Servidor extends javax.swing.JFrame implements Runnable {
    
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
        labelimg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        area.setColumns(20);
        area.setRows(5);
        jScrollPane1.setViewportView(area);

        labelimg.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelimg, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addComponent(labelimg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) throws FileNotFoundException{
        
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
        
        File csvFile = new File("Registro.csv");
        PrintWriter out = new PrintWriter(csvFile);
        
        
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelimg;
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
                
                Date fecha = new Date();
                SimpleDateFormat dateform = new SimpleDateFormat("MM/dd/YY");
                
                FileWriter out = new FileWriter("Registro.csv", true);
                out.append( "\nOperacion: " + cadena + " Resultado: " + ABE.EvaluaExpresion()+ " Fecha: " + dateform.format(fecha));
                out.close();
                
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        Tesseract tesseract = new Tesseract();
        
        try {
            ServerSocket serverIm = new ServerSocket(8000);
            
            while(true){
                Socket tusocket = serverIm.accept();
                InputStream imageDataStream = tusocket.getInputStream();
                
                int bytesAvailable = imageDataStream.available();

                byte[] imageData = new byte[bytesAvailable];

                imageDataStream.read(imageData);

                // Create a BufferedImage from the received image data.
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));

                // Display the image in the GUI.
                ImageIcon icon = new ImageIcon(image);
                labelimg.setIcon(icon);

                // Save the image to a file.
                ImageIO.write(image, ".jpg", new File("imagenServer/" + "EcuacionRecibida.jpg"));

                // Perform OCR on the image.
                //tesseract.setDatapath("Proyectocalcu\Tessdata");
                String text = tesseract.doOCR(new File("EcuacionRecibida.jpg"));

                // Evaluate the expression and send the result back to the client.
                //ArbolBinarioExp ABE = new ArbolBinarioExp(text);
                //DataOutputStream respaquete2 = new DataOutputStream(tusocket.getOutputStream());
                //respaquete2.writeUTF("" + ABE.EvaluaExpresion());
            }
            } catch (IOException | TesseractException e) {
            e.printStackTrace();
            }

    }
}
