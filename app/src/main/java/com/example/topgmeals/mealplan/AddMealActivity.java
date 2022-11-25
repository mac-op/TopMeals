package com.example.topgmeals.mealplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.AddEditIngredientActivity;
import com.example.topgmeals.utils.DateFormat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddMealActivity extends AppCompatActivity {

    EditText mealDate;
    Spinner type;
    Spinner selection;
    EditText serving;
    Button cancel;
    Button save;
    final String[] mealTypes = {"Recipe", "Ingredient"};
    ArrayList<String> mealNames = new ArrayList<>();
    final private Calendar myCalendar = Calendar.getInstance();
    private DateFormat format = new DateFormat();
    private String userID;
    CollectionReference selectionCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setTitle("Add Meal");

        mealDate = findViewById(R.id.meal_date);
        type = findViewById(R.id.meal_type);
        selection = findViewById(R.id.meal_selection);
        serving = findViewById(R.id.meal_serving);
        cancel = findViewById(R.id.cancel_meal);
        save = findViewById(R.id.save_meal);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference mealCollection = db.collection("mealplan");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myCalendar.setTime(new Date());
        updateLabel();
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        mealDate.setOnClickListener(view -> new DatePickerDialog(AddMealActivity.this,date,
                myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());


        ArrayAdapter<String> selectionAdapter = new ArrayAdapter<>(getBaseContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mealNames);
        selectionAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        selection.setAdapter(selectionAdapter);


        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mealTypes);
        typeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    selectionCollection = db.collection("recipes");
                } else {
                    selectionCollection = db.collection("ingredients");
                }
                mealNames.clear();
                selectionCollection.whereEqualTo("id", userID).get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (queryDocumentSnapshots.isEmpty()){
                                Log.d("R", "onSuccess: document empty");
                            } else {
                                for (DocumentSnapshot doc: queryDocumentSnapshots){
                                    String name;
                                    if (i == 0){ name = (String)doc.get("title"); }
                                    else{ name = (String) doc.get("description"); }
                                    mealNames.add(name);
                                }
                                selectionAdapter.notifyDataSetChanged();

                            }
                        });

                Log.d("MealName", String.valueOf(mealNames));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });



        cancel.setOnClickListener(view -> finish());

        save.setOnClickListener(view -> {
            String date1 = mealDate.getText().toString();
            String mealName = selection.getSelectedItem().toString();
            int numServings = Integer.parseInt(serving.getText().toString());

            DocumentReference docRef = mealCollection.document();

            HashMap<String, Object> item = new HashMap<>();
            item.put("id", userID);
            item.put("date", date1);
            item.put("mealName", mealName);
            item.put("numServings", numServings);

            docRef.set(item)
                    .addOnSuccessListener(unused -> Log.d("success", "Added successfully"))
                    .addOnFailureListener(e -> Log.d("failure", "failed"));

            finish();
        });
    }


    /**
     * This method updates the mealDate {@link EditText} to the date of myCalendar
     */
    private void updateLabel(){
        mealDate.setText(format.parse(myCalendar.getTime()));
    }

}