/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResponseCommands;

import Model.Event;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Maximiliano Herrera
 */
public class ResponseCommandBuilderTest {

    public ResponseCommandBuilderTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of Build method, of class ResponseCommandBuilder.
     */
    @Test
    public void testBuildAnErrorCommand() {
        ResponseCommandBuilder instance = new ResponseCommandBuilder();
        ResponseCommand expResult = new ErrorCommand("an error","");
        ResponseCommand result = instance.Build("Error;an error");
        assertEquals(expResult.getClass(), result.getClass());

        result = instance.Build("");
        assertEquals(expResult.getClass(), result.getClass());

        result = instance.Build(null);
        assertEquals(expResult.getClass(), result.getClass());

        ErrorCommand errorCommand = (ErrorCommand) instance.Build("Error;an error");
        assertEquals(expResult.getClass(), errorCommand.getClass());
        assertEquals("an error", errorCommand.getErrorMessage());
    }

    @Test
    public void testBuildATerminateCommand() {
        ResponseCommandBuilder instance = new ResponseCommandBuilder();
        ResponseCommand expResult = new TerminateCommand("");
        ResponseCommand result = instance.Build("terminate");
        assertEquals(expResult.getClass(), result.getClass());

        result = instance.Build("tERMINAte");
        assertEquals(expResult.getClass(), result.getClass());
    }

    @Test
    public void testBuildASuccessCommand() {
        ResponseCommandBuilder instance = new ResponseCommandBuilder();
        ResponseCommand expResult = new SuccessCommand(null, new ArrayList<String>(),"");
        SuccessCommand result = (SuccessCommand) instance.Build("2021-10-01;12:00,Food Market IFSC Square;22:00,Westlife Concert O2 Arena");
        assertEquals(expResult.getClass(), result.getClass());

        ArrayList<Event> events = result.getEvents();

        assertEquals(2, events.size());
        Event event1 = events.get(0);

        assertEquals("2021-10-01", event1.getDate().toString());
        assertEquals("12:00", event1.getTime().toString());
        assertEquals("Food Market IFSC Square", event1.getEventName());

        Event event2 = events.get(1);

        assertEquals("2021-10-01", event2.getDate().toString());
        assertEquals("22:00", event2.getTime().toString());
        assertEquals("Westlife Concert O2 Arena", event2.getEventName());

        result = (SuccessCommand) instance.Build("2021-10-01");
        assertEquals(expResult.getClass(), result.getClass());

        assertEquals(0, result.getEvents().size());

        result = (SuccessCommand) instance.Build("2021-10-01;error_time,Food Market IFSC Square;22:00,Westlife Concert O2 Arena");
        events = result.getEvents();
        assertEquals(1, events.size());
        Event event = events.get(0);

        assertEquals("2021-10-01", event.getDate().toString());
        assertEquals("22:00", event.getTime().toString());
        assertEquals("Westlife Concert O2 Arena", event.getEventName());
    }
}
