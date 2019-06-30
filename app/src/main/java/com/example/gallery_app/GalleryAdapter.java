package com.example.gallery_app;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> imageList = new ArrayList<>();
    private Context context;
    private AddClicked addClicked;

    private static final int IMAGE = 0;
    private static final int ADD = 1;

    public GalleryAdapter(Context context) {
        this.context = context;
        addClicked = (AddClicked) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == IMAGE){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
            return new ImageViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_list_item, parent, false);
            return new AddViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if( holder instanceof ImageViewHolder){

            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;

            Glide.with(context)
                    .load(new File(imageList.get(position)))
                    .into(imageViewHolder.imageView);

            imageViewHolder.imageView.setOnLongClickListener(v -> {
                imageList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, imageList.size());
                Toast.makeText(context, "Image removed", Toast.LENGTH_SHORT).show();
                return true;
            });

        }else if(holder instanceof AddViewHolder) {

            AddViewHolder addViewHolder = (AddViewHolder) holder;
            addViewHolder.frameLayout.setOnClickListener(v -> {
               addClicked.clicked();
            });

        }


    }

    @Override
    public int getItemCount() {
        return imageList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < imageList.size()){
            return IMAGE;
        }else{
            return ADD;
        }
    }

    public void add(String s) {
        imageList.add(s);
        notifyItemInserted(imageList.size() -1 );
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }

    }

    class AddViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout frameLayout;
        public AddViewHolder(@NonNull View itemView){
            super(itemView);
            frameLayout = itemView.findViewById(R.id.fl_add);
        }
    }

}
