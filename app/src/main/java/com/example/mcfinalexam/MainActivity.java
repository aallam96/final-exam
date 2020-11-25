package com.example.mcfinalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DB db;
    private ListView list;
    private ArrayAdapter adapter;
    private ArrayList<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        db = Room.databaseBuilder(getApplicationContext(),
                DB.class, "exam").allowMainThreadQueries().build();
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                R.layout.todo_list_item,
                R.id.task_title,
                taskList);
        this.list.setAdapter(adapter);
        this.addData();
    }

    public void delete(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        db.mQueries().delete(task);
        addData();
    }

    private void addData() {
        List<TodoItem> list = db.mQueries().getAll();
        taskList.clear();
        for (TodoItem item : list) {
            taskList.add(item.item);
        }
        adapter.clear();
        adapter.addAll(taskList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                TodoItem todoItem = new TodoItem(task);
                                db.mQueries().insert(todoItem);
                                addData();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}