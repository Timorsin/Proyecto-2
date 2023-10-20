/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalcu;

/**
 *
 * @author maxhp
 */
public class operador {
    public int a = 5;
    public int b = 3;
    public int c = ~a&b|a|a|a&b;

    public void printC() {
        System.out.println(c);
    }

    public static void main(String[] args) {
        operador operador = new operador();
        operador.printC();
    }
}

