package study.jdk21.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaRegEx {
    public static int matchText(String regex,String text) {
        int matchCount = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matchCount++;
        }
        return matchCount;
    }
    public static void main(String[] args) {

    }
}
