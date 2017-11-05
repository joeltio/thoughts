package joeltio.thoughts;

import java.util.ArrayList;
import java.util.Date;

public class Thought {
    private String name;
    private String body;
    private ArrayList<String> tags;
    private Date creationDate;

    public Thought(String name, String body, ArrayList<String> tags, Date creationDate) {
        this.name = name;
        this.body = body;
        this.tags = tags;
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Thought &&
                this.name.equals(((Thought) obj).getName()) &&
                this.body.equals(((Thought) obj).getBody()) &&
                this.tags.equals(((Thought) obj).getTags()) &&
                this.creationDate.equals(((Thought) obj).getCreationDate());
    }

    public String getName() {
        return this.name;
    }

    public String getBody() {
        return this.body;
    }

    public ArrayList<String> getTags() {
        return new ArrayList<>(this.tags);
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
}
