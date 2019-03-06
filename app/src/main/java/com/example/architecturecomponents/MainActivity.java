package com.example.architecturecomponents;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // We do not call new NoteViewModel because then we will have create new instance
        // with every activity. Instead we ask the Android system to create it for us.
        // Because the system knows when to create a new instance and provide and
        // already existing for us.

        noteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // update RecyclerView
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
