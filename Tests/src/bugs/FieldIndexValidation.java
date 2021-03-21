package bugs;


import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.Game;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.ui.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FieldIndexValidation {
    Game game;
    Board board;


    @BeforeEach
    public void setup() throws ParseException {
        board = Main.parseArguments("5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        game = new Game(board);
    }

    @Test()
    public void validateFieldIndexTests1() {
        Throwable throwable = Assertions.assertThrows(GameException.class, () -> {
            board.validateFieldIndex(-1, 2);
        });
        assertAll(
            () -> assertEquals("the row is invalid. The row has to be an integer between 0 and 4. You provided -1.",
                throwable.getMessage()));


    }

    @Test()
    public void validateFieldIndexTests2() {
        Throwable throwable = Assertions.assertThrows(GameException.class, () -> {
            board.validateFieldIndex(-1, 2);
        });
        assertAll(
            () -> assertEquals("the row is invalid. The row has to be an integer between 0 and 4. You provided -1.",
                throwable.getMessage()));


    }


    @Test()
    public void validateFieldIndexTests3() {
        Throwable throwable = Assertions.assertThrows(GameException.class, () -> {
            board.validateFieldIndex(0, 5);
        });
        assertAll(() -> assertEquals(
            "the column is invalid. The column has to be an integer between 0 and 4. You provided 5.",
            throwable.getMessage()));


    }

}
