package com.abacker.note;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import Common.Common;

/**
 * Created by 11059 on 2016/7/14 0014.
 */
public class AddNewNoteActivity extends Activity {
    private Button btn_save = null;
    private EditText editText_title, editText_author, editText_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_new_note);

        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_author = (EditText) findViewById(R.id.editText_author);
        editText_content = (EditText) findViewById(R.id.editText_content);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_title = editText_title.getText().toString();
                String str_author = editText_author.getText().toString();
                String str_content = editText_content.getText().toString();

                if (str_title.equals("")) {
                    str_title = str_content.length() < 15 ? str_content : str_content.substring(0, 14);
                }
                if (str_title.equals("")) {
                    Toast.makeText(AddNewNoteActivity.this, "标题和内容不能同时为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                MainActivity.dbUtility.Insert(
                        Common.DB_TABLE_NAME,
                        str_title,
                        str_author,
                        simpleDateFormat.format(date),
                        str_content
                );
                MainActivity.needUpdate = true;
                finish();
            }
        });
    }
}
