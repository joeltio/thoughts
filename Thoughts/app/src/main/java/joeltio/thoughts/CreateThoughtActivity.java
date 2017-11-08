package joeltio.thoughts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;
import java.util.HashSet;

public class CreateThoughtActivity extends AppCompatActivity {
    EditText thoughtNameEditText;
    EditText thoughtBodyEditText;
    EditText thoughtTagsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thought);

        this.thoughtNameEditText = findViewById(R.id.thought_name_field);
        this.thoughtBodyEditText = findViewById(R.id.thought_body_field);
        this.thoughtTagsEditText = findViewById(R.id.thought_tags_field);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thought, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_thought_done) {
            String name = this.thoughtNameEditText.getText().toString();
            String body = this.thoughtBodyEditText.getText().toString();
            HashSet<String> tags = new HashSet<>();
            tags.add(this.thoughtTagsEditText.getText().toString());

            if (name.isEmpty() || body.isEmpty()) {
                return false;
            }

            Thought thought = new Thought(name, body, tags, new Date());

            DbAdapter dbAdapter = new DbAdapter(this);
            dbAdapter.open();
            dbAdapter.insertThought(thought);
            dbAdapter.close();
        }
        return super.onOptionsItemSelected(item);
    }
}
