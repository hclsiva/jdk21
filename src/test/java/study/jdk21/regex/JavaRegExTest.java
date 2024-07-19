package study.jdk21.regex;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JavaRegExTest {

    @Test
    public void verifyJunitWorks(){
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        assertTrue(numbers.stream()
                .mapToInt(Integer::intValue)
                .sum() > 5, "Sum should be greater than 5");
    }
    @Test
    public void verifyTextMatchesRegEx(){
        String text = "ABCD";
        String regEx = "^[A-Za-z]\\w{2,23}[^_]$";
        assertTrue(JavaRegEx.matchText(regEx,text) > 0,"No match found for the given regex");
    }
}
