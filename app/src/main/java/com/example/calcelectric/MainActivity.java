package com.example.calcelectric;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText numberElectricity, rebate;
    Button btnCalculate, btnClear;
    TextView electricityOutput, finalCostOutput;

    //Change 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberElectricity = findViewById(R.id.numberElectricity);
        rebate = findViewById(R.id.rebate);
        btnCalculate = findViewById(R.id.btnCalc);
        btnClear = findViewById(R.id.btnClear);
        electricityOutput = findViewById(R.id.electricityOutput);
        finalCostOutput = findViewById(R.id.finalCostOutput);

        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCalc) {
            String inputElectricity = numberElectricity.getText().toString();
            String inputRebate = rebate.getText().toString();

            if (TextUtils.isEmpty(inputElectricity) || TextUtils.isEmpty(inputRebate)) {
                Toast.makeText(this, "Please enter values for electricity and rebate", Toast.LENGTH_SHORT).show();
            } else {
                double electricity = Double.parseDouble(inputElectricity);
                double rebateValue = Double.parseDouble(inputRebate);

                double electricityResult = calculateElectricityBill(electricity);
                double finalCost = electricityResult - (electricityResult * (rebateValue / 100.0));

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.000");
                decimalFormat.setRoundingMode(RoundingMode.DOWN); // Set rounding mode to down

                String formattedElectricityResult = decimalFormat.format(electricityResult / 100.0);
                String formattedFinalCost = decimalFormat.format(finalCost / 100.0);

                String electricityText = getString(R.string.electricity_output, formattedElectricityResult);
                String finalCostText = getString(R.string.final_cost_output, formattedFinalCost);

                electricityOutput.setText(electricityText);
                finalCostOutput.setText(finalCostText);

                Toast.makeText(this, finalCostText, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnClear) {
            numberElectricity.setText("");
            rebate.setText("");
            electricityOutput.setText("");
            finalCostOutput.setText("");
        }
    }

    private double calculateElectricityBill(double electricity) {
        double totalCharge = 0.0;

        if (electricity <= 200) {
            totalCharge = electricity * 21.8;
        } else if (electricity <= 300) {
            totalCharge = (200 * 21.8) + ((electricity - 200) * 33.4);
        } else if (electricity <= 600) {
            totalCharge = (200 * 21.8) + (100 * 33.4) + ((electricity - 300) * 51.6);
        } else if (electricity > 600) {
            totalCharge = (200 * 21.8) + (100 * 33.4) + (300 * 51.6) + ((electricity - 600) * 54.6);
        }

        return totalCharge;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        } else if (itemId == R.id.instruction) {
            Intent instructionsIntent = new Intent(this, InstructionActivity.class);
            startActivity(instructionsIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }




}