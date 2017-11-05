package joeltio.thoughts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class MindTest {
    private Thought createThought(String name) {
        return new Thought(name, "", new ArrayList<String>(), new Date());
    }

    @Test
    public void getAllThoughtsReturnsAllThoughts() {
        Mind mind = new Mind();
        Thought thought1 = createThought("a");
        Thought thought2 = createThought("b");

        mind.addThought(thought1);
        mind.addThought(thought2);

        ArrayList<Thought> thoughts = mind.getAllThoughts();
        assertTrue(thoughts.contains(thought1));
        assertTrue(thoughts.contains(thought2));
    }

    @Test
    public void containsReturnsTrueForContainedThoughts() {
        Mind mind = new Mind();

        Thought thought1 = new Thought("a", "", new ArrayList<String>(), new Date());
        Thought thought2 = new Thought("a", "abc", new ArrayList<String>(), new Date());
        mind.addThought(thought1);

        assertTrue(mind.contains(thought1));
        assertFalse(mind.contains(thought2));

        Thought thought3 = new Thought("b", "", new ArrayList<String>(), new Date());
        Thought thought4 = new Thought("c", "", new ArrayList<String>(), new Date());
        mind.addThought(thought3);

        assertTrue(mind.contains(thought3));
        assertFalse(mind.contains(thought4));
    }
}
