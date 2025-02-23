package com.comimi.diskon;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

        // Menambahkan TextWatcher hanya untuk EditText
        nHarga.addTextChangedListener(new NumberTextWatcher(nHarga));
        nDiskon.addTextChangedListener(new NumberTextWatcher(nDiskon));
    }

    private void hitungHargaAkhir() {
        String hargaInput = nHarga.getText().toString().replaceAll("[^\\d]", ""); // Ambil angka saja
        String diskonInput = nDiskon.getText().toString().replaceAll("[^\\d]", ""); // Ambil angka saja

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
                return;
            }

            // Validasi diskon dalam rentang 0 - 100%
            if (diskon < 0 || diskon > 100) {
                txtError.setText("Diskon harus antara 0% - 100%");
                txtError.setVisibility(View.VISIBLE);
                return;
            }

            // Hitung nilai diskon dan harga akhir
            double nilaiDiskon = harga * (diskon / 100.0);
            double hargaAkhir = harga - nilaiDiskon;

            // Format angka dengan pemisah ribuan
            DecimalFormat formatter = new DecimalFormat("#,###");

            // Menampilkan hasil yang benar
            nOverviewDiskon.setText(formatter.format(nilaiDiskon));
            nOverviewHarga.setText(formatter.format(hargaAkhir));

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

    private static class NumberTextWatcher implements TextWatcher {
        private final EditText editText;

        public NumberTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) return;

            editText.removeTextChangedListener(this);

            try {
                // Menghapus karakter non-angka
                String cleanString = s.toString().replaceAll("[^\\d]", "");
                double parsed = Double.parseDouble(cleanString);

                // Format angka dengan pemisah ribuan
                NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
                String formatted = formatter.format(parsed);

                // Set hasil format kembali ke EditText
                editText.setText(formatted);
                editText.setSelection(formatted.length()); // Geser kursor ke akhir

            } catch (NumberFormatException e) {
                editText.setText("");
            }

            editText.addTextChangedListener(this);
        }
    }
}