package com.abdallahehab.todo;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdallahehab.todo.databinding.NoteCardItemBinding;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteItemViewHolder>{

    private ArrayList<NoteDataModel> dataList;

    public NoteAdapter(ArrayList<NoteDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NoteCardItemBinding binding = NoteCardItemBinding.inflate(inflater,parent,false);
        return new  NoteItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class NoteItemViewHolder extends RecyclerView.ViewHolder{

        private NoteCardItemBinding binding;

        public NoteItemViewHolder(@NonNull NoteCardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NoteDataModel noteDataModel) {
            binding.userName.setText(noteDataModel.getTitle());
            binding.userNameLabel.setText(noteDataModel.getBody());
            binding.phoneLabel.setText(noteDataModel.getDate());
        }
    }
}
