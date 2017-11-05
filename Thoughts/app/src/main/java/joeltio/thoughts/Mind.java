package joeltio.thoughts;

import java.util.ArrayList;

public class Mind {
    private ArrayList<Thought> thoughts;

    public Mind() {
        this.thoughts = new ArrayList<>();
    }

    public void addThought(Thought thought) {
        this.thoughts.add(new Thought(thought));
    }

    public boolean contains(Thought thought) {
        return this.thoughts.contains(thought);
    }

    public ArrayList<Thought> getAllThoughts() {
        return new ArrayList<>(thoughts);
    }

    public ArrayList<Thought> filterByTags(ArrayList<String> tags, boolean matchExact) {
        ArrayList<Thought> filteredThoughts = new ArrayList<>();
        for (Thought thought : this.thoughts) {
            if (matchExact) {
                if (thought.getTags().equals(tags)) {
                    filteredThoughts.add(new Thought(thought));
                }
            } else {
                for (String tag : thought.getTags()) {
                    if (tags.contains(tag)) {
                        filteredThoughts.add(new Thought(thought));
                        break;
                    }
                }
            }
        }

        return filteredThoughts;
    }
}
