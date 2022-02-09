package com.example.booklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public static class FeedEntry implements BaseColumns {
        public static final String BOOKS_TABLE_NAME = "books";
        public static final String BOOKS_COLUMN_ID = "id";
        public static final String BOOKS_COLUMN_TITLE = "title";
        public static final String BOOKS_COLUMN_AUTHOR = "author";
        public static final String BOOKS_COLUMN_DESCRIPTION = "description";
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Book";



    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.BOOKS_TABLE_NAME + " (" +
                    FeedEntry.BOOKS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedEntry.BOOKS_COLUMN_TITLE + " TEXT," +
                    FeedEntry.BOOKS_COLUMN_AUTHOR + " TEXT," +
                    FeedEntry.BOOKS_COLUMN_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.BOOKS_TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /// INSERT BOOK
    public Boolean addBook (String title, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedEntry.BOOKS_COLUMN_AUTHOR, author);
        contentValues.put(FeedEntry.BOOKS_COLUMN_DESCRIPTION, description);
        contentValues.put(FeedEntry.BOOKS_COLUMN_TITLE, title);

        long newId = db.insert(FeedEntry.BOOKS_TABLE_NAME,null,contentValues);
        if (newId == -1) {
            return false;
        } else  {
            return true;
        }
    }

    // Update book
    public Boolean updateBook (int id, String title, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedEntry.BOOKS_COLUMN_AUTHOR, author);
        contentValues.put(FeedEntry.BOOKS_COLUMN_DESCRIPTION, description);
        contentValues.put(FeedEntry.BOOKS_COLUMN_TITLE, title);

        String selection = FeedEntry.BOOKS_COLUMN_ID +" = ?";
        String[] selectionArg = {String.valueOf(id)};

        int rows = db.update(FeedEntry.BOOKS_TABLE_NAME, contentValues, selection, selectionArg);

        if (rows > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Delete book
    public Boolean deleteBook (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = FeedEntry.BOOKS_COLUMN_ID +" = ?";
        String[] selectionArg = {String.valueOf(id)};



        int deletedRows = db.delete(FeedEntry.BOOKS_TABLE_NAME,selection, selectionArg);

        if (deletedRows > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Get all books
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();

        String[] columns = {
            FeedEntry.BOOKS_COLUMN_ID,
            FeedEntry.BOOKS_COLUMN_TITLE,
            FeedEntry.BOOKS_COLUMN_AUTHOR,
            FeedEntry.BOOKS_COLUMN_DESCRIPTION,
        };

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FeedEntry.BOOKS_TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setId(cursor.getLong(cursor.getColumnIndexOrThrow(FeedEntry.BOOKS_COLUMN_ID)));
            book.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.BOOKS_COLUMN_AUTHOR)));
            book.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.BOOKS_COLUMN_TITLE)));
            book.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.BOOKS_COLUMN_DESCRIPTION)));

            bookList.add(book);
        }
        cursor.close();
        return bookList;
    }


}
