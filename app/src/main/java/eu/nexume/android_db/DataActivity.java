package eu.nexume.android_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {

    TextView textView;
    String nameGetStr;
    MyDataBaseHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        textView = findViewById(R.id.name_text);

        // инициираме MyDataBaseHelper в onCreate метода на MainActivity.java
        dbHelper = new MyDataBaseHelper(this);

        // отваряме БД за четене и запис
        database = dbHelper.getWritableDatabase();

        // Четене на данни от БД с метода query()
        Cursor cursor = database.query(MyDataBaseHelper.TABLE_NAME, new String[]{
                        MyDataBaseHelper.UID, MyDataBaseHelper.COLUMN_USER_NAME},
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {

            // получаване на индексите и стойностите на колоните
            int id = cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.UID));

            nameGetStr = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_USER_NAME));
        }

        textView.setText(nameGetStr);

        cursor.close();
    }

    @Override
    protected void onDestroy() {
        // затваряме връзката с БД
        database.close();
        dbHelper.close();
        super.onDestroy();
    }
}
