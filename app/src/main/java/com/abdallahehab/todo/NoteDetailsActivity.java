package com.abdallahehab.todo;

import static com.abdallahehab.todo.Constants.BODY_ID;
import static com.abdallahehab.todo.Constants.DATE_ID;
import static com.abdallahehab.todo.Constants.NOTE_ID;
import static com.abdallahehab.todo.Constants.NOTE_STATUS;
import static com.abdallahehab.todo.Constants.TITLE_ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.abdallahehab.todo.R.id;
import com.abdallahehab.todo.databinding.ActivityNoteDetailsBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class NoteDetailsActivity extends AppCompatActivity {

    ActivityNoteDetailsBinding binding;
    TextView edt_txt_title, edt_txt_body, txt_view_date;
    String received_title, revived_noteBody, received_date, noteId;
    Boolean noteStatus;
    Intent intent;
    DatabaseAdapter database_adapter;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        setContentView(binding.getRoot());

        checkNoteStatus();

        if (!noteStatus) {

            getDateFromIntent();

            setDataToView();
        }

        setActionBarActionListener();

        binding.topAppBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        binding.topAppBar.setNavigationOnClickListener(view -> {
            super.onBackPressed();
        });


    }

    private void initialize() {
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        edt_txt_title = binding.edtTxtTitle;
        edt_txt_body = binding.edtTxtNoteBody;
        txt_view_date = binding.txtDate;
        intent = getIntent();
        database_adapter = new DatabaseAdapter(this);

    }

    private void getDateFromIntent() {
        received_title = intent.getStringExtra(TITLE_ID);
        revived_noteBody = intent.getStringExtra(BODY_ID);
        received_date = intent.getStringExtra(DATE_ID);
        noteId = intent.getStringExtra(NOTE_ID);
    }

    private void setDataToView() {
        edt_txt_title.setText(received_title);
        edt_txt_body.setText(revived_noteBody);
        txt_view_date.setText(received_date);
    }

    private void checkNoteStatus() {
        noteStatus = intent.getBooleanExtra(NOTE_STATUS, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setActionBarActionListener() {
        binding.topAppBar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case id.save:
                    saveNote();
                    return true;

                case id.delete:
                        deleteNote();
                    return true;
                default:
                    return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveNote() {
        setDataToDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataToDatabase() {
        received_title = edt_txt_title.getText().toString();
        revived_noteBody = edt_txt_body.getText().toString();
        received_date = Constants.getCurrentDateAsString();

        if (received_title.isEmpty() || revived_noteBody.isEmpty()) {
            Toast.makeText(this, "neither title nor note can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (noteStatus) {
                //insert new note
                long id = database_adapter.insertEntity(
                        new NoteDataModel(
                                received_title,
                                revived_noteBody,
                                received_date,
                                noteId));
            } else {
                // update the note
                long id = database_adapter.updateEntity(
                        new NoteDataModel(
                                received_title,
                                revived_noteBody,
                                received_date
                                , noteId));
            }
            onBackPressed();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

        }
    }

    private void deleteNote(){

        if(!noteStatus){
            DatabaseAdapter database_adapter = new DatabaseAdapter(this);
            database_adapter.deleteEntities(new String[]{ noteId });
            onBackPressed();
            Toast.makeText(this, "note deleted", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onBackPressed() {
        if (!edt_txt_title.getText().toString().equals(received_title) || !edt_txt_body.getText().toString().equals(revived_noteBody)) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("Save your changes or discard them ?")
                    .setPositiveButton("save", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveNote();
                        }
                    })
                    .setNegativeButton("discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NoteDetailsActivity.super.onBackPressed();

                        }
                    })
                    .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
            return;
        }
        super.onBackPressed();


    }
}