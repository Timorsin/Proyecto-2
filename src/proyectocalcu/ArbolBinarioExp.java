
package proyectocalcu;

/**
 *
 * @author maxhp
 */
public class ArbolBinarioExp {
    NodoArbol raiz;
    
    public ArbolBinarioExp(){
    
        raiz = null;
    
    }
    
    public ArbolBinarioExp(String cadena){
       
        raiz = creaArbolBE(cadena);
    
    
    }
    
    public void reiniciarArbol(){
    
        raiz = null;
    
    }
    
    public void creaNodo(Object dato){
    
        raiz = new NodoArbol(dato);
    
    }
    
    public NodoArbol creaSubArbol(NodoArbol dato2, NodoArbol dato1, NodoArbol operador){
        operador.izquierdo = dato1;
        operador.derecho = dato2;
        return operador;
    }

    public boolean arbolVacio(){
        return raiz == null;
    }

     private String preorden(NodoArbol subArbol, String c){
        String cadena;
        cadena = "";
        if (subArbol != null){
            cadena = c + subArbol.dato.toString() + "\n" + preorden(subArbol.izquierdo,c) + preorden(subArbol.derecho,c);
        }
        return cadena;
    }
    
     private String inorden(NodoArbol subArbol, String c){
        String cadena;
        cadena = "";
        if (subArbol != null){
            cadena = c +  inorden(subArbol.izquierdo, c)+ subArbol.dato.toString() + "\n" + inorden(subArbol.izquierdo, c);
        }
        return cadena;
    }
    
    private String posorden(NodoArbol subArbol, String c){
        String cadena;
        cadena = "";
        if (subArbol != null){
            cadena = c +  posorden(subArbol.izquierdo, c) + posorden(subArbol.izquierdo, c) + subArbol.dato.toString() + "\n";
        }
        return cadena;
    }

    public String toString(int a){
        String cadena = "";
        switch(a){
            case 0 -> cadena = preorden(raiz,cadena);
            case 1 -> cadena = inorden(raiz,cadena);
            case 2 -> cadena = posorden(raiz,cadena);
       
        }
        return cadena;
    }
    
    private int prioridad(char c){
        int p = 100;
        p = switch(c){
            case '^' -> 30;
            case'*','/','%' -> 20;
            case '+','-','&','|','~'-> 10;
            default -> 0;
        };
        return p;
    }
    
    private boolean esOperador(char c){
        boolean resultado;
        resultado = switch(c){
            case '(',')','^','*','/','+','-','%','&','|','~' -> true;
            default -> false;
        };
        return resultado;
    }
    
    private int prioridad(String op) {
        return switch(op) {
            case "**" -> 50;  // Potencia tiene la prioridad más alta
            default -> prioridad(op.charAt(0));  // Para otros casos, usa la versión char
        };
    }
    
    private boolean esOperador(String op) {
        return switch(op) {
            case "**" -> true;
            default -> esOperador(op.charAt(0));
        };
    }
    
    private NodoArbol creaArbolBE(String cadena) {
        PilaArbolExp PilaOperadores = new PilaArbolExp();
        PilaArbolExp PilaExpresiones = new PilaArbolExp();

        int i = 0;
        while (i < cadena.length()) {
            char caracterEvaluado = cadena.charAt(i);

            if (caracterEvaluado == '(') {
                PilaOperadores.insertar(new NodoArbol(caracterEvaluado));
                i++;
            } else if (caracterEvaluado == ')') {
                while (!PilaOperadores.pilaVacia() && PilaOperadores.topePila().dato.toString().charAt(0) != '(') {
                    NodoArbol operador = PilaOperadores.quitar();
                    NodoArbol dato2 = PilaExpresiones.quitar();
                    NodoArbol dato1 = PilaExpresiones.quitar();
                    NodoArbol subArbol = creaSubArbol(dato2, dato1, operador);
                    PilaExpresiones.insertar(subArbol);
                }
                PilaOperadores.quitar();
                i++;
            } else if (Character.isDigit(caracterEvaluado) || (caracterEvaluado == '.' && i < cadena.length() - 1 && Character.isDigit(cadena.charAt(i+1)))) {
                StringBuilder numberBuilder = new StringBuilder();
                while (i < cadena.length() && (Character.isDigit(cadena.charAt(i)) || cadena.charAt(i) == '.')) {
                    numberBuilder.append(cadena.charAt(i));
                    i++;
                }
                PilaExpresiones.insertar(new NodoArbol(numberBuilder.toString()));
            } else if (esOperador(String.valueOf(caracterEvaluado))) {
                String operadorActual = String.valueOf(caracterEvaluado);

                if (caracterEvaluado == '*' && i < cadena.length() - 1 && cadena.charAt(i + 1) == '*') {
                    operadorActual = "**";
                    i++;  // Aumentamos el índice adicionalmente para manejar ambos asteriscos
                }

                while (!PilaOperadores.pilaVacia() && prioridad(operadorActual) <= prioridad(PilaOperadores.topePila().dato.toString())) {
                    NodoArbol operador = PilaOperadores.quitar();
                    NodoArbol dato2 = PilaExpresiones.quitar();
                    NodoArbol dato1 = PilaExpresiones.quitar();
                    NodoArbol subArbol = creaSubArbol(dato2, dato1, operador);
                    PilaExpresiones.insertar(subArbol);
                }

                PilaOperadores.insertar(new NodoArbol(operadorActual));
                i++;
            } else {
                i++;
            }
        }

        while (!PilaOperadores.pilaVacia()) {
            NodoArbol operador = PilaOperadores.quitar();
            NodoArbol dato2 = PilaExpresiones.quitar();
            NodoArbol dato1 = PilaExpresiones.quitar();
            NodoArbol subArbol = creaSubArbol(dato2, dato1, operador);
            PilaExpresiones.insertar(subArbol);
        }

        NodoArbol resultado = PilaExpresiones.quitar();
        return resultado;
    }

    public double EvaluaExpresion(){
        return evalua(raiz);
    }

    private double evalua(NodoArbol subArbol){
        double acum = 0;
        if (subArbol == null) {
            throw new IllegalArgumentException("Subárbol no puede ser nulo");
        }
        String operador = subArbol.dato.toString();
        if (!esOperador(operador)) {
            return Double.parseDouble(operador);
        } else {
            switch (operador) {
                case "**" -> acum = Math.pow(evalua(subArbol.izquierdo), evalua(subArbol.derecho));
                case "^" -> acum = (int)evalua(subArbol.izquierdo) ^ (int)evalua(subArbol.derecho); // Cambio: ^ ahora es XOR
                case "*" -> acum = evalua(subArbol.izquierdo) * evalua(subArbol.derecho);
                case "/" -> acum = evalua(subArbol.izquierdo) / evalua(subArbol.derecho);
                case "%" -> acum = evalua(subArbol.izquierdo) % evalua(subArbol.derecho);
                case "+" -> acum = evalua(subArbol.izquierdo) + evalua(subArbol.derecho);
                case "-" -> acum = evalua(subArbol.izquierdo) - evalua(subArbol.derecho);
                case "&" -> acum = (int)evalua(subArbol.izquierdo) & (int)evalua(subArbol.derecho); // Nuevo: Evaluación AND
                case "|" -> acum = (int)evalua(subArbol.izquierdo) | (int)evalua(subArbol.derecho); // Nuevo: Evaluación OR
                case "~" -> acum = ~(int)evalua(subArbol.izquierdo); // Nuevo: Evaluación NOT
            }
        }
        return acum;
    }
}
