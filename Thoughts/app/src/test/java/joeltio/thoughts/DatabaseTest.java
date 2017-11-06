package joeltio.thoughts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseTest {
    private DbAdapter dbAdapter;

    @Before
    public void setup() {
        dbAdapter = new DbAdapter(RuntimeEnvironment.application);
        dbAdapter.open();
        dbAdapter.reset();
    }

    @After
    public void tearDown() {
        dbAdapter.reset();
        dbAdapter.close();
    }

    private Thought createThought(String name) {
        return new Thought(name, "", new HashSet<String>(), new Date());
    }

    @Test
    public void insertThoughtRetainsThought() {
        Thought thought = createThought("a");
        dbAdapter.insertThought(thought);
        Mind mind  = dbAdapter.getMind();
        assertEquals(1, mind.getAllThoughts().size());
        assertTrue(mind.getAllThoughts().contains(thought));

        DbAdapter dbAdapter2 = new DbAdapter(RuntimeEnvironment.application);
        dbAdapter2.open();
        Mind mind2 = dbAdapter2.getMind();
        assertEquals(1, mind2.getAllThoughts().size());
        assertTrue(mind2.getAllThoughts().contains(thought));
        dbAdapter2.close();
    }
}
