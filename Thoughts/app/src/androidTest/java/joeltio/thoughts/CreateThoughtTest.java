package joeltio.thoughts;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class CreateThoughtTest {
    @Rule
    public ActivityTestRule<CreateThoughtActivity> activityRule = new ActivityTestRule<>(CreateThoughtActivity.class);

    @Before
    @After
    public void clearDatabase() {
        DbAdapter dbAdapter = new DbAdapter(activityRule.getActivity());
        dbAdapter.open();
        dbAdapter.reset();
        dbAdapter.close();
    }

    private Mind getMind() {
        DbAdapter dbAdapter = new DbAdapter(activityRule.getActivity());
        dbAdapter.open();
        Mind mind = dbAdapter.getMind();
        dbAdapter.close();
        return mind;
    }

    @Test
    public void activitySavesThoughtsIntoDatabase() {
        String thoughtName = "Test thought 1";
        String thoughtBody = "The quick brown fox jumped over the fence.";
        onView(withId(R.id.thought_name_field)).perform(typeText(thoughtName));
        onView(withId(R.id.thought_body_field)).perform(typeText(thoughtBody));
        onView(withId(R.id.thought_tags_field)).perform(typeText("tag1"));
        onView(withId(R.id.action_thought_done)).perform(click());

        Mind mind = getMind();
        assertEquals(1, mind.getAllThoughts().size());
        Thought thought = mind.getAllThoughts().get(0);
        assertEquals(thoughtName, thought.getName());
        assertEquals(thoughtBody, thought.getBody());
        assertEquals(1, thought.getTags().size());
        assertTrue(thought.getTags().contains("tag1"));
    }

    @Test
    public void nameFieldSingleLine() {
        onView(withId(R.id.thought_name_field)).perform(typeText("a\n"))
                .check(matches(not(withText(containsString("\n")))));

        onView(withId(R.id.thought_body_field)).perform(typeText("a"));
        onView(withId(R.id.thought_tags_field)).perform(typeText("a"));
        onView(withId(R.id.action_thought_done)).perform(click());

        Mind mind = getMind();
        Thought thought = mind.getAllThoughts().get(0);
        assertEquals("a", thought.getName());
    }

    @Test
    public void tagsFieldSingleLine() {
        onView(withId(R.id.thought_name_field)).perform(typeText("a"));
        onView(withId(R.id.thought_body_field)).perform(typeText("a"));
        onView(withId(R.id.thought_tags_field)).perform(typeText("a\n"))
                .check(matches(not(withText(containsString("\n")))));
        onView(withId(R.id.action_thought_done)).perform(click());

        Mind mind = getMind();
        Thought thought = mind.getAllThoughts().get(0);
        assertTrue(thought.getTags().contains("a"));
    }

    @Test
    public void disallowEmptyNameAndBody() {
        onView(withId(R.id.action_thought_done)).perform(click());

        Mind mind = getMind();
        assertTrue(mind.getAllThoughts().isEmpty());

        onView(withId(R.id.thought_name_field)).perform(typeText("a"));
        onView(withId(R.id.action_thought_done)).perform(click());

        mind = getMind();
        assertTrue(mind.getAllThoughts().isEmpty());

        onView(withId(R.id.thought_name_field)).perform(clearText());
        onView(withId(R.id.thought_body_field)).perform(typeText("a"));
        onView(withId(R.id.action_thought_done)).perform(click());

        mind = getMind();
        assertTrue(mind.getAllThoughts().isEmpty());
    }
}
