
/**
 * OpenCVcode is a Java class for capturing images from a webcam using OpenCV.
 * It allows users to capture images and send them to a remote server.
 */
package proyectocalcu;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 * This class represents an application for capturing images from a webcam and sending them to a server.
 * It extends the JFrame class and provides a simple user interface for capturing images.
 */
public class OpenCVcode extends JFrame {

    private JLabel pantalla;
    private JButton btnCapture;
    private VideoCapture capture;
    private Mat image;
    private boolean clicked = false;
    public String nombre;

    /**
     * Constructor for the OpenCVcode class. Initializes the user interface.
     */
    public OpenCVcode() {

        setLayout(null);

        pantalla = new JLabel();

        pantalla.setBounds(0, 0, 640, 560);
        add(pantalla);

        btnCapture = new JButton("Capture");
        btnCapture.setBounds(300, 480, 80, 40);
        add(btnCapture);

        btnCapture.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clicked = true;
            }
        });

        setSize(new Dimension(640, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Starts capturing images from the webcam and sends them to a remote server when the "Capture" button is clicked.
     */
    public void StartCamera() {
        capture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;
        ImageIcon icon;

        while (true) {
            capture.read(image);

            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            imageData = buf.toArray();

            icon = new ImageIcon(imageData);

            pantalla.setIcon(icon);

            if (clicked) {
                String name = JOptionPane.showInputDialog(this, "Press OK");

                Imgcodecs.imwrite("images/" + "Ecuacion.jpg", image);

                try {
                    byte[] Data = Files.readAllBytes(Paths.get("images/Ecuacion.jpg"));

                    Socket tusocket = new Socket("127.0.0.1", 8000);

                    OutputStream outputStream = tusocket.getOutputStream();

                    outputStream.write(Data);

                    outputStream.flush();

                } catch (IOException ev) {
                    ev.printStackTrace();
                }

                clicked = false;
            }
        }
    }
}
