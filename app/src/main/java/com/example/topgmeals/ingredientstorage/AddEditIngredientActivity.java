package com.example.topgmeals.ingredientstorage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.utils.DateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * This is an Activity where user can add a new {@link Ingredient} or edit/delete an existing one.
 * Called by {@link IngredientStorage}
 */
public class AddEditIngredientActivity extends AppCompatActivity {
    private EditText description;
    private EditText bestBefore;
    private EditText location;
    private EditText amount;
    private EditText unit;
    private EditText category;
    private Button cancel;
    private Button save;

    private Intent intent;
    final private Calendar myCalendar = Calendar.getInstance();
    private DateFormat format = new DateFormat();

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
        // purpose is either add or edit/delete
        String purpose = intent.getStringExtra("purpose");

        if (purpose.equals("ADD")){
            addMenu();
        } else if (purpose.equals("EDIT")){
            editMenu();
        } else if (purpose.equals("UPDATE")){
            updateMenu();
        }
    }

    public void updateMenu() {
        setTitle("Update Ingredient Storage");
        cancel.setText("Delete");
        save.setText("Update");

        // Get the Ingredient from Intent and set the fields to its content
        Ingredient ingredient = intent.getParcelableExtra("ingredient_object");
        description.setText(ingredient.getDescription());
        bestBefore.setText(format.parse(ingredient.getBestBefore()));
        location.setText(ingredient.getLocation());
        amount.setText(String.valueOf(ingredient.getAmount()));
        unit.setText(ingredient.getUnit());
        category.setText(ingredient.getCategory());

        /* Set button to send the position of the Ingredient back to IngredientStorage to delete */
        cancel.setOnClickListener(view -> {
            AlertDialog.Builder cancelDialog =
                    new AlertDialog.Builder(AddEditIngredientActivity.this);
            cancelDialog.setMessage("Do you want to remove this ingredient from the Shopping " +
                            "List?.").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String description_ = description.getText().toString();
                            Date bbDate_ = myCalendar.getTime();
                            String location_ = location.getText().toString();
                            float amount_ = Float.parseFloat(amount.getText().toString());
                            String unit_ = unit.getText().toString();
                            String category_ = category.getText().toString();

                            Intent deleteIntent = new Intent();

                            Ingredient ingredient1 = new Ingredient(description_, bbDate_,
                                    location_, amount_, unit_, category_, "TEMP");
                            deleteIntent.putExtra("edited_ingredient", ingredient1);
                            setResult(2, deleteIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertCancel = cancelDialog.create();
            alertCancel.setTitle("Delete Ingredient");
            alertCancel.show();
        });

        /* Set button to send the position of the Ingredient and its new content back to
           IngredientStorage to update **/
        save.setOnClickListener(view -> {
            String description_ = description.getText().toString();
            Date bbDate_ = myCalendar.getTime();
            String location_ = location.getText().toString();
            float amount_ = Float.parseFloat(amount.getText().toString());
            String unit_ = unit.getText().toString();
            String category_ = category.getText().toString();

            Ingredient ingredient1 = new Ingredient(description_, bbDate_, location_, amount_,
                    unit_, category_, "TEMP");
            Intent editIntent = new Intent();
            editIntent.putExtra("edited_ingredient", ingredient1);
            setResult(RESULT_OK, editIntent);
            finish();
        });
    }

    /**
     * This method is invoked if the purpose of the Activity is to edit/delete an existing
     * {@link Ingredient}
     */
    public void editMenu() {
        setTitle("Edit Ingredient");
        cancel.setText("Delete");
        save.setText("Save");

        // Get the Ingredient from Intent and set the fields to its content
        Ingredient ingredient = intent.getParcelableExtra("ingredient_object");
        description.setText(ingredient.getDescription());
        bestBefore.setText(format.parse(ingredient.getBestBefore()));
        location.setText(ingredient.getLocation());
        amount.setText(String.valueOf(ingredient.getAmount()));
        unit.setText(ingredient.getUnit());
        category.setText(ingredient.getCategory());

        myCalendar.setTime(ingredient.getBestBefore());
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

        /* Set button to send the position of the Ingredient back to IngredientStorage to delete */
        cancel.setOnClickListener(view -> {
            AlertDialog.Builder cancelDialog =
                    new AlertDialog.Builder(AddEditIngredientActivity.this);
            cancelDialog.setMessage("Are you sure you want to delete this " +
                            "ingredient?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent deleteIntent = new Intent();
                            deleteIntent.putExtra("deleted_ref", ingredient.getDocumentID());
                            setResult(2, deleteIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertCancel = cancelDialog.create();
            alertCancel.setTitle("Delete Confirmation");
            alertCancel.show();
        });

        /* Set button to send the position of the Ingredient and its new content back to
           IngredientStorage to update **/
        save.setOnClickListener(view -> {
            String description_ = description.getText().toString();
            Date bbDate_ = myCalendar.getTime();
            String location_ = location.getText().toString();
            String unit_ = unit.getText().toString();
            String category_ = category.getText().toString();

            // Description validation
            if ((description_.trim()).isEmpty()) {
                description.setError("Description is required!");
                description.requestFocus();
                return;
            }

            // Location validation
            if ((location_.trim()).isEmpty()) {
                location.setError("Location is required!");
                location.requestFocus();
                return;
            }

            // Amount validation and setting its value
            float amount_;
            if ((amount.getText().toString().trim()).isEmpty()) {
                amount.setError("Amount is required!");
                amount.requestFocus();
                return;
            } else if (Float.parseFloat(amount.getText().toString()) == 0) {
                amount.setError("Amount cannot be 0!");
                amount.requestFocus();
                return;
            } else {
                amount_ = Float.parseFloat(amount.getText().toString());
            }

            // Unit validation
            if ((unit_.trim()).isEmpty()) {
                unit.setError("Unit is required!");
                unit.requestFocus();
                return;
            }

            // Category validation
            if ((category_.trim()).isEmpty()) {
                category.setError("Category is required!");
                category.requestFocus();
                return;
            }

            Ingredient ingredient1 = new Ingredient(description_, bbDate_, location_, amount_,
                    unit_, category_, ingredient.getDocumentID());
            Intent editIntent = new Intent();
            editIntent.putExtra("edited_ingredient", ingredient1);
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
            AlertDialog.Builder cancelDialog =
                    new AlertDialog.Builder(AddEditIngredientActivity.this);
            cancelDialog.setMessage("Do you want to discard changes and return to " +
                            "Ingredient Storage?").setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
            AlertDialog alertCancel = cancelDialog.create();
            alertCancel.setTitle("Discard Changes");
            alertCancel.show();
        });

        // When the user wants to save the newly created Ingredient
        save.setOnClickListener(view -> {
            String description_ = description.getText().toString();
            Date bbDate_ = myCalendar.getTime();
            String location_ = location.getText().toString();
            String unit_ = unit.getText().toString();
            String category_ = category.getText().toString();

            // Description validation
            if ((description_.trim()).isEmpty()) {
                description.setError("Description is required!");
                description.requestFocus();
                return;
            }

            // Location validation
            if ((location_.trim()).isEmpty()) {
                location.setError("Location is required!");
                location.requestFocus();
                return;
            }

            // Amount validation and setting its value
            float amount_;
            if ((amount.getText().toString().trim()).isEmpty()) {
                amount.setError("Amount is required!");
                amount.requestFocus();
                return;
            } else if (Float.parseFloat(amount.getText().toString()) == 0) {
                amount.setError("Amount cannot be 0!");
                amount.requestFocus();
                return;
            } else {
                amount_ = Float.parseFloat(amount.getText().toString());
            }

            // Unit validation
            if ((unit_.trim()).isEmpty()) {
                unit.setError("Unit is required!");
                unit.requestFocus();
                return;
            }

            // Category validation
            if ((category_.trim()).isEmpty()) {
                category.setError("Category is required!");
                category.requestFocus();
                return;
            }

            /* Create a new Ingredient and return it to IngredientStorage to be added */
            Ingredient ingredient = new Ingredient(description_, bbDate_, location_, amount_, unit_,
                    category_, "TEMP");
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