
package proyectocalcu;

/**
 * The NodoPila class represents a node in a stack used for storing NodoArbol objects.
 * It stores a NodoArbol as its data and has a reference to the next node in the stack.
 */

public class NodoPila {
    NodoArbol dato;
    NodoPila siguiente;
    
    /**
     * Constructs a new NodoPila with the given NodoArbol data.
     *
     * @param x The NodoArbol data to be stored in the node.
     */
    
    public NodoPila(NodoArbol x){
    
        dato = x;
        siguiente = null;
    }
    
}
