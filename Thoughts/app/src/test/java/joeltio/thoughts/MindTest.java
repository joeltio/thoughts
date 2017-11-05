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

    @Test
    public void getAllThoughtsReturnsCopyOfAllThoughts() {
        Mind mind = new Mind();

        Thought thought1 = createThought("a");
        mind.addThought(thought1);
        mind.getAllThoughts().clear();
        assertTrue(mind.getAllThoughts().contains(thought1));
    }

    @Test
    public void addThoughtCopiesThoughts() {
        Mind mind = new Mind();

        String thoughtName = "";
        Thought thought = createThought(thoughtName);
        mind.addThought(thought);

        assertEquals(thoughtName, mind.getAllThoughts().get(0).getName());
        thought.setName("abc");
        assertEquals(thoughtName, mind.getAllThoughts().get(0).getName());
    }

    @Test
    public void filterByTagReturnsCorrectMatchesForMatchAll() {
        Mind mind = new Mind();

        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");

        ArrayList<String> tags12 = new ArrayList<>();
        tags12.add("tag1");
        tags12.add("tag2");

        Thought thought1 = new Thought("a", "", tags1, new Date());
        Thought thought2 = new Thought("a", "", tags12, new Date());
        mind.addThought(thought1);
        mind.addThought(thought2);

        ArrayList<Thought> thoughts = mind.filterByTags(tags12, true);
        assertFalse(thoughts.contains(thought1));
        assertTrue(thoughts.contains(thought2));

        thoughts = mind.filterByTags(tags1, true);
        assertTrue(thoughts.contains(thought1));
        assertFalse(thoughts.contains(thought2));
    }

    @Test
    public void filterByTagReturnsCorrectMatches() {
        Mind mind = new Mind();

        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");

        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag2");

        ArrayList<String> tags12 = new ArrayList<>();
        tags12.add("tag1");
        tags12.add("tag2");

        Thought thought1 = new Thought("a", "", tags1, new Date());
        Thought thought2 = new Thought("a", "", tags12, new Date());
        mind.addThought(thought1);
        mind.addThought(thought2);

        ArrayList<Thought> thoughts = mind.filterByTags(tags1, false);
        assertTrue(thoughts.contains(thought1));
        assertTrue(thoughts.contains(thought2));

        thoughts = mind.filterByTags(tags12, false);
        assertTrue(thoughts.contains(thought1));
        assertTrue(thoughts.contains(thought2));

        thoughts = mind.filterByTags(tags2, false);
        assertFalse(thoughts.contains(thought1));
        assertTrue(thoughts.contains(thought2));
    }
}
