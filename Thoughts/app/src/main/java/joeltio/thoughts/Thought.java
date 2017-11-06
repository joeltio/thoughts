package joeltio.thoughts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Thought {
    private long id;
    private String name;
    private String body;
    private HashSet<String> tags;
    private Date creationDate;

    public Thought(String name, String body, HashSet<String> tags, Date creationDate) {
        this.name = name;
        this.body = body;
        this.tags = new HashSet<>(tags);
        this.creationDate = creationDate;
    }

    public Thought(long id, String name, String body, HashSet<String> tags, Date creationDate) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.tags = new HashSet<>(tags);
        this.creationDate = creationDate;
    }

    public Thought(Thought thought) {
        this.name = thought.getName();
        this.body = thought.getBody();
        this.tags = thought.getTags();
        this.creationDate = thought.getCreationDate();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Thought &&
                this.name.equals(((Thought) obj).getName()) &&
                this.body.equals(((Thought) obj).getBody()) &&
                this.tags.equals(((Thought) obj).getTags()) &&
                this.creationDate.equals(((Thought) obj).getCreationDate());
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = new HashSet<>(tags);
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBody() {
        return this.body;
    }

    public HashSet<String> getTags() {
        return new HashSet<>(this.tags);
    }

    public Date getCreationDate() {
        return this.creationDate;
    }
}
