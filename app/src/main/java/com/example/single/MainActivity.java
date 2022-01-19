package com.example.single;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class BMICalcUtil {
    public static final BMICalcUtil instance = new BMICalcUtil();
    public static final BMICalcUtil save = new BMICalcUtil();

    private static final int CENTIMETERS_IN_METER = 100;

    public static final String BMI_CATEGORY_UNDERWEIGHT = "BMI Category: Underweight";
    public static final String BMI_CATEGORY_HEALTHY = "BMI Category: Normal Weight";
    public static final String BMI_CATEGORY_OVERWEIGHT = "BMI Category: Overweight";
    public static final String BMI_CATEGORY_MODERATELY_OBESE = "BMI Category: Moderately Obese";
    public static final String BMI_CATEGORY_SEVERELY_OBESE = "BMI Category: Severely Obese";
    public static final String BMI_CATEGORY_VERYSEVERELY_OBESE = "BMI Category: Very Severely Obese";

    public static final String BMI_RANGE_UNDERWEIGHT = "BMI Range: 18.4 and below";
    public static final String BMI_RANGE_HEALTHY = "BMI Range: 18.5 - 24.9";
    public static final String BMI_RANGE_OVERWEIGHT = "BMI Range: 25 - 29.9";
    public static final String BMI_RANGE_MODERATELY_OBESE = "BMI Range: 30 - 34.9";
    public static final String BMI_RANGE_SEVERELY_OBESE = "BMI Range: 35 - 39.9";
    public static final String BMI_RANGE_VERYSEVERELY_OBESE = "BMI Range: 40 and above";

    public static final String BMI_RISK_UNDERWEIGHT = "Health Risk: Malnutrition Risk";
    public static final String BMI_RISK_HEALTHY = "Health Risk: Low Risk";
    public static final String BMI_RISK_OVERWEIGHT = "Health Risk: Enchanced Risk";
    public static final String BMI_RISK_MODERATELY_OBESE = "Health Risk: Medium Risk";
    public static final String BMI_RISK_SEVERELY_OBESE = "Health Risk: High Risk";
    public static final String BMI_RISK_VERYSEVERELY_OBESE = "Health Risk: Very High Risk";

    public static BMICalcUtil getInstance() {
        return instance;
    }
    public static BMICalcUtil getSave() {
        return save;
    }

    public double calculateBMIMetric(double heightCm, double weightKg) {
        return (weightKg / ((heightCm / CENTIMETERS_IN_METER) * (heightCm / CENTIMETERS_IN_METER)));
    }

    public double calculateBMIImperial(double heightM, double weightKgs) {
        return (weightKgs / (heightM * heightM));
    }



    public String classifyBMI(double bmi) {
        if (bmi < 18.5) {
            return BMI_CATEGORY_UNDERWEIGHT;
        } else if (bmi >= 18.5 && bmi < 25) {
            return BMI_CATEGORY_HEALTHY;
        } else if (bmi >= 25 && bmi < 30){
            return BMI_CATEGORY_OVERWEIGHT;
        } else if (bmi >= 30 && bmi < 35){
            return BMI_CATEGORY_MODERATELY_OBESE;
        }
          else if (bmi >= 35 && bmi < 40){
            return BMI_CATEGORY_SEVERELY_OBESE;
        }
          else{
              return BMI_CATEGORY_VERYSEVERELY_OBESE;
        }
    }

    public String rangeBMI(double bmi) {
        if (bmi < 18.5) {
            return BMI_RANGE_UNDERWEIGHT;
        } else if (bmi >= 18.5 && bmi < 25) {
            return BMI_RANGE_HEALTHY;
        } else if (bmi >= 25 && bmi < 30){
            return BMI_RANGE_OVERWEIGHT;
        } else if (bmi >= 30 && bmi < 35){
            return BMI_RANGE_MODERATELY_OBESE;
        }
        else if (bmi >= 35 && bmi < 40){
            return BMI_RANGE_SEVERELY_OBESE;
        }
        else{
            return BMI_RANGE_VERYSEVERELY_OBESE;
        }
    }

    public String healthBMI(double bmi) {
        if (bmi < 18.5) {
            return BMI_RISK_UNDERWEIGHT;
        } else if (bmi >= 18.5 && bmi < 25) {
            return BMI_RISK_HEALTHY;
        } else if (bmi >= 25 && bmi < 30){
            return BMI_RISK_OVERWEIGHT;
        } else if (bmi >= 30 && bmi < 35){
            return BMI_RISK_MODERATELY_OBESE;
        }
        else if (bmi >= 35 && bmi < 40){
            return BMI_RISK_SEVERELY_OBESE;
        }
        else{
            return BMI_RISK_VERYSEVERELY_OBESE;
        }
    }
}

