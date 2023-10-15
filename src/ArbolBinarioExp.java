public class ArbolBinarioExp {
    NodoArbol raiz;

    public ArbolBinarioExp () {
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
            case 0:
            cadena = posorden(raiz,cadena);
            break;

        }
        return cadena;
    }
    private int prioridad(char c){
        int p = 100;
        switch(c){
            case '^':
                p = 30;
                break;
            case '*':
            case '/':
                p = 20;
                break;
            case '+':
            case '-':
                p = 10;
                break;
            default:
                p=0;
        }
        return p;
    }

    private boolean esOperador(char c){
        boolean resultado;
        switch(c){
            case '(':
            case ')':
            case '^':
            case '*':
            case '/':
            case '+':
            case '-':
                resultado = true;
            default:
                resultado = false;
        }
        return resultado;

    }

    private NodoArbol creaArbolBE(String cadena){
        PilaArbolExp PilaOperadores;
        PilaArbolExp PilaExpresiones;
        NodoArbol token;
        NodoArbol op1;
        NodoArbol op2;
        NodoArbol op;
        PilaOperadores = new PilaArbolExp();
        PilaExpresiones = new PilaArbolExp();
        char caracterEvaluado;
        for (int i = 0; i<cadena.length(); i++){
            caracterEvaluado = cadena.charAt(i);
            token = new NodoArbol(caracterEvaluado);
            if(!esOperador(caracterEvaluado)){
                PilaExpresiones.insertar(token);
            }
            else{
                switch(caracterEvaluado){
                    case'(':
                        PilaOperadores.insertar(token);
                        break;
                    case ')':
                        while(!PilaOperadores.PilaVacia() && !PilaOperadores.topePila().dato.equals('(')){
                            op2 = PilaExpresiones.quitar();
                            op1 = PilaExpresiones.quitar();
                            op = PilaExpresiones.quitar();
                            op = creaSubArbol(op2, op1, op);
                            PilaExpresiones.insertar(op);

                        }
                        PilaOperadores.quitar();
                        break;
                    default:
                        while (!PilaOperadores.PilaVacia() && prioridad(caracterEvaluado)<= prioridad(PilaOperadores.topePila().dato.toString().charAt(0))){
                            op2 = PilaExpresiones.quitar();
                            op1 = PilaExpresiones.quitar();
                            op = PilaOperadores.quitar();
                            op = creaSubArbol(op2, op1, op);
                            PilaExpresiones.insertar(op);
                        }
                        PilaOperadores.insertar(token);


                }
            }

        }
        while(!PilaOperadores.PilaVacia()){
            op2 = PilaExpresiones.quitar();
            op1 = PilaExpresiones.quitar();
            op = PilaOperadores.quitar();
            op = creaSubArbol(op2, op1, op);
            PilaExpresiones.insertar(op);
        }
        op = PilaExpresiones.quitar();
        return op;
    }

    public double EvaluaExpresion(){
        return evalua(raiz);
    }

    private double evalua(NodoArbol subArbol){
        double acum = 0;
        if(!esOperador(subArbol.dato.toString().charAt(0))){
            return Double.parseDouble(subArbol.dato.toString());
        }else{
            switch(subArbol.dato.toString().charAt(0)){
                case '^':
                    acum = acum + Math.pow(evalua(subArbol.izquierdo),evalua(subArbol.derecho));
                    break;
                case '*':
                    acum = acum + evalua(subArbol.izquierdo) * evalua(subArbol.derecho);
                    break;
                case '/':
                    acum = acum + evalua(subArbol.izquierdo) / evalua(subArbol.derecho);
                    break;

                case '+':
                    acum = acum + evalua(subArbol.derecho) + evalua(subArbol.derecho);
                    break;

                case '-':
                    acum = acum + evalua(subArbol.izquierdo) - evalua(subArbol.derecho);
                    break;
                    
            }
        }
        return acum;
    }
}