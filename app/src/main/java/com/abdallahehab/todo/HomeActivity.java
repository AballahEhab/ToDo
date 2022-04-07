package com.abdallahehab.todo;

import static com.abdallahehab.todo.Constants.NOTE_STATUS;
import static com.abdallahehab.todo.R.id.delete;
import static com.abdallahehab.todo.R.id.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.abdallahehab.todo.databinding.ActivityHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static ActivityHomeBinding binding;
    private NotesAdapter adapter;
    static ArrayList<NoteDataModel> notesList;
    private Boolean hideSearchView = true;
    DatabaseAdapter database_adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();

        setContentView(binding.getRoot());

        updateUi();

        setFABListener();

        setActionBarActionListener();

        binding.ivBackArrow.setOnClickListener(view -> {
            toggleSearchView();
        });

        binding.etSearchContacts.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(binding.etSearchContacts.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private void initialize() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        notesList = new ArrayList<NoteDataModel>();
        database_adapter =  new DatabaseAdapter(this);
        adapter = new NotesAdapter();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUi(){
        getDataFromDataBase();
        constructRv();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDataFromDataBase() {
        notesList = database_adapter.getAllEntities();
        emptyListVisibility(notesList.isEmpty());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void constructRv() {

        adapter.setDataList(notesList);

        binding.rvAllNotesHome.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    private void setFABListener() {
        binding.extendedFab.setOnClickListener(view ->  {
            Intent intent = new Intent(this,NoteDetailsActivity.class);
            intent.putExtra(NOTE_STATUS,true);
            startActivity(intent);
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setActionBarActionListener() {
        binding.bottomAppBar.setOnMenuItemClickListener(item -> {

            switch(item.getItemId()) {
                case search:
                    toggleSearchView();
                    return true;
//
//                case share:
//
//                    return true;

                case delete:
                    if(((NotesAdapter)binding.rvAllNotesHome.getAdapter()).getInSelectMode()){
                        NotesAdapter updatedAdapter = (NotesAdapter) binding.rvAllNotesHome.getAdapter();
                        database_adapter.deleteEntities((String[]) (updatedAdapter.getItemsSelectedIndexes().toArray(new String[0])));
                        updatedAdapter.deletSelecetedItems();
                        updateUi();
                    }
                    return true;

                default:
                    return false;
            }
        });
    }

    private void toggleSearchView(){
        if(hideSearchView){
            binding.bottomAppBar.setVisibility( View.GONE);
            binding.extendedFab.setVisibility( View.GONE);


            binding.customSearchAppBar.setVisibility( View.VISIBLE);
            hideSearchView = false;
        }else{
            binding.customSearchAppBar.setVisibility( View.GONE);


            binding.bottomAppBar.setVisibility( View.VISIBLE);
            binding.extendedFab.setVisibility( View.VISIBLE);

            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0); // make keyboard hide


            hideSearchView = true;
        }
    }

    private void emptyListVisibility(Boolean show){
        if(show)
            binding.emptyListLayer.setVisibility(View.VISIBLE);
        else
            binding.emptyListLayer.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(!hideSearchView)
            toggleSearchView();
        else
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

}