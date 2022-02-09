package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("creation", "created");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClick();
            }
        });

    }

    private void fabClick() {
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("book_id", "0");
        intent.putExtra("book_author", "");
        intent.putExtra("book_title", "");
        intent.putExtra("book_description", "");
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        dbHelper = new DBHelper(this);
        List<Book> dbBooks = dbHelper.getAllBooks();
        List<Book> bookList = new ArrayList<>();
        int i = 0 ;
        while (i < 14) {
            Book book = new Book();
            book.setTitle("title: "+i);
            book.setAuthor("author: "+i);
            book.setDescription(("description: "+i));
            book.setId(i);

            bookList.add(book);
            i++;
        }

        recyclerView = (RecyclerView) findViewById(R.id.booksRecyclerView);
        BookListAdapter bookListAdapter = new BookListAdapter(getApplicationContext(), dbBooks);
        recyclerView.setAdapter(bookListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("creation", "resumed");
        super.onResume();
    }
}