package functionality;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.Game;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.resources.Messages;
import edu.kit.informatik.ui.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Move {
    Game game;
    Board board;

    Command[] commands;

    @BeforeEach
    public void setup() throws ParseException {
        board = Main.parseArguments("5,5,A,+,L,+,D,+,A0,*,D0,+,L,*,d,*,L,+,C0,d,B0,+,C,+,L,+,B");
        game = new Game(board);
        commands = Command.getCommands(game);

    }


    @Test
    public void basicTest() {
        assertEquals("w,2", Main.executeCommand(commands, "extinguish A0 0 1"));
        assertEquals(Messages.OK, Main.executeCommand(commands, "move A0 0 1"));
        Main.executeCommand(commands, "show-field 0 1");
        Main.executeCommand(commands, "show-field 1 1");

    }


    @Test
    public void invalidIndex() {
        assertEquals(String.format(Errors.INVALID_ROW, 4, -1), Main.executeCommand(commands, "move A0 -1 1"));

    }

    @Test
    public void invalidGoals() {
        assertEquals(String.format(Errors.INVALID_PLACEMENT_FIRE_STATION),
            Main.executeCommand(commands, "move A0 0 0"));
    }

    @Test
    public void sameField() {
        assertEquals(String.format(Errors.ENGINE_CAN_NOT_STAY_ON_SAME_FIELD),
            Main.executeCommand(commands, "move A0 1 1"));
    }


}