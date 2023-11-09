package com.example.formnilai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText etNamaMahasiswa, etNRPMahasiswa, etMataKuliah, etNilaiTugas1, etNilaiTugas2, etNilaiTugas3, etNilaiETS, etNilaiEAS;
    private Button btnKirim, btnHapus, btnKeluar;
    private TextView tvNamaMahasiswa, tvNRPMahasiswa, tvMataKuliah, tvNilaiTugas1, tvNilaiTugas2, tvNilaiTugas3, tvNilaiETS, tvNilaiEAS, tvHasilAkhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("FORM NILAI");

        etNamaMahasiswa = (EditText)findViewById(R.id.etNamaMahasiswa);
        etNRPMahasiswa = (EditText)findViewById(R.id.etNRPMahasiswa);
        etMataKuliah = (EditText)findViewById(R.id.etMataKuliah);
        etNilaiTugas1 = (EditText)findViewById(R.id.etNilaiTugas1);
        etNilaiTugas2 = (EditText)findViewById(R.id.etNilaiTugas2);
        etNilaiTugas3 = (EditText)findViewById(R.id.etNilaiTugas3);
        etNilaiETS = (EditText)findViewById(R.id.etNilaiETS);
        etNilaiEAS = (EditText)findViewById(R.id.etNilaiEAS);

        btnKirim = (Button)findViewById(R.id.btnKirim);
        btnHapus = (Button)findViewById(R.id.btnHapus);
        btnKeluar = (Button)findViewById(R.id.btnKeluar);

        tvNamaMahasiswa = (TextView)findViewById(R.id.tvNamaMahasiswa);
        tvNRPMahasiswa = (TextView)findViewById(R.id.tvNRPMahasiswa);
        tvMataKuliah = (TextView)findViewById(R.id.tvMataKuliah);
        tvNilaiTugas1 = (TextView)findViewById(R.id.tvNilaiTugas1);
        tvNilaiTugas2 = (TextView)findViewById(R.id.tvNilaiTugas2);
        tvNilaiTugas3 = (TextView)findViewById(R.id.tvNilaiTugas3);
        tvNilaiETS = (TextView)findViewById(R.id.tvNilaiETS);
        tvNilaiEAS = (TextView)findViewById(R.id.tvNilaiEAS);
        tvHasilAkhir = (TextView)findViewById(R.id.tvHasilAkhir);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double NT1 = Double.parseDouble(etNilaiTugas1.getText().toString().trim());
                double NT2 = Double.parseDouble(etNilaiTugas2.getText().toString().trim());
                double NT3 = Double.parseDouble(etNilaiTugas3.getText().toString().trim());
                double ETS = Double.parseDouble(etNilaiETS.getText().toString().trim());
                double EAS = Double.parseDouble(etNilaiEAS.getText().toString().trim());
                double total = NT1*0.1+NT2*0.1+NT3*0.1+ETS*0.3+EAS*0.4;

                if (total>85) tvHasilAkhir.setText("Nilai Akhir : A");
                else if(total>75 && total<86) tvHasilAkhir.setText("Nilai Akhir : AB");
                else if(total>65 && total<76) tvHasilAkhir.setText("Nilai Akhir : B");
                else if(total>60 && total<66) tvHasilAkhir.setText("Nilai Akhir : BC");
                else if(total>55 && total<61) tvHasilAkhir.setText("Nilai Akhir : C");
                else if(total>40 && total<56) tvHasilAkhir.setText("Nilai Akhir : D");
                else if(total>=0 && total<41) tvHasilAkhir.setText("Nilai Akhir : E");

                tvNamaMahasiswa.setText("Nama Mahasiswa : " + etNamaMahasiswa.getText());
                tvNRPMahasiswa.setText("NRP Mahasiswa : " + etNRPMahasiswa.getText());
                tvMataKuliah.setText("Mata Kuliah : " + etMataKuliah.getText());
                tvNilaiTugas1.setText("Nilai Tugas 1 :  " + etNilaiTugas1.getText());
                tvNilaiTugas2.setText("Nilai Tugas 2 : " + etNilaiTugas2.getText());
                tvNilaiTugas3.setText("Nilai Tugas 3 : " + etNilaiTugas3.getText());
                tvNilaiETS.setText("Nilai ETS : " + etNilaiETS.getText());
                tvNilaiEAS.setText("Nilai EAS : " + etNilaiEAS.getText());
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNamaMahasiswa.setText("");
                tvNRPMahasiswa.setText("");
                tvMataKuliah.setText("");
                tvNilaiTugas1.setText("");
                tvNilaiTugas2.setText("");
                tvNilaiTugas3.setText("");
                tvNilaiETS.setText("");
                tvNilaiEAS.setText("");
                tvHasilAkhir.setText("");
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