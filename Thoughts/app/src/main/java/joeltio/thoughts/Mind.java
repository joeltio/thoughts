package joeltio.thoughts;

import java.util.ArrayList;

public class Mind {
    private ArrayList<Thought> thoughts;

    public Mind() {
        this.thoughts = new ArrayList<>();
    }

    public void addThought(Thought thought) {
        this.thoughts.add(thought);
    }

    public boolean contains(Thought thought) {
        return this.thoughts.contains(thought);
    }
}