public class MainActivity extends AppCompatActivity {
    private EditText weightKgEditText, heightCmEditText;
    private EditText weightKgsEditText, heightMEditText;
    private Button calculateButton;
    private TextView bmiTextView, categoryTextView;
    private ToggleButton toggleUnitsButton;
    private CardView bmiResultCardView;
    private TextView riskTextView;
    private TextView rangeTextView;
    private static final String FILE_NAME1 = "reuseDataWeight.txt";
    private static final String FILE_NAME2 = "reuseDataHeight.txt";
    private static final String FILE_NAME3 = "reuseDataWeight2.txt";
    private static final String FILE_NAME4 = "reuseDataHeight2.txt";

    private boolean inMetricUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightKgEditText = findViewById(R.id.activity_main_weightkgs);
        heightCmEditText = findViewById(R.id.activity_main_heightcm);

        weightKgsEditText = findViewById(R.id.activity_main_weightkg);
        heightMEditText = findViewById(R.id.activity_main_heightmeters);

        calculateButton = findViewById(R.id.activity_main_calculate);
        toggleUnitsButton = findViewById(R.id.activity_main_toggleunits);

        bmiTextView = findViewById(R.id.activity_main_bmi);
        categoryTextView = findViewById(R.id.activity_main_category);
        bmiResultCardView = findViewById(R.id.activity_main_resultcard);
        riskTextView = findViewById(R.id.activity_main_risk);
        rangeTextView = findViewById(R.id.activity_main_range);

