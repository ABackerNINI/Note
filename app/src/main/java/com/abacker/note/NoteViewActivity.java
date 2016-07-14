package com.abacker.note;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import Common.Common;

/**
 * Created by 11059 on 2016/7/14 0014.
 */
public class NoteViewActivity extends Activity {
    TextView title, author, ctime, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view);
        title = (TextView) findViewById(R.id.textView_title);
        author = (TextView) findViewById(R.id.textView_author);
        ctime = (TextView) findViewById(R.id.textView_ctime);
        content = (TextView) findViewById(R.id.textView_content);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        Cursor cursor = MainActivity.dbUtility.Query(Common.DB_TABLE_NAME,
                Common.DB_COLUMN_TITLE + "," +
                        Common.DB_COLUMN_AUTHOR + "," +
                        Common.DB_COLUMN_CTIME + "," +
                        Common.DB_COLUMN_CONTENT, id);

        title.setText(cursor.getString(0));
        author.setText(cursor.getString(1));
        ctime.setText(cursor.getString(2));
        content.setText(cursor.getString(3));

        cursor.close();
    }
}
