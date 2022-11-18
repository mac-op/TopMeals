package com.example.topgmeals.recipebook;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.topgmeals.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Recipe Adapter to fit {@link Recipe} objects into {@link ArrayAdapter}
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private static final String TAG = "RecipeListAdapter";
    private Context context;
    private int resource;

    private StorageReference mStorageRef;


    /**
     * Constructor for {@link RecipeAdapter}
     */
    public RecipeAdapter(@NonNull Context context, int resource, @NonNull List<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * A {@link View} class to get the view of an {@link Recipe} object
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String title = getItem(position).getTitle();
        String prep_time = getItem(position).getPrepTime();
        Integer servings = getItem(position).getServings();
        String category = getItem(position).getCategory();
        String recID = getItem(position).getDocumentID();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView title_display = (TextView) convertView.findViewById(R.id.recipe_title_id);
        TextView prep_time_display = (TextView) convertView.findViewById(R.id.prep_time_id);
        TextView servings_display = (TextView) convertView.findViewById(R.id.serving_id);
        TextView category_display = (TextView) convertView.findViewById(R.id.category_id);

        ImageView recImg = (ImageView) convertView.findViewById(R.id.recipeBookImage);

        Log.e("TTT", "uploads/" + recID);

//        try {
//            Log.e("e", mStorageRef.child("uploads/" + recID).getDownloadUrl().getResult().toString());
//            Glide.with(context).load(mStorageRef.child("uploads/" + recID).getDownloadUrl()).into(recImg);
//            Log.e("s", "SUCC");
//        }
//        catch (Exception ex){
//
//        }
//        final Uri[] temp = {null};
        mStorageRef.child("uploads/" + recID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                Log.e("madeit", uri.toString());
                //temp[0] = uri;
                Glide.with(context).load(uri.toString()).into(recImg);
                //title_display.setText(title.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        // recImg.setImageURI(temp[0]);

        title_display.setText(title.toString());
        prep_time_display.setText(prep_time.toString());
        servings_display.setText(servings.toString());
        category_display.setText(category);

        return convertView;
    }
}

