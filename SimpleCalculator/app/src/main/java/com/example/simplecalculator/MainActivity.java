package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etBilanganSatu, etBilanganDua;
    private Button btnPlus, btnMinus, btnMultiply, btnDivide;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("KALKULATOR SEDERHANA");

        etBilanganSatu = (EditText)findViewById(R.id.etBilanganSatu);
        etBilanganDua = (EditText)findViewById(R.id.etBilanganDua);

        tvHasil = (TextView)findViewById(R.id.tvHasil);

        View.OnClickListener operasi = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float num1, num2, result = 0;
                Button bt;

                num1 = Float.parseFloat(etBilanganSatu.getText().toString());
                num2 = Float.parseFloat(etBilanganDua.getText().toString());

                switch (view.getId()){
                    case R.id.btnPlus: result = num1 + num2; break;
                    case R.id.btnMinus: result = num1 - num2; break;
                    case R.id.btnMultiple: result = num1 * num2; break;
                    case R.id.btnDivide: result = num1 / num2; break;
                }

                bt = (Button) findViewById(view.getId());

                tvHasil.setText(num1 + " " + bt.getText() + " " + num2 + " = " + result);

            }
        };

        btnPlus = (Button)findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(operasi);
        btnMinus = (Button)findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(operasi);
        btnMultiply = (Button)findViewById(R.id.btnMultiple);
        btnMultiply.setOnClickListener(operasi);
        btnDivide = (Button)findViewById(R.id.btnDivide);
        btnDivide.setOnClickListener(operasi);
    }
}