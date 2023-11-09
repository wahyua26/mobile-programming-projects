package com.example.dialog_listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private kontakAdapter kAdapter;
    private SQLiteOpenHelper dbopen;
    private SQLiteDatabase dbku;
    ArrayList<kontak> listKontak = new ArrayList<kontak>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn = (ImageButton) findViewById(R.id.btnAdd);
        btn.setOnClickListener(op);

        ImageButton btnEdit = (ImageButton) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(op);

        ImageButton btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(op);

        ImageButton btnCari = (ImageButton) findViewById(R.id.btnSearch);
        btnCari.setOnClickListener(op);

        lv = (ListView) findViewById(R.id.listView);

        kAdapter = new kontakAdapter(this,0,listKontak);

        lv.setAdapter(kAdapter);

        dbopen = new SQLiteOpenHelper(this, "kontak.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        dbku = dbopen.getWritableDatabase();
        dbku.execSQL("create table if not exists kontak(nama TEXT, nohp TEXT)");
        ambildata();
    }

    View.OnClickListener op = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnAdd) tampil_input();
            else if (view.getId() == R.id.btnEdit) {
                cari_kontak_update();
            }
            else if (view.getId() == R.id.btnDelete) {
                cari_kontak_delete();
            }
            else if (view.getId() == R.id.btnSearch) {
                cari_kontak();
            }
        }
    };

    private void add_item(String nm, String no){
        ContentValues datanya = new ContentValues();
        datanya.put("nama",nm);
        datanya.put("nohp",no);
        dbku.insert("kontak",null,datanya);
        listKontak.removeAll(listKontak);
        ambildata();
    }

    private void insertKontak(String nm, String no){
        kontak newKontak = new kontak(nm,no);
        kAdapter.add(newKontak);
    }

    @SuppressLint("Range")
    private void ambildata(){
        Cursor cur = dbku.rawQuery("select * from kontak order by nama asc", null);
        Toast.makeText(this, "Terdapat sejumlah "+ cur.getCount(), Toast.LENGTH_LONG).show();
        int i=0; if(cur.getCount()>0) cur.moveToFirst();
        while (i<cur.getCount()){
            insertKontak(cur.getString(cur.getColumnIndex("nama")),cur.getString(cur.getColumnIndex("nohp")) );
            cur.moveToNext();
            i++;
        }
    }

    @SuppressLint("MissingInflatedId")
    private void tampil_input(){
        LayoutInflater li = LayoutInflater.from(this);
        View inputnya = li.inflate(R.layout.input_dialog,null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(inputnya);
        dialognya.setTitle("Tambah Kontak Baru");
        final EditText Nama = (EditText) inputnya.findViewById(R.id.etNama);
        final EditText Nomor = (EditText) inputnya.findViewById(R.id.etNomor);

        dialognya.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        add_item(Nama.getText().toString(),Nomor.getText().toString());
                        Toast.makeText(getBaseContext(), "Data Tersimpan",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }

    private void cari_kontak_update(){
        LayoutInflater li = LayoutInflater.from(this);
        View carinya = li.inflate(R.layout.cari_kontak,null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(carinya);
        dialognya.setTitle("Masukkan Nama Kontak");
        final EditText Nama = (EditText) carinya.findViewById(R.id.etNama);

        dialognya.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tampil_edit(Nama.getText().toString());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }
    @SuppressLint("Range")
    private void tampil_edit(String nama){
        LayoutInflater li = LayoutInflater.from(this);
        View editnya = li.inflate(R.layout.update_kontak,null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(editnya);
        dialognya.setTitle("Update Kontak");

        final EditText Nama = (EditText) editnya.findViewById(R.id.etNama);
        final EditText Nomor = (EditText) editnya.findViewById(R.id.etNomor);

        Cursor c = dbku.rawQuery("SELECT * FROM kontak WHERE nama = '"+nama+"'", null);
        c.moveToNext();
        Nama.setText(c.getString(c.getColumnIndex("nama")));
        Nomor.setText(c.getString(c.getColumnIndex("nohp")));

        dialognya.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues dataku = new ContentValues();

                        dataku.put("nama", Nama.getText().toString());
                        dataku.put("nohp", Nomor.getText().toString());

                        dbku.update("kontak", dataku,"nama='"+nama+"'",null);
                        Toast.makeText(getBaseContext(), "Data Terupdate",Toast.LENGTH_LONG).show();
                        listKontak.removeAll(listKontak);
                        ambildata();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }

    private void cari_kontak_delete(){
        LayoutInflater li = LayoutInflater.from(this);
        View carinya = li.inflate(R.layout.cari_kontak,null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(carinya);
        dialognya.setTitle("Hapus Kontak");
        final EditText Nama = (EditText) carinya.findViewById(R.id.etNama);

        dialognya.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbku.delete("kontak","nama='"+Nama.getText().toString()+"'",null);
                        Toast.makeText(getBaseContext(), "Data Terhapus",Toast.LENGTH_LONG).show();
                        listKontak.removeAll(listKontak);
                        ambildata();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }

    private void cari_kontak(){
        LayoutInflater li = LayoutInflater.from(this);
        View carinya = li.inflate(R.layout.cari_kontak,null);

        AlertDialog.Builder dialognya = new AlertDialog.Builder(this);
        dialognya.setView(carinya);
        dialognya.setTitle("Cari Kontak");
        final EditText Nama = (EditText) carinya.findViewById(R.id.etNama);

        dialognya.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor c = dbku.rawQuery("SELECT * FROM kontak WHERE nama = '"+Nama.getText().toString()+"'", null);
                        c.moveToNext();
                        listKontak.removeAll(listKontak);
                        insertKontak(c.getString(c.getColumnIndex("nama")), c.getString(c.getColumnIndex("nohp")));

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialognya.show();
    }

    public class kontakAdapter extends ArrayAdapter<kontak> {
        public kontakAdapter(Context context, int resource, List<kontak> objects){
            super(context, resource, objects);
        }
        public View getView(int position, View ConvertView, ViewGroup parent){
            kontak dtkontak = getItem(position);
            if (ConvertView==null){
                ConvertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_user,parent,false);
            }

            TextView tNama = (TextView) ConvertView.findViewById(R.id.tNama);
            TextView tNomor = (TextView) ConvertView.findViewById(R.id.tNomor);

            tNama.setText(dtkontak.getNama());
            tNomor.setText(dtkontak.getNomor());

            return ConvertView;
        }
    }
}