package eu.nexume.android_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button insertButton;
    String nameSaveStr, nameGetStr;
    MyDataBaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // инициираме MyDataBaseHelper в onCreate метода на MainActivity.java
        dbHelper = new MyDataBaseHelper(this);

        // отваряме БД за четене и запис
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.edit_text);

        textView = findViewById(R.id.name_text);

        insertButton = findViewById(R.id.insert_button);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameSaveStr = editText.getText().toString().trim();
                // добавяне на запис
                String insertQuery = "INSERT INTO " +
                        MyDataBaseHelper.TABLE_NAME + " ("
                        + MyDataBaseHelper.COLUMN_USER_NAME + ") VALUES ('"
                        + nameSaveStr + "')";

                database.execSQL(insertQuery);

//                ContentValues values = new ContentValues();
//                values.put(MyDataBaseHelper.COLUMN_USER_NAME, nameSaveStr);
//                database.insert(MyDataBaseHelper.TABLE_NAME, null, values);

                // затваряме връзката с БД
                database.close();
                dbHelper.close();

                recreate();
            }
        });

        Cursor cursor = database.query(MyDataBaseHelper.TABLE_NAME, new String[]{
                        MyDataBaseHelper.UID, MyDataBaseHelper.COLUMN_USER_NAME},
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.UID));

            nameGetStr = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_USER_NAME));
        }

        textView.setText(nameGetStr);

        cursor.close();

    }
}
