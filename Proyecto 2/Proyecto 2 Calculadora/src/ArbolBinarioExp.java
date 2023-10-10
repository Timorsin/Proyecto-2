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

    public void creaNodo(Object Dato){
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
            cadena = c + subArbol.dato.toString() + "\n" + preorden(subArbol.izquierdo, c) + preorden(subArbol.derecho, c);
        }
        return cadena;
    }

    private String inorden(NodoArbol subArbol, String c){
        String cadena;
        cadena = "";
        if (subArbol != null){
            cadena = c + inorden(subArbol.izquierdo, c) + subArbol.dato.toString() + "\n" + inorden(subArbol.derecho, c);
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
            case 0:
            cadena = preorden(raiz,cadena);
            break;
            case 1:
            cadena = inorden(raiz,cadena);
            break;
            case 2:
            cadena = posorden(raiz,cadena);

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
                resultado = true;
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
                            op = creaArbolBE(op2 op1, op);
                            PilaExpresiones.insert(op);

                        }
                        PilaOperadores.quitar();
                        break;
                    default:
                    

                }
            }

        }
    }

    public double EvaluaExpresion(){

    }
}