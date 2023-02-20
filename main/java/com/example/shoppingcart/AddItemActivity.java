package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.Activity;
import android.widget.Spinner;

public class AddItemActivity extends AppCompatActivity {

    private Spinner itemMeasurementSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemMeasurementSpinner = findViewById(R.id.itemMeasurementSpinner);

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = ((EditText) findViewById(R.id.itemNameEditText)).getText().toString();
                int itemQuantity = Integer.parseInt(((EditText) findViewById(R.id.itemQuantityEditText)).getText().toString());
                String itemMeasurement = itemMeasurementSpinner.getSelectedItem().toString();

                Intent intent = new Intent();
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemQuantity", itemQuantity);
                intent.putExtra("itemMeasurement", itemMeasurement);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
