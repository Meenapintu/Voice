package com.yehigo.eagle.voiceapp.DBMS;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;


public class AsyncDB {
     AppDatabase  _db = null;
    Context _context;

    public  AsyncDB(Context context)
    {
        _context=context;
    }

    public AppDatabase get_db() {
        if (_db == null)
            _db = Room.databaseBuilder(_context, AppDatabase.class, "database-name").build();
        return _db;
    }

    public void insert(Words... words)
    {
        (new AsyncDB_helper()).execute(words);
    }

    public void getALL(AsyncDBListener asyncDBListener)
    {
            (new AsyncDB_helper_get()).execute(asyncDBListener);
    }


    private class AsyncDB_helper extends AsyncTask<Words, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected String doInBackground(Words... words) {
            int count = words.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                get_db().speechData().insert(words[i]);

            }
            return words[0].word;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(String result) {

        }
    }


    private class AsyncDB_helper_get extends AsyncTask<AsyncDBListener, Integer, Object> {

        AsyncDBListener _listener;
        protected Object doInBackground(AsyncDBListener... listener) {
            _listener = listener[0];

              return   get_db().speechData().getAll();
        }

        protected void onProgressUpdate(Integer... progress) {

        }


        protected void onPostExecute(Object result) {
            _listener.OnSuccess(result);

        }
    }
}