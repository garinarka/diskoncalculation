package com.comimi.diskon;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button check, clear;
    EditText hargaInput, diskonInput;
    TextView nHarga, nDiskon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check = findViewById(R.id.buttonInput);
        clear = findViewById(R.id.buttonClear);
        hargaInput = findViewById(R.id.nHarga);
        diskonInput = findViewById(R.id.nDiskon);
        nHarga = findViewById(R.id.nOverviewHarga);
        nDiskon = findViewById(R.id.nOverviewDiskon);

        check.setOnClickListener(view -> totalHarga());

        clear.setOnClickListener(view -> clearHistory());
    }

    public void totalHarga() {
        String hargaStr = hargaInput.getText().toString();
        String diskonStr = diskonInput.getText().toString();

        if (hargaStr.isEmpty() || diskonStr.isEmpty()) {
            Toast.makeText(this, "Harga dan Diskon tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            double diskon = Double.parseDouble(diskonStr);

            if (harga <= 0) {
                Toast.makeText(this, "Harga harus lebih besar dari 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (diskon < 0 || diskon > 100) {
                Toast.makeText(this, "Diskon harus antara 0 dan 100", Toast.LENGTH_SHORT).show();
                return;
            }

            double diskonHarga = harga * diskon / 100;
            double totHarga = harga - (diskonHarga);

            nHarga.setText("Rp. " + diskonHarga);
            nDiskon.setText("Rp. " + totHarga);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearHistory() {
        hargaInput.setText("");
        diskonInput.setText("");
        nHarga.setText("Rp.");
        nDiskon.setText("Rp.");
    }
}
