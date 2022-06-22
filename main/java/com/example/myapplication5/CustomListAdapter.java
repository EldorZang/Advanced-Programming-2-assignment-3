package com.example.myapplication5;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Contact> {
    LayoutInflater inflater;
    AppDB appDB;
    public CustomListAdapter(Context ctx, ArrayList<Contact> userArrayList,AppDB db) {
        super(ctx, R.layout.custom_list_item, userArrayList);
        appDB = db;
        this.inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Contact contact = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);
        String currUser = contact.getId();

        List<UserPicture> userPicture = appDB.UserPictureDao().getUserPicture(contact.getId());
        if (userPicture.size() != 0) {
            imageView.setImageURI(Uri.parse(userPicture.get(0).getPath()));
        }else{
            imageView.setImageResource(R.drawable.userdef);
        }
        userName.setText(contact.getName());
        lastMsg.setText(contact.getLast());
        time.setText(contact.getLastdate());

        return convertView;
    }


}
