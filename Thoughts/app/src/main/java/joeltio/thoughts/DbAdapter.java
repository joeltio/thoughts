package joeltio.thoughts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.HashSet;

public class DbAdapter {
    private static final String DATABASE_NAME = "thoughts.db";
    private static final int DATABASE_VERSION = 1;

    // Thoughts table
    private static final String THOUGHTS_TABLE = "thoughts";
    private static final String THOUGHTS_TABLE_COL_ID = "id";
    private static final String THOUGHTS_TABLE_COL_NAME = "name";
    private static final String THOUGHTS_TABLE_COL_BODY = "body";
    private static final String THOUGHTS_TABLE_COL_CREATION_DATE = "creation_date";

    private static final String[] THOUGHTS_TABLE_COLUMNS = {
            THOUGHTS_TABLE_COL_ID, THOUGHTS_TABLE_COL_NAME, THOUGHTS_TABLE_COL_BODY,
            THOUGHTS_TABLE_COL_CREATION_DATE
    };

    private static final String THOUGHTS_TABLE_CREATE = "CREATE TABLE " + THOUGHTS_TABLE + "("
            + THOUGHTS_TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + THOUGHTS_TABLE_COL_NAME + " TEXT NOT NULL, "
            + THOUGHTS_TABLE_COL_BODY + " TEXT NOT NULL, "
            + THOUGHTS_TABLE_COL_CREATION_DATE + " INTEGER NOT NULL, "
            + "CONSTRAINT CREATION_DATE_IS_DATE CHECK(date(" + THOUGHTS_TABLE_COL_CREATION_DATE + ") IS NOT NULL)"
            + ");";

    // Tags table
    private static final String TAGS_TABLE = "tags";
    private static final String TAGS_TABLE_COL_THOUGHT_ID = "thought_id";
    private static final String TAGS_TABLE_COL_TAG = "tag";

    private static final String[] TAGS_TABLE_COLUMNS = {
            TAGS_TABLE_COL_THOUGHT_ID, TAGS_TABLE_COL_TAG
    };

    private static final String TAGS_TABLE_CREATE = "CREATE TABLE " + TAGS_TABLE + "("
            + TAGS_TABLE_COL_THOUGHT_ID + " TEXT NOT NULL, "
            + TAGS_TABLE_COL_TAG + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + TAGS_TABLE_COL_THOUGHT_ID + ") REFERENCES "
            + THOUGHTS_TABLE + "(" + THOUGHTS_TABLE_COL_ID + ")"
            + ");";

    private SQLiteDatabase SQLdb;
    private DbHelper dbHelper;
    private Context context;

    public DbAdapter(Context c) {
        this.context = c;
    }

    public void open() {
        this.dbHelper = new DbHelper(this.context);
        this.SQLdb = this.dbHelper.getWritableDatabase();
    }

    public void close() {
        this.SQLdb.close();
    }

    public void reset() {
        this.SQLdb.execSQL("DROP TABLE IF EXISTS " + TAGS_TABLE);
        this.SQLdb.execSQL("DROP TABLE IF EXISTS " + THOUGHTS_TABLE);
        this.SQLdb.execSQL(THOUGHTS_TABLE_CREATE);
        this.SQLdb.execSQL(TAGS_TABLE_CREATE);
    }

    public void insertThought(Thought thought) {
        ContentValues thoughtsTableValues = new ContentValues();
        thoughtsTableValues.put(THOUGHTS_TABLE_COL_NAME, thought.getName());
        thoughtsTableValues.put(THOUGHTS_TABLE_COL_BODY, thought.getBody());
        thoughtsTableValues.put(THOUGHTS_TABLE_COL_CREATION_DATE, thought.getCreationDate().getTime());
        long id = this.SQLdb.insert(THOUGHTS_TABLE, null, thoughtsTableValues);

        for (String tag : thought.getTags()) {
            ContentValues tagsTableValues = new ContentValues();
            tagsTableValues.put(TAGS_TABLE_COL_THOUGHT_ID, id);
            tagsTableValues.put(TAGS_TABLE_COL_TAG, tag);
            this.SQLdb.insert(TAGS_TABLE, null, tagsTableValues);
        }
    }

    public Mind getMind() {
        Mind mind = new Mind();
        Cursor cursor = this.SQLdb.query(THOUGHTS_TABLE, THOUGHTS_TABLE_COLUMNS,
                null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            String body = cursor.getString(2);
            Date creationDate = new Date(cursor.getLong(3));
            HashSet<String> tags = getTags(id);

            Thought thought = new Thought(id, name, body, tags, creationDate);
            mind.addThought(thought);
        }

        cursor.close();

        return mind;
    }

    private HashSet<String> getTags(long id) {
        Cursor cursor = this.SQLdb.query(TAGS_TABLE, TAGS_TABLE_COLUMNS,
                TAGS_TABLE_COL_THOUGHT_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null);

        HashSet<String> tags = new HashSet<>();
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            tags.add(cursor.getString(1));
        }

        cursor.close();
        return tags;
    }


    private static class DbHelper extends SQLiteOpenHelper {
        private DbHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private void deleteAll(SQLiteDatabase db) {
            db.execSQL("DROP TABLE IF EXISTS " + TAGS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + THOUGHTS_TABLE);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(THOUGHTS_TABLE_CREATE);
            db.execSQL(TAGS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DbHelper.class.getName(),
                    "Updating database version from version " + oldVersion +
                            " to version " + newVersion + ". This will destroy all data.");
            deleteAll(db);
            onCreate(db);
        }
    }
}
