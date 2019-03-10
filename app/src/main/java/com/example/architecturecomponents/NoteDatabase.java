package com.example.architecturecomponents;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

// Want to create "singleton" Database

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    // Room automatically generates code for this.
    // So all you have to do is call this.
    public abstract NoteDao noteDao();

    // Synchronized make sures that only one thread can access it at one time.
    // So in multi threading environment, multiple threads cannot access the database at same time.
    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(room_callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback room_callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }

    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private PopulateDBAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
