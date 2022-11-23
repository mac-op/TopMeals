package com.example.topgmeals.mealplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.AddEditIngredientActivity;
import com.example.topgmeals.utils.DateFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class AddMealActivity extends AppCompatActivity {

    EditText mealDate;
    Spinner type;
    Spinner selection;
    EditText serving;
    Button cancel;
    Button save;
    final private Calendar myCalendar = Calendar.getInstance();
    private DateFormat format = new DateFormat();
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        setTitle("Add Meal");

        mealDate = findViewById(R.id.meal_date);
        type = findViewById(R.id.meal_type);
        selection = findViewById(R.id.meal_selection);
        serving = findViewById(R.id.meal_serving);

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


    }


    /**
     * This method updates the mealDate {@link EditText} to the date of myCalendar
     */
    private void updateLabel(){
        mealDate.setText(format.parse(myCalendar.getTime()));
    }

}