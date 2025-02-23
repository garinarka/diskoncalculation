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

        // Menambahkan TextWatcher untuk nHarga
        nHarga.addTextChangedListener(new NumberTextWatcher(nHarga));
        // Menambahkan TextWatcher untuk nOverviewDiskon
        nOverviewDiskon.addTextChangedListener(new NumberTextWatcher(nOverviewDiskon));
        // Menambahkan TextWatcher untuk nOverviewHarga
        nOverviewHarga.addTextChangedListener(new NumberTextWatcher(nOverviewHarga));
    }

    private void hitungHargaAkhir() {
        String hargaInput = nHarga.getText().toString().replaceAll("\\D", ""); // Menghapus format
        String diskonInput = nDiskon.getText().toString().replaceAll("\\D", ""); // Menghapus format

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

            // Menampilkan hasil perhitungan dengan format pemisah ribuan
            nOverviewDiskon.setText(String.valueOf(nilaiDiskon));
            nOverviewHarga.setText(String.valueOf(hargaAkhir));
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

    private static class NumberTextWatcher implements TextWatcher {
        private final TextView textView;

        public NumberTextWatcher(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Tidak perlu melakukan apa-apa di sini
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Tidak perlu melakukan apa-apa di sini
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) return;

            // Menghapus listener untuk mencegah loop tak terbatas
            textView.removeTextChangedListener(this);

            // Mengonversi string ke angka
            String input = s.toString().replaceAll("\\D", "");
            if (!input.isEmpty()) {
                double parsed = Double.parseDouble(input);
                // Memformat angka dengan pemisah ribuan
                String formatted = NumberFormat.getInstance(Locale.getDefault()).format(parsed);
                // Mengatur teks yang diformat ke TextView
                textView.setText(formatted);
            }

            // Menambahkan kembali listener
            textView.addTextChangedListener(this);
        }
    }
}