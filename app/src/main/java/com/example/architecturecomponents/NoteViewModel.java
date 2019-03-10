package com.example.architecturecomponents;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;


public class NoteViewModel extends AndroidViewModel {
    // Never store context of the activity in ViewModel because ViewModel
    // is used to outlift the activity after it is destroyed. And if hold the
    // the reference to already destroyed activity, we have a memory leak

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insert(Note note){
        noteRepository.insert(note);
    }

    public void update(Note note){
        noteRepository.update(note);
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }

    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
