package com.example.accont;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddIncomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income);
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());

        Button addIncomeBtn = findViewById(R.id.add_income_button);
        addIncomeBtn.setOnClickListener(new AddIncomeListener(this, db));
    }

    private class AddIncomeListener implements View.OnClickListener{
        private Activity activity;
        private AppDatabase db;
        private String content;
        private String date;
        private int price;

        private AddIncomeListener(Activity activity, AppDatabase db){
            this.activity = activity;
            this.db = db;
        }

        @Override
        public void onClick(View view) {
            EditText content_edit = findViewById(R.id.content_input);
            EditText date_edit = findViewById(R.id.date_input);
            EditText price_edit = findViewById(R.id.price_input);
            content = content_edit.getText().toString();
            date = date_edit.getText().toString();
            price = Integer.parseInt(String.valueOf(price_edit.getText()));

            new DataStoreAsyncTask(db, activity, date, content, price).execute();
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private int price;
        private StringBuilder sb;
        private String date;
        private String content;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, String date, String content, int price) {
            this.db = db;
            weakActivity = new WeakReference<>(activity);
            this.date = date;
            this.price = price;
            this.content = content;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            AccountDao accountDao= db.AccountDao();
            accountDao.insert(new AccountEntity(content,price, 0, date));

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return;
            }
        }
    }
}
