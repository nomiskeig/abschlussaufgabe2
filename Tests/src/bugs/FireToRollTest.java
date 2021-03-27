package bugs;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.FireBreakerGame;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.BoardParser;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireToRollTest {
    FireBreakerGame game;
    Board board;

    Command[] commands;

    @BeforeEach
    public void setup() throws ParseException {
        for (Player player : Player.values()) {
            player.reset();
        }
        BoardParser parser = new BoardParser("5,5,A,*,L,+,D,+,A0,+,D0,+,L,+,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        board = parser.parseAndGetBoard();
        game = new FireBreakerGame(board);
        commands = Command.getCommands(game);
        // this is a modified field

    }

    /*
    @Test
    public void basicTest() {
        assertEquals("x,*,x,+,x\n" + "+,x,+,x,+\n" + "x,+,x,*,x\n" + "+,x,x,x,+\n" + "x,+,x,+,x",
            Main.executeCommand(commands, "show-board"));
        assertEquals(Messages.OK, Main.executeCommand(commands, "fire-to-roll 4"));
        assertEquals("x,*,x,*,x\n" + "*,+,*,x,*\n" + "x,*,x,*,x\n" + "*,x,x,+,*\n" + "x,*,x,*,x",
            Main.executeCommand(commands, "show-board"));
        assertEquals(Messages.OK, Main.executeCommand(commands, "fire-to-roll 4"));
        assertEquals("x,*,x,*,x\n" + "*,*,*,+,*\n" + "x,*,+,*,x\n" + "*,+,x,*,*\n" + "x,*,x,*,x",
            Main.executeCommand(commands, "show-board"));
        assertEquals(Messages.LOSE, Main.executeCommand(commands, "fire-to-roll 5"));
        assertEquals("x,*,x,*,x\n" + "*,*,*,*,*\n" + "x,*,*,*,x\n" + "*,*,+,*,*\n" + "x,*,x,*,x",
            Main.executeCommand(commands, "show-board"));

    }

    @Test
    public void allDirections() {
        Main.executeCommand(commands, "show-board");
        assertEquals(Messages.OK, Main.executeCommand(commands, "fire-to-roll 1"));
        Main.executeCommand(commands, "show-board");
    }
    */
     
}
