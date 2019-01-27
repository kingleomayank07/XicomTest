package com.example.vishesh.xicomtest.Adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.vishesh.xicomtest.Helper.SaveImageHelper;
import com.example.vishesh.xicomtest.R;
import com.example.vishesh.xicomtest.activities.MainActivity;
import com.example.vishesh.xicomtest.model.Images;
import com.example.vishesh.xicomtest.model.Images1;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.MyViewHolder> {
    private Context context;
    private List<Images>data;
    private RequestOptions option;

    public RecyclerImageAdapter(Context context, List<Images> data) {
        this.context = context;
        this.data = data;
        option = new RequestOptions().fitCenterTransform()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_list,viewGroup,false);
        return new MyViewHolder(view,context,data);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
                Glide
                .with(context)
                .load(data.get(i).getImg_url())
                .apply(RequestOptions.fitCenterTransform())
                .into(myViewHolder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_thumbnail;
        private List<Images>images;
        private int permission_request_code=1;

        Context context;
        public MyViewHolder(@NonNull View itemView,Context context,List<Images>images) {
            super(itemView);
            this.images=images;
            this.context=context;
            itemView.setOnClickListener(this);
            img_thumbnail = itemView.findViewById(R.id.img2);
        }

        @Override
        public void onClick(View v) {
            saveImage();
            int position = getAdapterPosition();
            Images images = this.images.get(position);
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("img_id",images.getImg_url());
            this.context.startActivity(intent);

        }

        private void saveImage() {
            String filename = UUID.randomUUID().toString() + ".jpg";
            int position = getAdapterPosition();
            Picasso.get().load(images.get(position).getImg_url())
                    .into(new SaveImageHelper(context.getContentResolver(), filename, "Image"));
        }
    }
}
