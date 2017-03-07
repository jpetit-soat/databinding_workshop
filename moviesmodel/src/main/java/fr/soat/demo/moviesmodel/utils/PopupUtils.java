package fr.soat.demo.moviesmodel.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import fr.soat.demo.moviesmodel.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by yann_huriez on 03/02/17.
 */

public class PopupUtils {

    public static void showPopupForItemCount(Context context, View originView, int itemCount) {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.view_popup_item_count, null);

        // Initialize a new instance of popup window
        final PopupWindow popupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        // Set an elevation value for popup window
        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(5.0f);
        }

        TextView voteTextView = (TextView) customView.findViewById(R.id.popup_votes_text);
        voteTextView.setText(context.getString(R.string.format_item_count_number, itemCount));

        // Get a reference for the custom view close button
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.popup_votes_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(originView, Gravity.CENTER, 0, 0);
    }
}
