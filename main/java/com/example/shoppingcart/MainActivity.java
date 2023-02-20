package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_ITEM_REQUEST_CODE = 1;

    private LinearLayout itemListLinearLayout;
    private Button clearListButton;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListLinearLayout = findViewById(R.id.itemListLinearLayout);
        clearListButton = findViewById(R.id.clearListButton);

        Button addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
            }
        });

        clearListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog("Are you sure you want to clear the list?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemListLinearLayout.removeAllViews();
                        Toast.makeText(MainActivity.this, "List Cleared", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                changeTitleText();
                return true;
            }
        });
    }

    private void showConfirmationDialog(String message, DialogInterface.OnClickListener onConfirm) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Yes", onConfirm)
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void changeTitleText() {
        setContentView(R.layout.activity_main);
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        
        final EditText editText = new EditText(this);
        editText.setText(titleTextView.getText());
        editText.setSelection(editText.getText().length());

        new AlertDialog.Builder(this)
                .setTitle("Change Title Text")
                .setMessage("Enter new title text:")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newTitleText = editText.getText().toString();
                        titleTextView.setText(newTitleText);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            String itemName = data.getStringExtra("itemName");
            int itemQuantity = data.getIntExtra("itemQuantity", 0);
            String itemMeasurement = data.getStringExtra("itemMeasurement");

            LinearLayout itemLinearLayout = new LinearLayout(this);
            itemLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            CheckBox itemCheckBox = new CheckBox(this);
            itemCheckBox.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            itemCheckBox.setTextSize(24);

            TextView itemNameTextView = new TextView(this);
            itemNameTextView.setText(itemName);
            itemNameTextView.setTextSize(24);
            itemNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            TextView itemQuantityTextView = new TextView(this);
            itemQuantityTextView.setText(Integer.toString(itemQuantity));
            itemQuantityTextView.setTextSize(24);
            itemQuantityTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView itemMeasurementTextView = new TextView(this);
            itemMeasurementTextView.setText(itemMeasurement);
            itemMeasurementTextView.setTextSize(24);
            itemMeasurementTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1));

            ImageButton removeButton = new ImageButton(this);
            removeButton.setImageResource(R.drawable.redx4);
            removeButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            removeButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmationDialog("Are you sure you want to remove this item?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            itemListLinearLayout.removeView(itemLinearLayout);
                            Toast.makeText(MainActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            itemLinearLayout.addView(itemCheckBox);
            itemLinearLayout.addView(itemNameTextView);
            itemLinearLayout.addView(itemQuantityTextView);
            itemLinearLayout.addView(itemMeasurementTextView);
            itemLinearLayout.addView(removeButton);
            itemListLinearLayout.addView(itemLinearLayout);
        }
    }
}
