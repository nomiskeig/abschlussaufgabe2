package bugs;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.FireBreakerGame;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.BoardParser;
import edu.kit.informatik.ui.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExtinguishSameField {
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


    @Test
    public void basicTest() {
        assertEquals("w,2", Main.executeCommand(commands, "extinguish A0,0,1"));
        assertEquals("+,1", Main.executeCommand(commands, "extinguish A0,1,2"));
        assertEquals(String.format(Errors.CANNOT_EXTINGUISH_SAME_FIELD_TWICE),
            Main.executeCommand(commands, "extinguish A0,1,2"));


    }


}