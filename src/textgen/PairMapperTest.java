package textgen;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PairMapperTest {
    @Test
    public void test1() {
        int[] array = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] result = PairMapper.pairMap(Arrays.stream(array).boxed(), (a, b) -> a + b).mapToInt(i -> i).toArray();
        int[] expectedResult = new int[] {3, 5, 7, 9, 11, 13, 15, 17, 19};
        Assert.assertEquals(array.length - 1, result.length);
        Assert.assertArrayEquals(expectedResult, result);
    }
}
