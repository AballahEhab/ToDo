package com.abdallahehab.todo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.abdallahehab.todo.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<NoteDataModel> dataList = new ArrayList<NoteDataModel>();
        dataList.add(new NoteDataModel("this is my title","the ",getCurrentDateAsString()));
        NoteAdapter adapter = new NoteAdapter(dataList);

        binding.userDataRecyclerview.setAdapter(adapter);


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getCurrentDateAsString(){
        LocalDateTime date = LocalDateTime.now();   ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(date);
    }
}