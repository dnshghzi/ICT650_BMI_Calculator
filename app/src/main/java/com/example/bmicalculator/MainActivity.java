package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText weight, height;
    Button btnCalculate, btnReset;
    TextView bmiCategory, bmi, healthRisk;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "height";
    public static final String TEXT2 = "weight";
    private String heightString, weightString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);

        btnCalculate = (Button) findViewById(R.id.calculate);
        btnReset = (Button) findViewById(R.id.reset);
        bmi = (TextView) findViewById(R.id.result);
        bmiCategory = (TextView) findViewById(R.id.bmiCategory);
        healthRisk = (TextView) findViewById(R.id.healthRisk);

        btnCalculate.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        loadData();
        updateViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calculate:

                try {
                    saveData();
                    double weightValue = Double.parseDouble(weight.getText().toString());
                    double heightValue = Double.parseDouble(height.getText().toString());
                    double heightMeter = heightValue / 100;

                    double bmiValue = weightValue / (heightMeter * heightMeter);
                    String result = String.format("%.2f", bmiValue); //Change to 2 Decimal Places

                    if (bmiValue <= 18.4) {
                        bmi.setText(result + " kg/m²");
                        bmiCategory.setText("Underweight");
                        healthRisk.setText("Malnutrition Risk");
                    } else if (bmiValue > 18.4 && bmiValue <= 24.9) {
                        bmi.setText(result + " kg/m²");
                        bmiCategory.setText("Normal Weight");
                        healthRisk.setText("Low Risk");
                    } else if (bmiValue > 24.9 && bmiValue <= 29.9) {
                        bmi.setText(result + " kg/m²");
                        bmiCategory.setText("Overweight");
                        healthRisk.setText("Enhanced Risk");
                    } else if (bmiValue > 29.9 && bmiValue <= 34.9) {
                        bmi.setText(result + " kg/m²");
                        bmiCategory.setText("Moderately Obese");
                        healthRisk.setText("Medium Risk");
                    } else if (bmiValue > 34.9 && bmiValue <= 39.9) {
                        bmi.setText(result + " kg/m²");
                        bmiCategory.setText("Severely Obese");
                        healthRisk.setText("High Risk");
                    } else {
                        bmi.setText(result + " kg/m²");
                        bmiCategory.setText("Very Severely Obese");
                        healthRisk.setText("Very High Risk");
                    }
                }
                catch (java.lang.NumberFormatException nfe) {
                    Toast.makeText(this, "Please Enter A Valid Number", Toast.LENGTH_SHORT).show();
                }
                catch (Exception exp) {
                    Toast.makeText(this, "Please Enter A Valid Number", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.reset:
                height.setText("");
                weight.setText("");

                break;

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, aboutPage.class);
                startActivity(intent);
                break;
            case R.id.bmicalc:
                Toast.makeText(this, "This is BMI Calculator", Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, height.getText().toString());
        editor.putString(TEXT2, weight.getText().toString());

        //editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        heightString = String.valueOf(height);
        heightString = sharedPreferences.getString(TEXT, "");
        weightString = String.valueOf(weight);
        weightString = sharedPreferences.getString(TEXT2, "");
    }

    public void updateViews() {
        height.setText(heightString);
        weight.setText(weightString);
    }
}


