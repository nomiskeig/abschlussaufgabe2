package bugs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringOrderingTest {
    @Test
    public void baseTest() {
        String s1 = "A0";
        String s2 = "A1";
        String s3 = "A3";
        String s4 = "B0";
        String s5 = "B5";

        ArrayList<String> list = new ArrayList();
        list.add(s5);
        list.add(s4);
        list.add(s3);
        list.add(s2);
        list.add(s1);
        assertEquals("[B5, B0, A3, A1, A0]", list.toString());

        Collections.sort(list);
        assertEquals("[A0, A1, A3, B0, B5]", list.toString());
    }


}
