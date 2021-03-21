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

public class MoveTest {
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
        assertEquals(Messages.OK, Main.executeCommand(commands, "move A0 2 2"));
        assertEquals("d", Main.executeCommand(commands, "show-field 1 1"));
        assertEquals("d,A0", Main.executeCommand(commands, "show-field 2 2"));
        assertEquals("w,1", Main.executeCommand(commands, "extinguish A0 1 2"));
        assertEquals("A,6\nA0,2,1,2,2", Main.executeCommand(commands, "show-player"));


    }

    @Test
    public void twoWays() {
        // i changed the board so that extinguish is not needed
        //assertEquals("+,2", Main.executeCommand(commands, "extinguish A0 1 2"));
        //assertEquals("+,1", Main.executeCommand(commands, "extinguish A0 2 1"));
        assertEquals("OK", Main.executeCommand(commands, "move A0 2 2"));
        assertEquals("d,A0", Main.executeCommand(commands, "show-field 2 2"));

    }


    @Test
    public void invalidIndex() {
        assertEquals(String.format(Errors.INVALID_ROW, 4, -1), Main.executeCommand(commands, "move A0 -1 1"));

    }

    @Test
    public void hasToPassNonForestField() {

    }

    @Test
    public void burningGoal() {
        assertEquals(Errors.NO_ENGINE_ON_LIGHT, Main.executeCommand(commands, "move A0 1 2"));
        assertEquals(Errors.NO_ENGINE_ON_STRONG, Main.executeCommand(commands, "move A0 0 1"));

    }


    @Test
    public void invalidGoals() {
        assertEquals(String.format(Errors.INVALID_PLACEMENT_FIRE_STATION),
            Main.executeCommand(commands, "move A0 0 0"));
        assertEquals(Errors.INVALID_PLACEMENT_POND, Main.executeCommand(commands, "move A0 0 2"));
    }

    @Test
    public void sameField() {
        assertEquals(Errors.ENGINE_CAN_NOT_STAY_ON_SAME_FIELD, Main.executeCommand(commands, "move A0 1 1"));
    }


}