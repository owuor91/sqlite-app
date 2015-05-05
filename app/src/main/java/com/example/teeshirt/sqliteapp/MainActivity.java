package com.example.teeshirt.sqliteapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    DatabaseHelper myDb;

    EditText etName, etSurname, etMarks;
    Button btnAdd, btnRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        etName = (EditText)findViewById(R.id.etName);
        etSurname = (EditText)findViewById(R.id.etSurname);
        etMarks = (EditText)findViewById(R.id.etMarks);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnRead = (Button)findViewById(R.id.btnRead);
        addData();
        fetchAllRecords();
    }

    public void addData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(etName.getText().toString(), etSurname.getText().toString(), etMarks.getText().toString());
                        if(isInserted ==true){
                            Toast.makeText(MainActivity.this, "Record Inserted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Record Insertion Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void fetchAllRecords(){
        btnRead.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor records= myDb.getAllRecords();
                        if (records.getCount()==0){
                            showMessage("Error","No Records Found");
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (records.moveToNext()){
                            buffer.append("Id :" +records.getString(0)+"\n");
                            buffer.append("Name :" +records.getString(1)+"\n");
                            buffer.append("Surname :" +records.getString(2)+"\n");
                            buffer.append("Marks :" +records.getString(3)+"\n\n");
                        }
                            showMessage("Records", buffer.toString());
                    }
                }
        );
    }



    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}
