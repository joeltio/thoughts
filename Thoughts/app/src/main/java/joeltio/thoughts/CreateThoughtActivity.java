package joeltio.thoughts;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class CreateThoughtActivity extends AppCompatActivity {
    EditText thoughtNameEditText;
    EditText thoughtBodyEditText;
    EditText thoughtTagsEditText;

    private void shakeEditText(EditText editText) {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(200);
        shake.setInterpolator(new CycleInterpolator(7));
        editText.startAnimation(shake);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thought);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            String tagsString = this.thoughtTagsEditText.getText().toString();

            HashSet<String> tags = new HashSet<>(Arrays.asList(tagsString.split(",")));

            if (name.isEmpty()) {
                shakeEditText(this.thoughtNameEditText);
                return false;
            } else if (body.isEmpty()) {
                shakeEditText(this.thoughtBodyEditText);
                return false;
            }

            Thought thought = new Thought(name, body, tags, new Date());

            DbAdapter dbAdapter = new DbAdapter(this);
            dbAdapter.open();
            dbAdapter.insertThought(thought);
            dbAdapter.close();
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
