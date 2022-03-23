package com.example.accont;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.lang.ref.WeakReference;
import java.security.Timestamp;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.index);
        Button incomeBtn = findViewById(R.id.income_button);
        Button outgoBtn = findViewById(R.id.outgo_button);
        AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
        new DataStoreAsyncTask(db, this, tv).execute();

        incomeBtn.setOnClickListener(new AccountListener("income"));
        outgoBtn.setOnClickListener(new AccountListener("outgo"));

    }

    private class ButtonClickListener implements View.OnClickListener {
        private Activity activity;
        private AppDatabase db;
        private TextView tv;
        private EditText et;

        private ButtonClickListener(Activity activity, AppDatabase db, TextView tv, EditText et) {
            this.activity = activity;
            this.db = db;
            this.tv = tv;
            this.et = et;
        }

        @Override
        public void onClick(View view) {
            //new DataStoreAsyncTask(db, activity).execute();

        }
    }

    private class AccountListener implements View.OnClickListener {
        private String type;

        public AccountListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(View view) {
            switch (type){
                case "income":
                    Intent income_intent = new Intent(getApplication(), AddIncomeActivity.class);
                    startActivity(income_intent);
                    break;
                case "outgo":
                    Intent outgo_intent = new Intent(getApplication(), AddOutgoActivity.class);
                    startActivity(outgo_intent);
                    break;
            }
        }
    }

    private static class DataStoreAsyncTask extends AsyncTask<Void, Void, Integer> {
        private WeakReference<Activity> weakActivity;
        private AppDatabase db;
        private TextView textView;
        private StringBuilder sb;
        private EditText et;

        public DataStoreAsyncTask(AppDatabase db, Activity activity, TextView tv) {
            this.db = db;
            this.textView= tv;
            weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            AccountDao accountDao= db.AccountDao();

            sb = new StringBuilder();
            List<AccountEntity> atList = accountDao.getAll();
            for (AccountEntity at : atList) {
                sb.append(at.getTime()+"  "+at.getContent()+"  "+at.getIncome()).append("\n");
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            Activity activity = weakActivity.get();
            if (activity == null) {
                return;
            }

            textView.setText(sb.toString());

        }
    }
}