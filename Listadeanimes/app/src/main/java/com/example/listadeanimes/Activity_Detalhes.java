package com.example.listadeanimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.listadeanimes.database.BancoController;

public class Activity_Detalhes extends AppCompatActivity {

    private String codigo;
    private TextView lblNome, lblTemporada, lblAniStatus, lblMeuStatus;
    private BancoController crud;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__detalhes);

        lblNome = findViewById(R.id.lblNome);
        lblTemporada = findViewById(R.id.lblTemporadas);
        lblAniStatus = findViewById(R.id.lblStatusAni);
        lblMeuStatus = findViewById(R.id.lblMeuStatus);

        codigo = this.getIntent().getStringExtra("codigo");

        crud = new BancoController(this);

        cursor = crud.carregarDadoById(Integer.parseInt(codigo));

        lblNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        lblTemporada.setText(cursor.getString(cursor.getColumnIndexOrThrow("temporadas")));
        lblAniStatus.setText(cursor.getString(cursor.getColumnIndexOrThrow("status_anime")));
        lblMeuStatus.setText(cursor.getString(cursor.getColumnIndexOrThrow("status_pessoal")));

        getSupportActionBar().setTitle("Detalhes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Activity_Detalhes.this, Activity_lista.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}