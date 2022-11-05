package com.example.topgmeals.ingredient_storage;

import android.app.DatePickerDialog;
import android.content.Intent;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.utils.DateFormat;

/**
 * This is an Activity where user can add a new {@link Ingredient} or edit/delete an existing one.
 * Called by {@link IngredientStorage}
 */
public class AddEditIngredientActivity extends AppCompatActivity {
    private EditText description;
    private EditText bestBefore;
    private EditText location;
    private EditText amount;
    private Spinner unit;
    private Spinner category;
    private Button cancel;
    private Button save;

    private Intent intent;
    final private Calendar myCalendar = Calendar.getInstance();
    private DateFormat format = new DateFormat();

    //TODO: Separate classes for unit, category, location

    String[] unitArray = new String[]{"test unit1", "test unit2"};
    String[] categoryArray = new String[]{"category1", "category2"};

    /**
     * Method to handle layout of the Activity when it is created
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_ingredient);

        description = findViewById(R.id.description);
        bestBefore = findViewById(R.id.bb_date);
        location = findViewById(R.id.set_location);
        amount = findViewById(R.id.amount);
        unit = findViewById(R.id.unit);
        category = findViewById(R.id.category);
        cancel = findViewById(R.id.cancel_delete_button);
        save = findViewById(R.id.save_button);


        intent = getIntent();
        String purpose = intent.getStringExtra("purpose");              // purpose is either add or edit/delete
        int pos = intent.getIntExtra("position", -1);        // position of item to edit/delete. -1 if purpose is add

        //Set Spinners to hold values
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, unitArray);
        unit.setAdapter(unitAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categoryArray);
        category.setAdapter(categoryAdapter);

        if (purpose.equals("ADD")){
            addMenu();
        } else if (purpose.equals("EDIT")){
            editMenu(pos);
        }
    }

    /**
     * This method is invoked if the purpose of the Activity is to edit/delete an existing {@link Ingredient}
     * @param pos: position of item to edit/delete
     */
    public void editMenu(int pos) {
        setTitle("Edit Ingredient");
        cancel.setText("Delete");
        save.setText("Save");

        // Get the Ingredient from Intent and set the fields to its content
        Ingredient ingredient = intent.getParcelableExtra("ingredient_object");
        description.setText(ingredient.getDescription());
        bestBefore.setText(format.parse(ingredient.getBestBefore()));
        location.setText(ingredient.getLocation());
        amount.setText(String.valueOf(ingredient.getAmount()));
        unit.setSelection(Arrays.asList(unitArray).indexOf(ingredient.getUnit()));
        category.setSelection(Arrays.asList(categoryArray).indexOf(ingredient.getCategory()));

        /* Set button to send the position of the Ingredient back to IngredientStorage to delete */
        cancel.setOnClickListener(view -> {
            Intent deleteIntent = new Intent();
            assert (pos != -1);
            deleteIntent.putExtra("delete_position", pos);
            setResult(2, deleteIntent);
            finish();
        });

        /* Set button to send the position of the Ingredient and its new content back to IngredientStorage
         to update **/
        save.setOnClickListener(view -> {
            //TODO: Separate method to collect text??
            String description_ = description.getText().toString();
            Date bbDate_ = myCalendar.getTime();
            String location_ = location.getText().toString();
            float amount_ = Float.parseFloat(amount.getText().toString());
            String unit_ = unit.getSelectedItem().toString();
            String category_ = category.getSelectedItem().toString();

            //TODO: Input validation

            Ingredient ingredient1 = new Ingredient(description_, bbDate_, location_, amount_, unit_, category_, "TEMP");
            Intent editIntent = new Intent();
            editIntent.putExtra("edited_ingredient", ingredient1);
            editIntent.putExtra("edited_position", pos);
            setResult(RESULT_OK, editIntent);
            finish();
        });
    }

    /**
     * This method is invoked if the purpose of the Activity is to add a new {@link Ingredient} by
     * prompting user to input the content of that Ingredient
     */
    public void addMenu(){
        setTitle("Add Ingredient");
        cancel.setText("Cancel");
        save.setText("Save");

        /*
        Create a DatePicker from a calendar for the user to choose a date.
        Code adapted from https://stackoverflow.com/a/14933515
         */
        myCalendar.setTime(new Date());
        updateLabel();
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        bestBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditIngredientActivity.this,date,
                        myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // When the user wants to discard changes and go back to IngredientStorage
        cancel.setOnClickListener(view -> {
            //TODO: Ask user if they want to discard changes
            finish();
        });

        // When the user wants to save the newly created Ingredient
        save.setOnClickListener(view -> {
            String description_ = description.getText().toString();
            Date bbDate_ = myCalendar.getTime();
            String location_ = location.getText().toString();
            float amount_ = Float.parseFloat(amount.getText().toString());
            String unit_ = unit.getSelectedItem().toString();
            String category_ = category.getSelectedItem().toString();

            //TODO: Input validation

            /* Create a new Ingredient and return it to IngredientStorage to be added */
            Ingredient ingredient = new Ingredient(description_, bbDate_, location_, amount_, unit_, category_, "TEMP");
            Intent saveIntent = new Intent();
            saveIntent.putExtra("added_ingredient", ingredient);
            setResult(RESULT_OK, saveIntent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * This method updates the bestBefore {@link EditText} to the date of myCalendar
     */
    private void updateLabel(){
        bestBefore.setText(format.parse(myCalendar.getTime()));
    }

}

