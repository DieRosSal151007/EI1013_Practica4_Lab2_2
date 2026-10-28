import java.util.Collection;
import java.util.NoSuchElementException;

public class LinkedListWithCursor<T> {
    //Importante: No cambiar ni los nombres ni la visibilidad
    Node<T> left;
    Node<T> right;
    int sizeLeft;
    int sizeRight;

    public LinkedListWithCursor() {
        left = null;
        right = null;
        sizeLeft = 0;
        sizeRight = 0;
    }


    public LinkedListWithCursor(Collection<T> col, int index) {
        // TODO Ejercicio 1
        if (col == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > col.size()) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> lastRight = null;
        int currentIndex  = 0;

        for (T elem : col) {
            if (currentIndex < index) {
                this.left = new Node<>(elem, this.left);
                this.sizeLeft++;
            } else {
                Node<T> newNode = new Node<>(elem, null);
                if (this.right == null) {
                    this.right = newNode;
                } else {
                    lastRight.next = newNode;
                }
                lastRight = newNode;
                this.sizeRight++;
            }
            currentIndex++;
        }
    }


    public int index() {
        // TODO Ejercicio 2
        return this.sizeLeft;
    }


    public int size() {
        // TODO Ejercicio 3
        return this.sizeLeft + this.sizeRight;
    }


    public T current() {
        // TODO Ejercicio 4
        if (this.right == null) {
            return null;
        }
        return this.right.data;
    }


    public void add(T item) {
        // TODO Ejercicio 5
        if (item == null) {
            throw new NullPointerException();
        }
        this.left = new Node<>(item, this.left);
        sizeLeft++;
    }


    public T remove() {
        // TODO Ejercicio 6
        if (this.right == null) {
            throw new NoSuchElementException();
        }
        T borrado = this.right.data;
        this.right = this.right.next;
        sizeRight--;
        return borrado;
    }


    public T next() {
        // TODO Ejercicio 7
        if (this.right == null) {
            throw new NoSuchElementException();
        }
        Node<T> aux = this.right;
        this.right = this.right.next;
        aux.next = this.left;
        this.left = aux;
        this.sizeLeft++;
        this.sizeRight--;
        return aux.data;
    }


    public T previous() {
        // TODO Ejercicio 8
        if (this.left == null) {
            throw new NoSuchElementException();
        }
        Node<T> aux = this.left;
        this.left = this.left.next;
        aux.next = this.right;
        this.right = aux;
        this.sizeLeft--;
        this.sizeRight++;
        return aux.data;
    }


    public T moveTo(int index) {
        // TODO Ejercicio 9
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        while (this.sizeLeft < index) {
            this.next();
        }

        while (this.sizeLeft > index) {
            this.previous();
        }

        return (this.right != null) ? this.right.data : null;
    }

    /**
     * Devuelve la lista con el formato [izquierda][*derecha], por ejemplo
     * "[l, u][*n, a]", "[][*s, o]", "[s, o][*]" o "[][*]".
     *  NO LO MODIFIQUES.
     */
    @Override
    public String toString() {
        // La cadena izquierda está en orden inverso: se copia a un array al revés
        Object[] leftItems = new Object[sizeLeft];
        Node<T> n = left;
        for (int i = sizeLeft - 1; i >= 0 && n != null; i--) {
            leftItems[i] = n.data;
            n = n.next;
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < leftItems.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(leftItems[i]);
        }
        sb.append("][*");
        n = right;
        boolean first = true;
        while (n != null) {
            if (!first) sb.append(", ");
            sb.append(n.data);
            first = false;
            n = n.next;
        }
        return sb.append("]").toString();
    }
}
