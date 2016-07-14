package com.abacker.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import Adapter.ListItemAdapter;
import Common.Common;
import DBUtility.DBUtility;

public class MainActivity extends AppCompatActivity {
    public static DBUtility dbUtility = null;
    public static boolean needUpdate = false;

    private ListView listView = null;
    private TextView textView_add = null;
    private ListItemAdapter listItemAdapter = null;

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
        UpdateList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbUtility.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        dbUtility = new DBUtility(this);

        listView = (ListView) findViewById(R.id.listView);
        textView_add = (TextView) findViewById(R.id.textView_add);

        needUpdate = true;
        UpdateList();

        textView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
                startActivity(intent);
            }
        });

        textView_add.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    textView_add.setText(" × ");
                    textView_add.setBackgroundColor(0);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    textView_add.setText(" + ");
                    textView_add.setBackgroundColor(0);
                }
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题

                        .setMessage("确定删除该条笔记吗?")//设置显示的内容

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                dbUtility.Delete("NOTE", listItemAdapter.getIdByPosition(position));
                                needUpdate = true;
                                UpdateList();
                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮


                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件

                    }

                }).show();//在按键响应事件中显示此对话框
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NoteViewActivity.class);
                intent.putExtra("ID", listItemAdapter.getIdByPosition(position));
                startActivity(intent);
            }
        });
    }


    private void UpdateList() {
        if (needUpdate) {
            if (listItemAdapter != null) listItemAdapter.close();
            listItemAdapter = new ListItemAdapter(this, dbUtility.Query(Common.DB_TABLE_NAME,
                            Common.DB_COLUMN_ID + "," +
                            Common.DB_COLUMN_TITLE + "," +
                            Common.DB_COLUMN_CTIME, -1));

            listView.setAdapter(listItemAdapter);
            needUpdate = false;
            System.out.println("UpdateList");
        }
    }
}
