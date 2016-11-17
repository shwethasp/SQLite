package com.shwethasp.mysqldb;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextAdd;
    private Button btnAdd;
    private Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAdd = (EditText) findViewById(R.id.editTextAddress);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        btnAdd.setOnClickListener(this);
        btnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            String name = editTextName.getText().toString().trim();
            String add = editTextAdd.getText().toString().trim();
            if (name.equals("") || add.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }

            DBHelper db = new DBHelper(this);
            db.insertDetails(name, add);
            editTextName.setText("");
            editTextAdd.setText("");
            editTextName.requestFocus();
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
        } else if (v == btnView) {
            Intent i = new Intent(MainActivity.this, ShowAllDetailsActivity.class);
            startActivity(i);
        }
    }
}
