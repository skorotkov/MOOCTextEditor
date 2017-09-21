/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

    private static final int LONG_LIST_LENGTH =10;

    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<String>();
        shortList.add("A");
        shortList.add("B");
        emptyList = new MyLinkedList<Integer>();
        longerList = new MyLinkedList<Integer>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++)
        {
            longerList.add(i);
        }
        list1 = new MyLinkedList<Integer>();
        list1.add(65);
        list1.add(21);
        list1.add(42);

    }


    /** Test if the get method is working correctly.
     */
    /*You should not need to add much to this method.
     * We provide it as an example of a thorough test. */
    @Test
    public void testGet()
    {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Check first", "A", shortList.get(0));
        assertEquals("Check second", "B", shortList.get(1));

        try {
            shortList.get(-1);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) {

        }
        // test longer list contents
        for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
            assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        }
        catch (IndexOutOfBoundsException e) {
        }

    }


    /** Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.  */
    @Test
    public void testRemove()
    {
        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
        assertEquals("Remove: check size is correct ", 2, list1.size());

        try {
            assertEquals("Remove: empty list", null, emptyList.remove(0));
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            assertEquals("Remove: short list, <0", null, shortList.remove(-1));
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            assertEquals("Remove: short list, >size-1", null, shortList.remove(shortList.size()));
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        a = list1.remove(list1.size() - 1);
        assertEquals("Remove: last elem ", 42, a);
        assertEquals("Remove: last elem, size ", 1, list1.size());

        a = list1.remove(list1.size() - 1);
        assertEquals("Remove: last remain elem ", 21, a);
        assertEquals("Remove: last remain elem, size ", 0, list1.size());

        list1.add(77);
        assertEquals("Remove: list is usable after remove all elems ", (Integer)77, list1.get(0));
        assertEquals("Remove: list is usable after remove all elems, size ", 1, list1.size());
    }

    /** Test adding an element into the end of the list, specifically
     *  public boolean add(E element)
     * */
    @Test
    public void testAddEnd()
    {
        try {
            longerList.add(null);
            fail("Add: null element");
        } catch (NullPointerException ex) {

        }

        emptyList.add(3);
        assertEquals("Add: empty list: check size is correct", 1, emptyList.size());
        assertEquals("Add: empty list: check added elem", Integer.valueOf(3), emptyList.get(0));
        assertEquals("Add: empty list: check added elem directly", Integer.valueOf(3), emptyList.head.next.data);

        shortList.add("5");
        assertEquals("Add: short list: check size is correct", 3, shortList.size());
        assertEquals("Add: short list: check added elem", "5", shortList.get(shortList.size()-1));
        assertEquals("Add: short list: check added elem directly", "5", shortList.head.next.next.next.data);
    }


    /** Test the size of the list */
    @Test
    public void testSize()
    {
        assertEquals("Size: short list", 2, shortList.size());
        assertEquals("Size: empty list", 0, emptyList.size());
    }



    /** Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     * */
    @Test
    public void testAddAtIndex()
    {
        try {
            shortList.add(-1, "F");
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            shortList.add(shortList.size()+1, "F");
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            shortList.add(shortList.size(), null);
            fail("Null element");
        } catch (NullPointerException ex) {

        }

        shortList.add(2, "F");
        assertEquals("AddAt: short list, add as last elem", "F", shortList.get(2));
        assertEquals("AddAt: short list, add as last elem, size", 3, shortList.size());

        shortList.add(2, "E");
        assertEquals("AddAt: short list, add as elem before last", "E", shortList.get(2));
        assertEquals("AddAt: short list, add as elem before last, size", 4, shortList.size());

        shortList.add(0, "G");
        assertEquals("AddAt: short list, add as first elem", "G", shortList.get(0));
        assertEquals("AddAt: short list, add as first elem", "A", shortList.get(1));
        assertEquals("AddAt: short list, add as first elem, size", 5, shortList.size());

        emptyList.add(0, 1);
        assertEquals("AddAt: empty list, add as first elem", (Integer)1, emptyList.get(0));
        assertEquals("AddAt: emptyshort list, add as first elem, size", 1, emptyList.size());
    }

    /** Test setting an element in the list */
    @Test
    public void testSet()
    {
        try {
            shortList.set(-1, "F");
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            shortList.set(shortList.size(), "F");
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            shortList.set(0, null);
            fail("Null element");
        } catch (NullPointerException ex) {

        }

        try {
            emptyList.set(0, 2);
            fail("Out of bound");
        } catch (IndexOutOfBoundsException ex) {

        }

        shortList.set(1, "F");
        assertEquals("Set: short list, set last elem", "F", shortList.get(1));
        assertEquals("Set: short list, add as last elem, size", 2, shortList.size());
    }


    // TODO: Optionally add more test methods.

}
