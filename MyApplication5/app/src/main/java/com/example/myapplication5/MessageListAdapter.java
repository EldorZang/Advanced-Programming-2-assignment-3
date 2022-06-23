package com.example.myapplication5;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MessageListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    public MessageListAdapter(Context ctx, ArrayList<Message> userArrayList) {
        super(ctx, R.layout.message_list_item, userArrayList);
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.text_balloon);
        TextView content = convertView.findViewById(R.id.content);
        TextView time = convertView.findViewById(R.id.time);
        RelativeLayout baseView = convertView.findViewById(R.id.main_layout);
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        DisplayMetrics displayMetricsObj = convertView.getResources().getDisplayMetrics();
        if(message.isSent()){
            imageView.setImageResource(R.drawable.sent_message);
            baseView.setGravity(Gravity.LEFT);
            marginParams.setMargins(dpToPixels(-10,displayMetricsObj), 0, 0, dpToPixels(-12,displayMetricsObj));
        }
        else{
            imageView.setImageResource(R.drawable.recv_message);
            baseView.setGravity(Gravity.RIGHT);
            marginParams.setMargins(0, 0, 0, dpToPixels(-15,displayMetricsObj));
        }
        content.setText(message.getContent());
        time.setText(message.getCreated());
        return convertView;
    }

    private int dpToPixels(int dp, DisplayMetrics displayMetricsObj) {
        float output = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetricsObj);
        return Math.round(output);
    }
}
