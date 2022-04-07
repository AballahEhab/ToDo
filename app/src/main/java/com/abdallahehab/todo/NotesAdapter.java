package com.abdallahehab.todo;

import static com.abdallahehab.todo.Constants.BODY_ID;
import static com.abdallahehab.todo.Constants.DATE_ID;
import static com.abdallahehab.todo.Constants.NOTE_ID;
import static com.abdallahehab.todo.Constants.NOTE_STATUS;
import static com.abdallahehab.todo.Constants.TITLE_ID;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdallahehab.todo.databinding.NoteItemLayoutBinding;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteItemViewHolder> {

    private ArrayList<NoteDataModel> dataList;
    private ArrayList<NoteDataModel> shownDataList;
    private ArrayList<String> itemsSelectedIndexes;
    private Boolean isInSelectMode;

    public ArrayList<String> getItemsSelectedIndexes() {
        return itemsSelectedIndexes;
    }

    public Boolean getInSelectMode() {
        return isInSelectMode;
    }

    public void setInSelectMode(Boolean inSelectMode) {
        isInSelectMode = inSelectMode;
    }

    public void setDataList(ArrayList<NoteDataModel> dataList) {
        this.dataList = dataList;
        shownDataList = new ArrayList<>();
        showAllData();
    }

    private void showAllData() {
        for (int i = 0; i < dataList.size(); i++) {
           shownDataList.add(dataList.get(i));
        }
    }

    public NotesAdapter() {
        isInSelectMode = false;
        itemsSelectedIndexes = new ArrayList<>();
    }

    public NotesAdapter(ArrayList<NoteDataModel> dataList) {
        setDataList(dataList);
        isInSelectMode = false;
        itemsSelectedIndexes = new ArrayList<>();
    }

    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NoteItemLayoutBinding binding = NoteItemLayoutBinding.inflate(inflater, parent, false);
        return new NoteItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        NoteDataModel dataItem = shownDataList.get(position);
        NoteItemLayoutBinding binding = holder.binding;

        binding.txtNoteTitle.setText(dataItem.getTitle());
        binding.txtNoteBody.setText(dataItem.getBody());
        binding.txtNoteDate.setText(dataItem.getDate());


        binding.getRoot().setOnClickListener(view -> {


            if (isInSelectMode) {
                ((MaterialCardView) view).toggle();
                if (itemsSelectedIndexes.contains((dataItem.getId()))) {
                    itemsSelectedIndexes.remove((dataItem.getId()));
                    if (itemsSelectedIndexes.isEmpty())
                        isInSelectMode = false;
                } else {
                    itemsSelectedIndexes.add((dataItem.getId()));
                }
            } else {
                Context context = binding.getRoot().getContext();

                Intent intent = new Intent(context, NoteDetailsActivity.class);
                intent.putExtra(TITLE_ID, dataItem.getTitle());
                intent.putExtra(BODY_ID, dataItem.getBody());
                intent.putExtra(DATE_ID, dataItem.getDate());
                intent.putExtra(NOTE_ID, dataItem.getId());
                intent.putExtra(NOTE_STATUS, false);
                context.startActivity(intent);
            }


        });
        binding.getRoot().setOnLongClickListener(view -> {
            if (!isInSelectMode) {
                ((MaterialCardView) view).toggle();
                itemsSelectedIndexes.add((dataItem.getId()));
                isInSelectMode = true;
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return shownDataList.size();
    }

    public void deletSelecetedItems() {
        itemsSelectedIndexes = new ArrayList<>();
        isInSelectMode = false;
    }

    public void filter(String keyWord) {
        keyWord = keyWord.toLowerCase();
        shownDataList.clear();
        if (keyWord.length() == 0) {
            shownDataList.addAll(dataList);
        } else {
            shownDataList.clear();
            for (int counter = 0; counter < dataList.size(); counter++) {
                NoteDataModel note = dataList.get(counter);
                if (note.getTitle().toLowerCase().contains(keyWord) || note.getBody().toLowerCase().contains(keyWord)) {
                    shownDataList.add(note);
                }
            }
        }
        notifyDataSetChanged();
    }

    class NoteItemViewHolder extends RecyclerView.ViewHolder {

        NoteItemLayoutBinding binding;

        public NoteItemViewHolder(@NonNull NoteItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
