package com.yehigo.eagle.voiceapp;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yehigo.eagle.voiceapp.DBMS.Words;

import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Words> mDataset;
    private  TextToSpeech tts=null;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        final View mView;
        public final TextView mTextView;
        public final ImageButton mImageButton;
        public MyViewHolder(View v) {
            super(v);
            mView = v;
            mTextView = mView.findViewById(R.id.text);
            mImageButton = mView.findViewById(R.id.play);
        }


    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Words> myDataset) {
        mDataset = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        tts =  new TextToSpeech(parent.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR)tts.setLanguage(Locale.getDefault());
            }
        });

       return new MyViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // Log.d("FFFFFf3333",mDataset.get(position).word);
        holder.mTextView.setText(mDataset.get(position).word);



        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tts.speak(holder.mTextView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null,getClass().getName());

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
       // Log.d("FFFFFf3333",""+mDataset.size());
        return mDataset.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if(tts !=null) tts.shutdown();
        tts=null;
    }
}