        inMetricUnits = true;
        updateInputsVisibility();
        bmiResultCardView.setVisibility(View.GONE);



        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inMetricUnits) {
                    if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0) {
                        Toast.makeText(MainActivity.this, "Key In Your Weight and Height to Calculate BMI", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                        double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                        double bmi = BMICalcUtil.getInstance().calculateBMIMetric(heightInCms, weightInKgs);
                        saveFile();
                        displayBMI(bmi);
                    }
                } else {
                    if (weightKgsEditText.length() == 0 || heightMEditText.length() == 0) {
                        Toast.makeText(MainActivity.this, "Key In Your Weight and Height to Calculate BMI", Toast.LENGTH_SHORT).show();
                    } else {
                        double heightM = Double.parseDouble(heightMEditText.getText().toString());
                        double weightKgs = Double.parseDouble(weightKgsEditText.getText().toString());
                        double bmi = BMICalcUtil.getInstance().calculateBMIImperial(heightM, weightKgs);
                        saveFile();
                        displayBMI(bmi);
                    }
                }
            }
        });

        toggleUnitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inMetricUnits = !inMetricUnits;
                updateInputsVisibility();
            }
        });
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;

        if (weightKgEditText != null){
            try {
                fis1 = openFileInput(FILE_NAME1);
                InputStreamReader isr = new InputStreamReader(fis1);
                BufferedReader br = new BufferedReader(isr);
                String txt;

                while((txt = br.readLine()) != null){
                    weightKgEditText.setText(txt);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis1 != null){
                    try {
                        fis1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                fis2 = openFileInput(FILE_NAME2);
                InputStreamReader isr = new InputStreamReader(fis2);
                BufferedReader br = new BufferedReader(isr);
                String txt;

                while((txt = br.readLine()) != null){
                    heightCmEditText.setText(txt);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis2 != null){
                    try {
                        fis2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (weightKgsEditText != null){
            try {
                fis1 = openFileInput(FILE_NAME3);
                InputStreamReader isr = new InputStreamReader(fis1);
                BufferedReader br = new BufferedReader(isr);
                String txt;

                while((txt = br.readLine()) != null){
                    weightKgsEditText.setText(txt);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis1 != null){
                    try {
                        fis1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                fis2 = openFileInput(FILE_NAME4);
                InputStreamReader isr = new InputStreamReader(fis2);
                BufferedReader br = new BufferedReader(isr);
                String txt;

                while((txt = br.readLine()) != null){
                    heightMEditText.setText(txt);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis2 != null){
                    try {
                        fis2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }

    public void aboutPage(View view){
        Intent intent = new Intent(this, about_us.class);
        startActivity(intent);
    }

    public void saveFile(){
        if (weightKgEditText != null){
            String txt1 = weightKgEditText.getText().toString();
            String txt2 = heightCmEditText.getText().toString();
            FileOutputStream fos1 = null;
            try {
                fos1 = openFileOutput(FILE_NAME1, MODE_PRIVATE);
                fos1.write(txt1.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos1!=null){
                    try {
                        fos1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            FileOutputStream fos2 = null;
            try {
                fos2 = openFileOutput(FILE_NAME2, MODE_PRIVATE);
                fos2.write(txt2.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos2!=null){
                    try {
                        fos2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (weightKgsEditText != null){
            String txt1 = weightKgsEditText.getText().toString();
            String txt2 = heightMEditText.getText().toString();
            FileOutputStream fos1 = null;
            try {
                fos1 = openFileOutput(FILE_NAME3, MODE_PRIVATE);
                fos1.write(txt1.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos1!=null){
                    try {
                        fos1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            FileOutputStream fos2 = null;
            try {
                fos2 = openFileOutput(FILE_NAME4, MODE_PRIVATE);
                fos2.write(txt2.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos2!=null){
                    try {
                        fos2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }



    private void updateInputsVisibility() {
        if (inMetricUnits) {
            heightCmEditText.setVisibility(View.VISIBLE);
            weightKgEditText.setVisibility(View.VISIBLE);
            heightMEditText.setVisibility(View.GONE);
            weightKgsEditText.setVisibility(View.GONE);
        } else {
            heightCmEditText.setVisibility(View.GONE);
            weightKgEditText.setVisibility(View.GONE);
            heightMEditText.setVisibility(View.VISIBLE);
            weightKgsEditText.setVisibility(View.VISIBLE);
        }
    }

    private void displayBMI(double bmi) {
        bmiResultCardView.setVisibility(View.VISIBLE);

        bmiTextView.setText(String.format("%.2f", bmi));

        String bmiCategory = BMICalcUtil.getInstance().classifyBMI(bmi);
        categoryTextView.setText(bmiCategory);

        String bmiRange = BMICalcUtil.getInstance().rangeBMI(bmi);
        rangeTextView.setText(bmiRange);

        String bmiRisk = BMICalcUtil.getInstance().healthBMI(bmi);
        riskTextView.setText(bmiRisk);



        switch (bmiCategory) {
            case BMICalcUtil.BMI_CATEGORY_UNDERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                break;
            case BMICalcUtil.BMI_CATEGORY_HEALTHY:
                bmiResultCardView.setCardBackgroundColor(Color.GREEN);
                break;
            case BMICalcUtil.BMI_CATEGORY_OVERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                break;
            case BMICalcUtil.BMI_CATEGORY_MODERATELY_OBESE:
                bmiResultCardView.setCardBackgroundColor(Color.RED);
                break;
            case BMICalcUtil.BMI_CATEGORY_SEVERELY_OBESE:
                bmiResultCardView.setCardBackgroundColor(Color.RED);
                break;
            case BMICalcUtil.BMI_CATEGORY_VERYSEVERELY_OBESE:
                bmiResultCardView.setCardBackgroundColor(Color.RED);
                break;
        }
    }
}

