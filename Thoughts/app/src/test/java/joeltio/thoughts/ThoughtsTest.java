package joeltio.thoughts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ThoughtsTest {
    @Test
    public void thoughtsKeepsAttributes() {
        String thoughtName = "name";
        String thoughtBody = "body";
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        Date date = new Date();
        Thought thought = new Thought(thoughtName, thoughtBody, tags, date);

        assertEquals(thoughtName, thought.getName());
        assertEquals(thoughtBody, thought.getBody());
        assertEquals(tags, thought.getTags());
        assertEquals(date, thought.getCreationDate());
    }

    private Thought createThought() {
        Date date = new Date();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        return new Thought("name", "body", tags, date);
    }

    @Test
    public void equalThoughtsEquate() {
        Thought thought1 = createThought();
        Thought thought2 = createThought();

        assertTrue(thought1.equals(thought2));
    }
}
