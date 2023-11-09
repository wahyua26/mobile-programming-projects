package com.example.penjualan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText NamaPembeli, NamaBarang, JumlahBarang, HargaBarang, UangBayar;
    private Button btnProses, btnHapus, btnKeluar;
    private TextView Pembeli, Barang, Jumlah, Harga, Bayar, Total, Kembali, Bonus, Keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Aplikasi Penjualan");

        NamaPembeli = (EditText)findViewById(R.id.etNamaPelanggan);
        NamaBarang = (EditText)findViewById(R.id.etNamaBarang);
        JumlahBarang = (EditText)findViewById(R.id.etJmlBarang);
        HargaBarang = (EditText)findViewById(R.id.etHarga);
        UangBayar = (EditText)findViewById(R.id.etUangBayar);

        btnProses = (Button)findViewById(R.id.btnProses);
        btnHapus = (Button)findViewById(R.id.btnHapus);
        btnKeluar = (Button)findViewById(R.id.btnKeluar);

        Pembeli = (TextView)findViewById(R.id.tvNamaPembeli);
        Barang = (TextView)findViewById(R.id.tvNamaBarang);
        Jumlah = (TextView)findViewById(R.id.tvJumlahBarang);
        Harga = (TextView)findViewById(R.id.tvHarga);
        Bayar = (TextView)findViewById(R.id.tvUangBayar);
        Total = (TextView)findViewById(R.id.tvTotalBelanja);
        Kembali = (TextView)findViewById(R.id.tvUangKembalian);
        Bonus = (TextView)findViewById(R.id.tvBonus);
        Keterangan = (TextView)findViewById(R.id.tvKeterangan);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double tempJumlah = Double.parseDouble(JumlahBarang.getText().toString().trim());
                double tempHarga = Double.parseDouble(HargaBarang.getText().toString().trim());
                double total = tempJumlah * tempHarga;

                double tempBayar = Double.parseDouble(UangBayar.getText().toString().trim());
                double kembali = tempBayar - total;

                Pembeli.setText("Nama Pembeli : " + NamaPembeli.getText());
                Barang.setText("Nama Barang : " + NamaBarang.getText());
                Jumlah.setText("Jumlah Barang : " + JumlahBarang.getText());
                Harga.setText("Harga Barang : Rp. " + HargaBarang.getText());
                Bayar.setText("Uang Bayar : Rp. " + UangBayar.getText());
                Total.setText("Total Belanja : Rp. " + total);
                Kembali.setText("Uang Kembalian : Rp. " + kembali);
                Bonus.setText("Bonus : HardDisk 1 TB");
                Keterangan.setText("Keterangan : Tunggu kembalian");
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pembeli.setText("");
                Barang.setText("");
                Jumlah.setText("");
                Harga.setText("");
                Bayar.setText("");
                Total.setText("");
                Kembali.setText("");
                Bonus.setText("");
                Keterangan.setText("");
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }
}