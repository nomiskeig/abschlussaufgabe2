package bugs;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.ui.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.FireBreakerGame;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.ui.BoardParser;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class showPlayerTest {
    FireBreakerGame game;
    Board board;

    Command[] commands;

    @BeforeEach
    public void setup() throws ParseException {
        for (Player player : Player.values()) {
            player.reset();
        }
        BoardParser parser = new BoardParser("5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        board = parser.parseAndGetBoard();
        game = new FireBreakerGame(board);
        commands = Command.getCommands(game);


    }
    /*

    @Test
    public void basicTest() {
        String result = "A,5\nA0,3,3,1,1";
        assertEquals(result, Main.executeCommand(commands, "show-player"));
        assertEquals(Messages.OK, Main.executeCommand(commands, "move A0 2 2"));
        assertEquals("A,5\nA0,3,2,2,2", Main.executeCommand(commands, "show-player"));
    }
*/

}