package joeltio.thoughts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class MindTest {
    @Test
    public void getAllThoughtsReturnsAllThoughts() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a");
        tags.add("b");

        Thought thought1 = new Thought("a", "", tags, new Date());
        Thought thought2 = new Thought("b", "", tags, new Date());
        Thought thought3 = new Thought("c", "", tags, new Date());

        Mind mind = new Mind();
        mind.addThought(thought1);
        mind.addThought(thought2);
        mind.addThought(thought3);

        ArrayList<Thought> thoughts = mind.getAllThoughts();
        assertTrue(thoughts.contains(thought1));
        assertTrue(thoughts.contains(thought2));
        assertTrue(thoughts.contains(thought3));
    }

    @Test
    public void getAllThoughtsReturnsCopyOfAllThoughts() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a");
        tags.add("b");

        Thought thought1 = new Thought("a", "", tags, new Date());
        Thought thought2 = new Thought("b", "", tags, new Date());
        Thought thought3 = new Thought("c", "", tags, new Date());

        Mind mind = new Mind();
        mind.addThought(thought1);
        mind.addThought(thought2);
        mind.addThought(thought3);

        ArrayList<Thought> thoughts = mind.getAllThoughts();
        thoughts.clear();

        assertTrue(mind.getAllThoughts().contains(thought1));
    }

    @Test
    public void containsReturnsTrueForContainedThoughts() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("a");
        tags.add("b");

        Thought thought1 = new Thought("a", "", tags, new Date());
        Thought thought2 = new Thought("a", "abc", tags, new Date());
        Thought thought3 = new Thought("b", "", tags, new Date());
        Thought thought4 = new Thought("c", "", tags, new Date());

        Mind mind = new Mind();
        mind.addThought(thought1);

        assertTrue(mind.contains(thought1));
        assertFalse(mind.contains(thought2));

        mind.addThought(thought3);

        assertTrue(mind.contains(thought3));
        assertFalse(mind.contains(thought4));
    }
}
