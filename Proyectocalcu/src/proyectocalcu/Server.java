/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    public static void main (String []args){
       
        try {
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Iniciado");
            
            while(true){
                Socket misocket = servidor.accept();
                DataInputStream recibido = new DataInputStream(misocket.getInputStream());
                String cadena = recibido.readUTF();
                
                sc.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }
}