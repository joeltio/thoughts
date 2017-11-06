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

    @Test
    public void getMindReturnsThoughtsWithId() {
        Thought thought1 = createThought("a");
        thought1.setId(1);
        Thought thought2 = createThought("b");
        thought2.setId(2);

        dbAdapter.insertThought(thought1);
        dbAdapter.insertThought(thought2);

        Mind mind = dbAdapter.getMind();
        for (Thought thought : mind.getAllThoughts()) {
            assertNotNull(thought.getId());
        }

        assertTrue(mind.getAllThoughts().contains(thought1));
        thought1.setId(2);
        assertFalse(mind.getAllThoughts().contains(thought1));
    }

    @Test
    public void insertThoughtSetsIdOfThought() {
        Thought thought = createThought("a");
        assertNull(thought.getId());
        dbAdapter.insertThought(thought);
        assertNotNull(thought.getId());
    }

    @Test
    public void deleteThoughtRemovesThought() {
        Thought thought1 = createThought("a");
        thought1.setId(1);
        Thought thought2 = createThought("b");
        thought2.setId(2);

        dbAdapter.insertThought(thought2);
        dbAdapter.insertThought(thought1);

        assertEquals(2, dbAdapter.getMind().getAllThoughts().size());
        dbAdapter.removeThought(thought1.getId());
        Mind mind = dbAdapter.getMind();
        assertEquals(1, mind.getAllThoughts().size());
        assertEquals(thought2, mind.getAllThoughts().get(0));

        DbAdapter dbAdapter2 = new DbAdapter(RuntimeEnvironment.application);
        dbAdapter2.open();
        mind = dbAdapter2.getMind();
        assertEquals(1, mind.getAllThoughts().size());
        assertEquals(thought2, mind.getAllThoughts().get(0));
        dbAdapter2.close();
    }
}
