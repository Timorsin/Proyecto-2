
package proyectocalcu;

/**
 * The ArbolBinarioExp class represents a binary expression tree used for evaluating mathematical expressions.
 * It allows the creation of expression trees from string expressions and their evaluation.
 */

public class ArbolBinarioExp {
    NodoArbol raiz;
    
    /**
     * Default constructor for the ArbolBinarioExp class.
     * Initializes the root node to null.
     */
    
    public ArbolBinarioExp(){
    
        raiz = null;
    
    }
    
    /**
     * Constructor for the ArbolBinarioExp class that creates an expression tree from a given string expression.
     *
     * @param cadena The string expression to be converted into an expression tree.
     */
    
    public ArbolBinarioExp(String cadena){
       
        raiz = creaArbolBE(cadena);
    
    
    }
    
    /**
     * Resets the expression tree by setting the root node to null.
     */
    
    public void reiniciarArbol(){
    
        raiz = null;
    
    }
    
    /**
     * Creates a new root node with the given data.
     *
     * @param dato The data for the new root node.
     */
    
    public void creaNodo(Object dato){
    
        raiz = new NodoArbol(dato);
    
    }
    
    /**
     * Creates a subtree with the given data and left and right children.
     *
     * @param dato2     The data for the new root node.
     * @param dato1     The left child of the root node.
     * @param operador  The right child of the root node.
     * @return The root node of the created subtree.
     */
    
    public NodoArbol creaSubArbol(NodoArbol dato2, NodoArbol dato1, NodoArbol operador){
        operador.izquierdo = dato1;
        operador.derecho = dato2;
        return operador;
    }
    
    /**
     * Checks if the expression tree is empty.
     *
     * @return true if the tree is empty, false otherwise.
     */
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
    
    /**
     * Returns a string representation of the expression tree in pre-order, in-order, or post-order format.
     *
     * @param a The order in which to traverse the tree (0 for pre-order, 1 for in-order, 2 for post-order).
     * @return The string representation of the expression tree in the specified order.
     */
    
    public String toString(int a){
        String cadena = "";
        switch(a){
            case 0 -> cadena = preorden(raiz,cadena);
            case 1 -> cadena = inorden(raiz,cadena);
            case 2 -> cadena = posorden(raiz,cadena);
       
        }
        return cadena;
    }
    
    /**
     * Determines the priority of an operator character.
     *
     * @param c The operator character.
     * @return The operator's priority level (higher values indicate higher priority).
     */
    
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
    
    /**
     * Checks if a character is an operator.
     *
     * @param c The character to check.
     * @return true if the character is an operator, false otherwise.
     */
    
    private boolean esOperador(char c){
        boolean resultado;
        resultado = switch(c){
            case '(',')','^','*','/','+','-','%','&','|','~' -> true;
            default -> false;
        };
        return resultado;
    }
    
    /**
     * Determines the priority of an operator string.
     *
     * @param op The operator string.
     * @return The operator's priority level (higher values indicate higher priority).
     */
    
    private int prioridad(String op) {
        return switch(op) {
            case "**" -> 50;  // Potencia tiene la prioridad más alta
            default -> prioridad(op.charAt(0));  // Para otros casos, usa la versión char
        };
    }
    
    /**
     * Checks if a string represents an operator.
     *
     * @param op The string to check.
     * @return true if the string is an operator, false otherwise.
     */
    
    private boolean esOperador(String op) {
        return switch(op) {
            case "**" -> true;
            default -> esOperador(op.charAt(0));
        };
    }
    
    /**
     * Creates an expression tree from a given string expression.
     *
     * @param cadena The string expression to be converted into an expression tree.
     * @return The root node of the created expression tree.
     */
    
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
    /**
     * Evaluates the expression tree and returns the result as a double.
     *
     * @return The result of the expression evaluation.
     */
    public double EvaluaExpresion(){
        return evalua(raiz);
    }
    
    /**
     * Recursively evaluates the expression tree starting from the given root node.
     *
     * @param subArbol The root node of the subtree to evaluate.
     * @return The result of the subtree evaluation.
     */
    
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
