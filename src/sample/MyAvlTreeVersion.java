package sample;

/** This class extends Avl and creates its own search function that returns the element search for
 * The AvlTree's search function returns a boolean so the purpose of this class is to create a search function that
 * returns the element being searched for **/
public class MyAvlTreeVersion<E> extends AVLTree<E> {

    public MyAvlTreeVersion(){

    }

    /** Create a BST with a specified comparator */
    public MyAvlTreeVersion(java.util.Comparator<E> c) {
        super(c);
    }

    /** Create an AVL tree from an array of objects */
    public MyAvlTreeVersion(E[] objects) {
        super(objects);
    }

    public E mySearch(E e) {
        E element = null;
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (c.compare(e, current.element) < 0) {
                current = current.left;
            }
            else if (c.compare(e, current.element) > 0) {
                current = current.right;
            }
            else // element matches current.element
               return element = current.element;
        }

        return null;
    }

}
