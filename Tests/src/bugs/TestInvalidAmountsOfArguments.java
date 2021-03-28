package bugs;

import edu.kit.informatik.ui.BoardParser;
import edu.kit.informatik.ui.ParseException;
import org.junit.jupiter.api.Test;

public class TestInvalidAmountsOfArguments {
    @Test
    public void test() {
        BoardParser parser = new BoardParser("5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B,L");
        try {
            parser.parseAndGetBoard();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test2() {
        BoardParser parser = new BoardParser("A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B,L");
        try {
            parser.parseAndGetBoard();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}