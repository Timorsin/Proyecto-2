
package proyectocalcu;

/**
 * The PilaArbolExp class represents a stack used for managing binary expression tree nodes.
 * It provides methods for inserting, checking if the stack is empty, getting the top element,
 * resetting the stack, and removing elements from the stack.
 */
public class PilaArbolExp {
    
    private NodoPila tope;
    
    /**
     * Default constructor for the PilaArbolExp class.
     * Initializes the top of the stack to null.
     */
    
    public PilaArbolExp(){
    
        tope = null;
    
    }
    
    /**
     * Inserts a new binary expression tree node into the stack.
     *
     * @param elemento The binary expression tree node to be inserted.
     */
    
    public void insertar(NodoArbol elemento){
    
        NodoPila nuevo;
        nuevo = new NodoPila(elemento);
        nuevo.siguiente = tope;
        tope = nuevo;
    
    }
    
    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise.
     */
    
    public boolean pilaVacia(){
    
        return tope == null;
    
    }
    
    /**
     * Returns the top element of the stack, which is a binary expression tree node.
     *
     * @return The top binary expression tree node on the stack.
     */
    
    public NodoArbol topePila(){
    
        return tope.dato;
    
    }
    
     /**
     * Resets the stack by setting the top element to null.
     */
    
    public void ReiniciarPila(){
    
        tope = null;
    
    }
    
    /**
     * Removes and returns the top element of the stack, which is a binary expression tree node.
     *
     * @return The top binary expression tree node removed from the stack.
     */
    
    public NodoArbol quitar(){
    
        NodoArbol aux = null;
        if(!pilaVacia()){

            aux = tope.dato;
            tope = tope.siguiente;
        }
        return aux;
    }
}
