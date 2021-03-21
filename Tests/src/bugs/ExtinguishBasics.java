package bugs;


import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.Board;
import edu.kit.informatik.logic.Game;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtinguishBasics {
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
        assertEquals("+,1", Main.executeCommand(commands, "extinguish A0 1 2"));
        assertEquals("w,0", Main.executeCommand(commands, "extinguish A0 1 2"));
        assertEquals(String.format(Errors.ACTION_POINT_NEEDED_TO_EXTINGUISH, "A0"),
            Main.executeCommand(commands, "extinguish A0 2 1"));
    }


}