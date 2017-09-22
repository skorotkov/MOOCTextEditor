package spelling;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DictionaryHashSetMatchCaseTest {
    private String dictFile = "data/words.small.txt";

    Dictionary emptyDict;
    Dictionary smallDict;
    Dictionary largeDict;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        emptyDict = new DictionaryHashSetMatchCase();
        smallDict = new DictionaryHashSetMatchCase();
        largeDict = new DictionaryHashSetMatchCase();

        smallDict.addWord("hello");
        smallDict.addWord("Cristine");

        DictionaryLoader.loadDictionary(largeDict, dictFile);
    }

    @Test
    public void testIsWord()
    {
        assertEquals("Testing isWord on empty: Hello", false, emptyDict.isWord("Hello"));
        assertFalse("Testing isWord on empty: empty string", emptyDict.isWord(""));

        assertTrue("Testing isWord on small: Hello", smallDict.isWord("Hello"));
        assertTrue("Testing isWord on small: hello", smallDict.isWord("hello"));
        assertTrue("Testing isWord on small: HELLO", smallDict.isWord("HELLO"));
        assertFalse("Testing isWord on small: hEllo", smallDict.isWord("hEllo"));
        assertFalse("Testing isWord on small: hELLO", smallDict.isWord("hELLO"));

        assertFalse("Testing isWord on small: empty string", smallDict.isWord(""));
        assertFalse("Testing isWord on small: no", smallDict.isWord("no"));
        assertFalse("Testing isWord on small: no", smallDict.isWord("No"));
        assertFalse("Testing isWord on small: no", smallDict.isWord("nO"));
        assertFalse("Testing isWord on small: no", smallDict.isWord("NO"));

        assertTrue("Testing isWord on small: Cristine", smallDict.isWord("Cristine"));
        assertTrue("Testing isWord on small: CRISTINE", smallDict.isWord("CRISTINE"));
        assertFalse("Testing isWord on small: cristine", smallDict.isWord("cristine"));
        assertFalse("Testing isWord on small: cRISTINE", smallDict.isWord("cRISTINE"));
        assertFalse("Testing isWord on small: CristIne", smallDict.isWord("CristIne"));
    }
}
