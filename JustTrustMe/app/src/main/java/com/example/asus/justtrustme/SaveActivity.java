package com.example.asus.justtrustme;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveActivity extends AppCompatActivity {

    EditText wr;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        wr = (EditText)findViewById(R.id.msgText);
        save = (Button)findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = wr.getText().toString();
                wr.setText("");

                File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/msgdata");
                if(!saveFile.exists()){
                    saveFile.mkdir();
                }

                try{
                    FileOutputStream fos = openFileOutput("data.txt", Context.MODE_APPEND);

                    PrintWriter writer = new PrintWriter(fos);
                    writer.println(data.getBytes());
                    writer.close();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(SaveActivity.this, OtherSettings.class);
                startActivity(intent);
                finish();
            }

        });

    }

}
