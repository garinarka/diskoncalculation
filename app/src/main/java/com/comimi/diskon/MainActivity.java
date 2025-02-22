package com.comimi.diskon;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText nHarga, nDiskon;
    private TextView nOverviewDiskon, nOverviewHarga, txtError;
    private CardView cardHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi View
        nHarga = findViewById(R.id.nHarga);
        nDiskon = findViewById(R.id.nDiskon);
        nOverviewDiskon = findViewById(R.id.nOverviewDiskon);
        nOverviewHarga = findViewById(R.id.nOverviewHarga);
        txtError = findViewById(R.id.txtError);
        cardHasil = findViewById(R.id.cardHasil);
        Button btnInput = findViewById(R.id.buttonInput);
        Button btnClear = findViewById(R.id.buttonClear);

        // Event saat tombol dihitung ditekan
        btnInput.setOnClickListener(v -> hitungHargaAkhir());

        // Event saat tombol clear ditekan
        btnClear.setOnClickListener(v -> clearInput());
    }

    private void hitungHargaAkhir() {
        String hargaInput = nHarga.getText().toString();
        String diskonInput = nDiskon.getText().toString();

        // Validasi input tidak boleh kosong
        if (TextUtils.isEmpty(hargaInput) || TextUtils.isEmpty(diskonInput)) {
            txtError.setText("Harga dan diskon tidak boleh kosong!");
            txtError.setVisibility(View.VISIBLE);
            return;
        }

        try {
            // Konversi input ke angka
            double harga = Double.parseDouble(hargaInput);
            double diskon = Double.parseDouble(diskonInput);

            // Validasi harga harus lebih dari 0
            if (harga <= 0) {
                txtError.setText("Harga harus lebih dari 0");
                txtError.setVisibility(View.VISIBLE);
                nOverviewDiskon.setText("");
                nOverviewHarga.setText("");
                return;
            }

            // Validasi diskon dalam rentang 0 - 100%
            if (diskon < 0 || diskon > 100) {
                txtError.setText("Diskon harus antara 0% - 100%");
                txtError.setVisibility(View.VISIBLE);
                nOverviewDiskon.setText("");
                nOverviewHarga.setText("");
                return;
            }

            // Hitung nilai diskon dan harga akhir
            double nilaiDiskon = (diskon / 100) * harga;
            double hargaAkhir = harga - nilaiDiskon;

            // Menampilkan hasil perhitungan
            nOverviewDiskon.setText(String.format(Locale.getDefault(), "%.2f", nilaiDiskon));
            nOverviewHarga.setText(String.format(Locale.getDefault(), "%.2f", hargaAkhir));
            // Sembunyikan error & tampilkan hasil
            txtError.setText("");
            cardHasil.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            txtError.setText("Masukkan angka yang valid!");
            txtError.setVisibility(View.VISIBLE);
        }
    }

    private void clearInput() {
        // Mengosongkan input harga dan diskon
        nHarga.setText("");
        nDiskon.setText("");

        // Sembunyikan error dan hasil perhitungan
        txtError.setText("");
        nOverviewDiskon.setText("");
        nOverviewHarga.setText("");
    }
}