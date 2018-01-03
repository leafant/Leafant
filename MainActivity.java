package com.irdeto.sqliteapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper =  new MyDatabaseHelper(this, "BookStore.db", null, 2);
        Button createDatabase = (Button) findViewById(R.id.create_database);

        Button addData = (Button) findViewById(R.id.add_data);

        Button updateData = (Button) findViewById(R.id.update_data);

        Button deleteData = (Button) findViewById(R.id.delete_data);

        Button queryData = (Button) findViewById(R.id.query_data);

        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("name", "The Da Vinci Code2");
//                values.put("author", "Dan Brown2");
//                values.put("pages", 454);
//                values.put("price", 16.95);
//                db.insert("book",null,values);
//                values.clear();
//
//                values.put("name", "The Lost Symbol");
//                values.put("author", "Dan Brown");
//                values.put("pages", 510);
//                values.put("price", 19.94);
//                db.insert("book", null, values);

                db.execSQL("insert into book (name, author,pages,price) values (?,?,?,?)", new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});
                db.execSQL("insert into book (name, author,pages,price) values (?,?,?,?)", new String[]{"The Lost","Dan","500","29.95"});
                Toast.makeText(getApplicationContext(),"Add data",Toast.LENGTH_SHORT).show();

            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values =  new ContentValues();
                values.put("price", 10.99);
                db.update("book", values,"name = ?",new String[]{"The Da Vinci Code"});
                Toast.makeText(getApplicationContext(),"Have update price",Toast.LENGTH_SHORT).show();
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =  dbHelper.getWritableDatabase();
                db.delete("book", "pages > ?", new String[]{"500"});
                Toast.makeText(getApplicationContext(),"Delete data", Toast.LENGTH_SHORT).show();
            }
        });

        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =  dbHelper.getReadableDatabase();
                Cursor cursor = db.query("book", null,null,null,null,null,null);
                if(cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "Book name is " + name);
                        Log.d("MainActivity", "author is " + author);
                        Log.d("MainActivity", "pages is " + pages);
                        Log.d("MainActivity", "price is " + price);
                    } while (cursor.moveToNext());
                }

                cursor.close();
            }
        });
    }
}
