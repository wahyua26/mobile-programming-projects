package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText NamaMahasiswa, NRPMahasiswa, NilaiTugas1, NilaiTugas2, NilaiTugas3, NilaiETS, NilaiEAS;
    private Button Simpan, Ambil, Update, Delete;
    private TextView NilaiAkhir;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NamaMahasiswa = (EditText)findViewById(R.id.etNamaMahasiswa);
        NRPMahasiswa = (EditText)findViewById(R.id.etNRPMahasiswa);
        NilaiTugas1 = (EditText)findViewById(R.id.etNilaiTugas1);
        NilaiTugas2 = (EditText)findViewById(R.id.etNilaiTugas2);
        NilaiTugas3 = (EditText)findViewById(R.id.etNilaiTugas3);
        NilaiETS = (EditText)findViewById(R.id.etNilaiETS);
        NilaiEAS = (EditText)findViewById(R.id.etNilaiEAS);

        Simpan = (Button)findViewById(R.id.btnSimpan);
        Ambil = (Button)findViewById(R.id.btnAmbil);
        Update = (Button)findViewById(R.id.btnUpdate);
        Delete = (Button)findViewById(R.id.btnDelete);

        NilaiAkhir = (TextView)findViewById(R.id.tvNilaiAkhir);

        Simpan.setOnClickListener(operasi);
        Ambil.setOnClickListener(operasi);
        Update.setOnClickListener(operasi);
        Delete.setOnClickListener(operasi);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null,1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newversion) {

            }
        };

        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists maha(nrp TEXT, nama TEXT, nilaitugas1 TEXT, nilaitugas2 TEXT, nilaitugas3 TEXT, nilaiets TEXT, nilaieas TEXT, nilaiakhir TEXT);");

    }

    @Override
    protected void onStop() {
        dbku.close();
        Opendb.close();
        super.onStop();
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            if (view.getId()==R.id.btnSimpan) simpan();
            else if (view.getId()==R.id.btnAmbil) ambil();
            else if (view.getId()==R.id.btnUpdate) update();
            else if (view.getId()==R.id.btnDelete) delete();
        }
    };

    private String hitung()
    {
        double NT1 = Double.parseDouble(NilaiTugas1.getText().toString().trim());
        double NT2 = Double.parseDouble(NilaiTugas2.getText().toString().trim());
        double NT3 = Double.parseDouble(NilaiTugas3.getText().toString().trim());
        double ETS = Double.parseDouble(NilaiETS.getText().toString().trim());
        double EAS = Double.parseDouble(NilaiEAS.getText().toString().trim());
        double total = NT1*0.1+NT2*0.1+NT3*0.1+ETS*0.3+EAS*0.4;

        if (total>85) return "A";
        else if(total>75 && total<86) return "AB";
        else if(total>65 && total<76) return "B";
        else if(total>60 && total<66) return "BC";
        else if(total>55 && total<61) return "C";
        else if(total>40 && total<56) return "D";
        else if(total>=0 && total<41) return "E";
        return null;
    }

    private void simpan()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("nrp", NRPMahasiswa.getText().toString());
        dataku.put("nama", NamaMahasiswa.getText().toString());
        dataku.put("nilaitugas1", NilaiTugas1.getText().toString());
        dataku.put("nilaitugas2", NilaiTugas2.getText().toString());
        dataku.put("nilaitugas3", NilaiTugas3.getText().toString());
        dataku.put("nilaiets", NilaiETS.getText().toString());
        dataku.put("nilaieas", NilaiEAS.getText().toString());
        dataku.put("nilaiakhir", hitung());

        dbku.insert("maha", null, dataku);
        Toast.makeText(this,"Data Tersimpan", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("Range")
    private void ambil(){
        Cursor cur = dbku.rawQuery("select * from maha where nrp='"+NRPMahasiswa.getText().toString()+"'",null);

        if(cur.getCount() > 0){
            Toast.makeText(this, "Data Ditemukan Sejumlah "+ cur.getCount(), Toast.LENGTH_LONG).show();
            cur.moveToFirst();
            NamaMahasiswa.setText(cur.getString(cur.getColumnIndex("nama")));
            NilaiTugas1.setText(cur.getString(cur.getColumnIndex("nilaitugas1")));
            NilaiTugas2.setText(cur.getString(cur.getColumnIndex("nilaitugas2")));
            NilaiTugas3.setText(cur.getString(cur.getColumnIndex("nilaitugas3")));
            NilaiETS.setText(cur.getString(cur.getColumnIndex("nilaiets")));
            NilaiEAS.setText(cur.getString(cur.getColumnIndex("nilaieas")));
            NilaiAkhir.setText(cur.getString(cur.getColumnIndex("nilaiakhir")));
        }
        else Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
    }

    private void update()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("nrp", NRPMahasiswa.getText().toString());
        dataku.put("nama", NamaMahasiswa.getText().toString());
        dataku.put("nilaitugas1", NilaiTugas1.getText().toString());
        dataku.put("nilaitugas2", NilaiTugas2.getText().toString());
        dataku.put("nilaitugas3", NilaiTugas3.getText().toString());
        dataku.put("nilaiets", NilaiETS.getText().toString());
        dataku.put("nilaieas", NilaiEAS.getText().toString());
        dataku.put("nilaiakhir", hitung());

        dbku.update("maha", dataku,"nrp='"+NRPMahasiswa.getText().toString()+"'",null);
        Toast.makeText(this, "Data Terupdate", Toast.LENGTH_LONG).show();
    }

    private void delete(){
        dbku.delete("maha","nrp='"+NRPMahasiswa.getText().toString()+"'",null);
        Toast.makeText(this,"Data Terhapus",Toast.LENGTH_LONG).show();
    }

}