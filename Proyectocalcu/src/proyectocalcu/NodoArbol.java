/**
 * The NodoArbol class represents a node in a binary tree used for mathematical expressions.
 * It stores an object as its data, and has left and right child nodes.
 */
package proyectocalcu;

public class NodoArbol {
    Object dato;
    NodoArbol izquierdo;
    NodoArbol derecho;
    
     /**
     * Constructs a new NodoArbol with the given data.
     *
     * @param x The data to be stored in the node.
     */
    
    public NodoArbol(Object x){
        dato = x;
        izquierdo = null;
        derecho = null;
    
    }
}
