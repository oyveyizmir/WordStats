package wordstats;

import org.junit.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TempTest {
    @org.junit.Test
    public void test1() {
        Pattern outputPattern = Pattern.compile("(.+):(\\p{L}+\\d*)");
        Matcher m = outputPattern.matcher("[auf](er)stehen:EIG.NAC.AKK.SIN.FEM.ART");
        if (!m.find()) {
            System.out.println("NOT FOUND");
            return;
        }
        System.out.println(m.group(0));
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        String s = m.group(1).chars()
                .filter(c -> "[]()".indexOf(c) < 0)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        System.out.println(s);
    }
}
