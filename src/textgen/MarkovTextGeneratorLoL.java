package textgen;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.IMMUTABLE;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator)
    {
        wordList = null;//new LinkedList<ListNode>();
        starter = "";
        rnGenerator = generator;
    }


    class Pair {
        String currWord;
        String nextWord;
        Pair(String currWord, String nextWord) {
            this.currWord = currWord;
            this.nextWord = nextWord;
        }
    }

    /** Train the generator by adding the sourceText */
    @Override
    public void train(String sourceText)
    {
        if (wordList != null)
            return;
        ArrayList<String> words = new ArrayList<>(Arrays.asList(sourceText.split("\\s+")));
        starter = words.get(0);
        words.add(starter);
        wordList = PairMapper.pairMap(words.stream(), Pair::new)
            .collect(Collectors.groupingBy(p -> p.currWord))
            .entrySet()
            .stream()
            .map(e -> {
                String currWord = e.getKey();
                ListNode node = new ListNode(currWord);
                e.getValue().forEach(p -> node.addNextWord(p.nextWord));
                return node;
            })
            .collect(Collectors.toList());
    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        if (wordList == null)
            return "";
        return IntStream.range(0, numWords)
                .mapToObj(i -> {
                    Optional<String> nextWord = wordList.stream().filter(wl -> wl.getWord().equals(starter)).findFirst().map(w -> w.getRandomNextWord(rnGenerator));
                    starter = nextWord.get();
                    return starter;
                })
                .collect(Collectors.joining(" "));
    }


    // Can be helpful for debugging
    @Override
    public String toString()
    {
        String toReturn = "";
        for (ListNode n : wordList)
        {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /** Retrain the generator from scratch on the source text */
    @Override
    public void retrain(String sourceText)
    {
        wordList = null;
        starter = "";
        train(sourceText);
    }

    // TODO: Add any private helper methods you need here.


    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     * @param args
     */
    public static void main(String[] args)
    {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        gen.generateText(20);
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, "+
                "You say stop, and I say go, go, go, "+
                "Oh no. You say goodbye and I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello. "+
                "I say high, you say low, "+
                "You say why, and I say I don't know. "+
                "Oh no. "+
                "You say goodbye and I say hello, hello, hello. "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello. "+
                "Why, why, why, why, why, why, "+
                "Do you say goodbye. "+
                "Oh no. "+
                "You say goodbye and I say hello, hello, hello. "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello. "+
                "You say yes, I say no, "+
                "You say stop and I say go, go, go. "+
                "Oh, oh no. "+
                "You say goodbye and I say hello, hello, hello. "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello, hello, hello, "+
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word)
    {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord()
    {
        return word;
    }

    public void addNextWord(String nextWord)
    {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator)
    {
        // TODO: Implement this method
        // The random number generator should be passed from
        // the MarkovTextGeneratorLoL class
        return nextWords.get(generator.nextInt(nextWords.size()));
    }

    public String toString()
    {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}

class PairMapper {
    static <T, R> Stream<R> pairMap(Stream<T> sourceStream, BiFunction<T, T, R> mapper) {
        PairIterator<T, R> pairIterator = new PairIterator<>(mapper, sourceStream.iterator());
        Spliterator<R> pairSpliterator = Spliterators.spliteratorUnknownSize(pairIterator, IMMUTABLE);
        return StreamSupport.stream(pairSpliterator, false);
    }
}

class PairIterator<T, R> implements Iterator<R> {

    private Iterator<T> sourceIterator;
    private BiFunction<T, T, R> mapper;
    private T prev;
    private boolean hasPrev;

    PairIterator(BiFunction<T, T, R> mapper, Iterator<T> sourceIterator) {
        this.sourceIterator = sourceIterator;
        this.mapper = mapper;
        prev = null;
        hasPrev = false;
    }

    @Override
    public boolean hasNext() {
        if (hasPrev) {
            return sourceIterator.hasNext();
        } else {
            hasPrev = sourceIterator.hasNext();
            return hasPrev && sourceIterator.hasNext();
        }
    }

    @Override
    public R next() {
        T curr;
        if (prev != null) {
            curr = sourceIterator.next();
        } else {
            prev = sourceIterator.next();
            curr = sourceIterator.next();
        }
        R res = mapper.apply(prev, curr);
        prev = curr;
        return res;
    }
}

