package com.example.booklist;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListHolder> {

    private List<Book> bookList;
    private Context context;

    public BookListAdapter (Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public BookListAdapter.BookListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleBookView = LayoutInflater.from(context).inflate(R.layout.single_book, parent, false);
        return new BookListHolder(singleBookView);
    }


    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.BookListHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (bookList.size() > 0){
            return bookList.size();
        }
        return 0;
    }

    public class BookListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView txtAuthor;
        private final TextView txtTitle;
        private final TextView txtDescription;

        public BookListHolder(@NonNull View itemView) {
            super(itemView);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            itemView.setOnClickListener(this);
        }

        public void bind (Book bookHolder) {
            txtTitle.setText(bookHolder.getTitle());
            txtAuthor.setText(bookHolder.getAuthor());
            String descr = bookHolder.getDescription();
            if(descr.length() > 50){
                descr = descr.substring(0, 80);
            }
            txtDescription.setText(descr+"...");
        }


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Book bk = bookList.get(position);

            Intent intent = new Intent(v.getContext(), BookActivity.class);
            intent.putExtra("book_id", String.valueOf(bk.getId()));
            intent.putExtra("book_author", bk.getAuthor());
            intent.putExtra("book_title", bk.getTitle());
            intent.putExtra("book_description", bk.getDescription());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);

        }
    }
}
