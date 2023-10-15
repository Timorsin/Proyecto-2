public class PilaArbolExp {
    private NodoPila tope;

    public PilaArbolExp(){
        tope = null;
    }

    public void insertar(NodoArbol elemento){
        NodoPila nuevo;
        nuevo = new NodoPila (elemento);
        nuevo.siguiente = tope;
        tope = nuevo;
    }
    
    public boolean PilaVacia(){
        return tope == null;
    }

    public NodoArbol topePila(){
        return tope.dato;

    }

    public void ReiniciarPila(){
        tope = null;

    }

    public NodoArbol quitar(){
        NodoArbol aux = null;
        if(!PilaVacia()){
            aux = tope.dato;
            tope = tope.siguiente;

        }
        return aux;
    }
}
