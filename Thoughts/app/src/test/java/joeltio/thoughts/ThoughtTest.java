package joeltio.thoughts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class ThoughtTest {
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

    @Test
    public void thoughtsSetNewValues() {
        Thought thought = new Thought("", "", new ArrayList<String>(), new Date());

        String name = "name";
        assertNotEquals(name, thought.getName());
        thought.setName(name);
        assertEquals(name, thought.getName());

        assertNotEquals("body", thought.getBody());
        thought.setBody("body");
        assertEquals("body", thought.getBody());

        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        assertNotEquals(tags, thought.getTags());
        thought.setTags(tags);
        assertEquals(tags, thought.getTags());
    }

    private Thought createThought() {
        Date date = new Date();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        return new Thought("name", "body", tags, date);
    }

    @Test
    public void getTagsReturnsCopyOfTags() {
        Thought thought = createThought();
        ArrayList<String> tags = thought.getTags();

        assertFalse(thought.getTags().isEmpty());
        tags.clear();
        assertFalse(thought.getTags().isEmpty());
    }

    @Test
    public void equalThoughtsEquate() {
        Thought thought1 = createThought();
        Thought thought2 = createThought();

        assertTrue(thought1.equals(thought2));
    }

    @Test
    public void thoughtCopiesWhenSettingTagInConstructor() {
        ArrayList<String> tags = new ArrayList<>();
        Thought thought = new Thought("", "", tags, new Date());
        assertTrue(thought.getTags().isEmpty());
        tags.add("tag1");
        assertTrue(thought.getTags().isEmpty());
    }

    @Test
    public void thoughtCopiesWhenSettingTagInSetTagMethod() {
        Thought thought = new Thought("", "", new ArrayList<String>(), new Date());
        ArrayList<String> tags = new ArrayList<>();
        thought.setTags(tags);
        assertTrue(thought.getTags().isEmpty());
        tags.add("tag1");
        assertTrue(thought.getTags().isEmpty());
    }

    @Test
    public void thoughtDeepCopiesForCopyConstructor() {
        String thoughtName = "";
        String thoughtBody = "";
        Date thoughtCreationDate = new Date(0);
        Thought thought = new Thought(thoughtName, thoughtBody, new ArrayList<String>(), thoughtCreationDate);
        Thought thoughtCopy = new Thought(thought);

        thoughtCopy.setName("abc");
        assertEquals("", thought.getName());

        thoughtCopy.setBody("abc");
        assertEquals("", thought.getBody());

        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag");
        thoughtCopy.setTags(tags);
        assertTrue(thought.getTags().isEmpty());
    }
}
