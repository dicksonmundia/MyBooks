package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText editAuthor, editTitle, editDescription;
    private String title, author, description;
    private int id;

    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editDescription = (EditText) findViewById(R.id.editDescription);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        Intent intent = getIntent();
        title = intent.getStringExtra("book_title");
        author = intent.getStringExtra("book_author");
        description = intent.getStringExtra("book_description");
        id = Integer.parseInt(intent.getStringExtra("book_id"));


        setDisplay(title,author,description,id);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("details", "submit");
                submitDetails();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemMId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemMId == R.id.delete) {
            if (id == 0) {
                Toast.makeText(getApplicationContext(), "Unable to delete book. please select book first", Toast.LENGTH_LONG).show();
            } else {
                if (deleteBook(id)) {
                    //success
                    Toast.makeText(getApplicationContext(), "Book was successfully deleted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //unsuccessful
                    Toast.makeText(getApplicationContext(), "Unable to delete book", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean deleteBook(int id) {
        dbHelper = new DBHelper(this);
        if (dbHelper.deleteBook(this.id)) return true;
        return false;
    }

    private void submitDetails() {

        String mTitle = editTitle.getText().toString().trim();
        String mDesc = editDescription.getText().toString().trim();
        String mAuth = editAuthor.getText().toString().trim();

        if (mAuth.isEmpty() || mDesc.isEmpty() || mTitle.isEmpty()){
            Toast.makeText(getApplicationContext(), "Cannot submit with empty fields", Toast.LENGTH_LONG).show();
        }else {
            dbHelper = new DBHelper(this);
            if (id == 0) {
                //insert
                if (dbHelper.addBook(mTitle,mAuth,mDesc)){
                    Toast.makeText(getApplicationContext(), "Book was successfully added", Toast.LENGTH_LONG).show();
                    editTitle.setText("");
                    editDescription.setText("");
                    editAuthor.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to add book.", Toast.LENGTH_LONG).show();
                }


            } else {
                //update
                Log.d("queryselectoo", "updating"+id);
                if (dbHelper.updateBook(id,mTitle,mAuth,mDesc)){
                    Toast.makeText(getApplicationContext(), "Successfully updated.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Updating failed", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void setDisplay(String title, String author, String description, int id) {
        if (title != "") {
            editTitle.setText(title);
            editDescription.setText(description);
            editAuthor.setText(author);
        }
    }
}