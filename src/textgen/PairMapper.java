//package textgen;
//
//import java.util.Iterator;
//import java.util.Spliterator;
//import java.util.Spliterators;
//import java.util.function.BiFunction;
//import java.util.stream.Stream;
//import java.util.stream.StreamSupport;
//
//import static java.util.Spliterator.IMMUTABLE;
//
//public class PairMapper {
//    public static <T, R> Stream<R> pairMap(Stream<T> sourceStream, BiFunction<T, T, R> mapper) {
//        PairIterator<T, R> pairIterator = new PairIterator<>(mapper, sourceStream.iterator());
//        Spliterator<R> pairSpliterator = Spliterators.spliteratorUnknownSize(pairIterator, IMMUTABLE);
//        return StreamSupport.stream(pairSpliterator, false);
//    }
//}
//
//class PairIterator<T, R> implements Iterator<R> {
//
//    private Iterator<T> sourceIterator;
//    private BiFunction<T, T, R> mapper;
//    private T prev;
//    private boolean hasPrev;
//
//    PairIterator(BiFunction<T, T, R> mapper, Iterator<T> sourceIterator) {
//        this.sourceIterator = sourceIterator;
//        this.mapper = mapper;
//        prev = null;
//        hasPrev = false;
//    }
//
//    @Override
//    public boolean hasNext() {
//        if (hasPrev) {
//            return sourceIterator.hasNext();
//        } else {
//            hasPrev = sourceIterator.hasNext();
//            return hasPrev && sourceIterator.hasNext();
//        }
//    }
//
//    @Override
//    public R next() {
//        T curr;
//        if (prev != null) {
//            curr = sourceIterator.next();
//        } else {
//            prev = sourceIterator.next();
//            curr = sourceIterator.next();
//        }
//        R res = mapper.apply(prev, curr);
//        prev = curr;
//        return res;
//    }
//}
