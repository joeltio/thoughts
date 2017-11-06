package joeltio.thoughts;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.*;

public class ThoughtTest {
    @Test
    public void thoughtsKeepsAttributes() {
        String thoughtName = "name";
        String thoughtBody = "body";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        Date date = new Date();
        Thought thought1 = new Thought(thoughtName, thoughtBody, tags, date);

        assertEquals(thoughtName, thought1.getName());
        assertEquals(thoughtBody, thought1.getBody());
        assertEquals(tags, thought1.getTags());
        assertEquals(date, thought1.getCreationDate());

        Long thoughtId = 1L;
        Thought thought2 = new Thought(thoughtId, thoughtName, thoughtBody, tags, date);

        assertEquals(thoughtId, thought2.getId());
        assertEquals(thoughtName, thought2.getName());
        assertEquals(thoughtBody, thought2.getBody());
        assertEquals(tags, thought2.getTags());
        assertEquals(date, thought2.getCreationDate());
    }

    @Test
    public void thoughtsSetNewValues() {
        Thought thought = new Thought("", "", new HashSet<String>(), new Date());

        Long id = 1L;
        assertNotEquals(id, thought.getId());
        thought.setId(id);
        assertEquals(id, thought.getId());

        String name = "name";
        assertNotEquals(name, thought.getName());
        thought.setName(name);
        assertEquals(name, thought.getName());

        assertNotEquals("body", thought.getBody());
        thought.setBody("body");
        assertEquals("body", thought.getBody());

        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        assertNotEquals(tags, thought.getTags());
        thought.setTags(tags);
        assertEquals(tags, thought.getTags());
    }

    private Thought createThought() {
        Date date = new Date();
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");
        return new Thought("name", "body", tags, date);
    }

    @Test
    public void getTagsReturnsCopyOfTags() {
        Thought thought = createThought();
        HashSet<String> tags = thought.getTags();

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
    public void equalThoughtsWithUnequalIdsDoNotEquate() {
        Thought thought1 = createThought();
        thought1.setId(1);
        Thought thought2 = createThought();
        thought2.setId(2);

        assertFalse(thought1.equals(thought2));
    }

    @Test
    public void equalThoughtsWithOneThatHasNoIdEquate() {
        Thought thought1 = createThought();
        thought1.setId(1);
        Thought thought2 = createThought();

        assertTrue(thought1.equals(thought2));
    }

    @Test
    public void thoughtCopiesWhenSettingTagInConstructor() {
        HashSet<String> tags = new HashSet<>();
        Thought thought = new Thought("", "", tags, new Date());
        assertTrue(thought.getTags().isEmpty());
        tags.add("tag1");
        assertTrue(thought.getTags().isEmpty());
    }

    @Test
    public void thoughtCopiesWhenSettingTagInSetTagMethod() {
        Thought thought = new Thought("", "", new HashSet<String>(), new Date());
        HashSet<String> tags = new HashSet<>();
        thought.setTags(tags);
        assertTrue(thought.getTags().isEmpty());
        tags.add("tag1");
        assertTrue(thought.getTags().isEmpty());
    }

    @Test
    public void thoughtDeepCopiesForCopyConstructor() {
        Thought thought = new Thought(1L, "", "", new HashSet<String>(), new Date(0));
        Thought thoughtCopy = new Thought(thought);

        assertEquals(1, (long) thoughtCopy.getId());
        thoughtCopy.setId(2);
        assertEquals(1, (long) thought.getId());

        assertEquals("", thoughtCopy.getName());
        thoughtCopy.setName("abc");
        assertEquals("", thought.getName());

        assertEquals("", thoughtCopy.getBody());
        thoughtCopy.setBody("abc");
        assertEquals("", thought.getBody());

        assertTrue(thoughtCopy.getTags().isEmpty());
        HashSet<String> tags = new HashSet<>();
        tags.add("tag");
        thoughtCopy.setTags(tags);
        assertTrue(thought.getTags().isEmpty());
    }
}
