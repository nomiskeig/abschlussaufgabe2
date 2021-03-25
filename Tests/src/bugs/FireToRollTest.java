package bugs;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.Game;
import edu.kit.informatik.logic.Player;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.resources.Messages;
import edu.kit.informatik.ui.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FireToRollTest {
    Game game;
    Board board;

    Command[] commands;

    @BeforeEach
    public void setup() throws ParseException {
        for (Player player : Player.values()) {
            player.setReputationPoints(5);
        }
        // this is a modified field
        board = Main.parseArguments("5,5,A,*,L,+,D,+,A0,+,D0,+,L,+,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        game = new Game(board);
        commands = Command.getCommands(game);

    }


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
}
