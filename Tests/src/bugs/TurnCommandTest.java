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

public class TurnCommandTest {
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
        assertEquals("B", Main.executeCommand(commands, "turn"));
        assertEquals("C", Main.executeCommand(commands, "turn"));
        assertEquals("D", Main.executeCommand(commands, "turn"));
        assertEquals("B", Main.executeCommand(commands, "turn"));
        //assertEquals(Errors.FIRE_TO_ROLL_NEEDED, Main.executeCommand(commands, "turn"));
        assertEquals("C", Main.executeCommand(commands, "turn"));
        assertEquals("D", Main.executeCommand(commands, "turn"));
        assertEquals("A", Main.executeCommand(commands, "turn"));
        assertEquals("C", Main.executeCommand(commands, "turn"));
        assertEquals("D", Main.executeCommand(commands, "turn"));
        assertEquals("A", Main.executeCommand(commands, "turn"));
        assertEquals("B", Main.executeCommand(commands, "turn"));
        assertEquals("D", Main.executeCommand(commands, "turn"));
    }

}