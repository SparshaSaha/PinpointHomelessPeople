package com.fourthstatelab.pinpointhomelesspeople;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by sparsha on 15/4/17.
 */

public class MyAsyncTask extends AsyncTask<Void,Void,Void> {
    String link;
    Bitmap bitmap;

    MyAsyncTask(String url)
    {
        link=url;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReference().child("images/");
        httpsReference.getBytes(1024*100).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
    }
}
