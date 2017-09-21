package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
    public LLNode<E> head;
    public LLNode<E> tail;
    private int size;

    /** Create a new empty LinkedList */
    public MyLinkedList() {
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /**
     * Appends an element to the end of the list
     * @param element The element to add
     */
    public boolean add(E element )
    {
        if (element == null)
            throw new NullPointerException();

        add(size, element);
//        LLNode<E> e = new LLNode<>(element);
//        e.prev = tail.prev;
//        e.next = tail;
//        tail.prev.next = e;
//        tail.prev = e;
//        size++;

        return true;
    }

    /** Get the element at position index
     * @throws IndexOutOfBoundsException if the index is out of bounds. */
    public E get(int index)
    {
        if (index > size - 1 || index < 0)
            throw new IndexOutOfBoundsException();

        return getNode(index).data;
    }

    /**
     * Add an element to the list at the specified index
     * @param index where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element )
    {
        if (element == null)
            throw new NullPointerException();

        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();

        LLNode<E> currNode = getNode(index);

        LLNode<E> e = new LLNode<>(element);
        e.next = currNode;
        e.prev = currNode.prev;

        currNode.prev.next = e;
        currNode.prev = e;
        size++;
    }


    /** Return the size of the list */
    public int size()
    {
        return size;
    }

    /** Remove a node at the specified index and return its data element.
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     *
     */
    public E remove(int index)
    {
        if (index > size - 1 || index < 0)
            throw new IndexOutOfBoundsException();

        LLNode<E> e = getNode(index);
        e.prev.next = e.next;
        e.next.prev = e.prev;
        size--;
        return e.data;
    }

    /**
     * Set an index position in the list to a new element
     * @param index The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element)
    {
        if (element == null)
            throw new NullPointerException();

        if (index > size - 1 || index < 0)
            throw new IndexOutOfBoundsException();

        LLNode<E> e = getNode(index);
        E data = e.data;
        e.data = element;
        return data;
    }

    private LLNode<E> getNode(int index)
    {
        LLNode<E> currNode = head;
        for (int i = 0; i <= index; i++)
            currNode = currNode.next;

        return currNode;
    }

}

class LLNode<E> 
{
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    LLNode(E e)
    {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
