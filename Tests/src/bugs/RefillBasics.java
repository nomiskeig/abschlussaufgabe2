package bugs;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.FireBreakerGame;
import edu.kit.informatik.ui.BoardParser;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefillBasics {
    FireBreakerGame game;
    Board board;

    Command[] commands;

    @BeforeEach
    public void setup() throws ParseException {
        BoardParser parser = new BoardParser("5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        board = parser.parseAndGetBoard();
        game = new FireBreakerGame(board);
        commands = Command.getCommands(game);


    }
    /*

    @Test
    public void basicTest() {
        assertEquals(String.format(Errors.ALREADY_FULL, "A0"), Main.executeCommand(commands, "refill A0"));
        assertEquals("w,2", Main.executeCommand(commands, "extinguish A0 1 0"));
        assertEquals("1", Main.executeCommand(commands, "refill A0"));
        assertEquals(String.format(Errors.ALREADY_FULL, "A0"), Main.executeCommand(commands, "refill A0"));
        assertEquals(String.format(Errors.ALREADY_FULL, "A0"), Main.executeCommand(commands, "refill A0"));
    }


    //TODO
    @Test
    public void noPossibilityToRefill() {

    }

    @Test
    public void invalidId() {
        assertEquals(String.format(Errors.NO_FIRE_ENGINE, "C0"), Main.executeCommand(commands, "refill C0"));
        assertEquals(String.format(Errors.NO_FIRE_ENGINE, "adsfa"), Main.executeCommand(commands, "refill adsfa"));
        assertEquals(String.format(Errors.NO_FIRE_ENGINE, "A1"), Main.executeCommand(commands, "refill A1"));

    }

    @Test
    public void invalidArguments() {
        assertEquals(Errors.INVALID_INPUT, Main.executeCommand(commands, "refill"));
        assertEquals(Errors.INVALID_INPUT, Main.executeCommand(commands, "refill "));
        assertEquals(Errors.INVALID_INPUT, Main.executeCommand(commands, "refill asdf "));
        assertEquals(Errors.INVALID_INPUT, Main.executeCommand(commands, "refill asdf sadf"));
        assertEquals(Errors.INVALID_INPUT, Main.executeCommand(commands, "refill "));

    }

    */
}