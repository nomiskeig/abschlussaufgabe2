package bugs;

import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.FireBreakerGame;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.BoardParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowFieldTest {
    FireBreakerGame game;

    @BeforeEach
    public void setup() throws ParseException {
        BoardParser parser = new BoardParser("5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        Board board = parser.parseAndGetBoard();

        game = new FireBreakerGame(board);
    }

    @Test
    public void showField1() throws GameException {
        String result = game.showField(1, 0);
        assertEquals("+", result);
    }

    @Test
    public void invalidRows() {
        Throwable throwable = assertThrows(GameException.class, () -> {
            game.showField(-1, 0);
        });
        assertEquals(String.format(Errors.INVALID_ROW, "4", "-1"), throwable.getMessage());
        throwable = assertThrows(GameException.class, () -> {
            game.showField(5, 0);
        });
        assertEquals(String.format(Errors.INVALID_ROW, "4", "5"), throwable.getMessage());
    }

    @Test
    public void invalidColumns() {
        Throwable throwable = assertThrows(GameException.class, () -> {
            game.showField(0, -1);
        });
        assertEquals(String.format(Errors.INVALID_COLUMN, "4", "-1"), throwable.getMessage());
        throwable = assertThrows(GameException.class, () -> {
            game.showField(0, 5);
        });
        assertEquals(String.format(Errors.INVALID_COLUMN, "4", "5"), throwable.getMessage());
    }
}
