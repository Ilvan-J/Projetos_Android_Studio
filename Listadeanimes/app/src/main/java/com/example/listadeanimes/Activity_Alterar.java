package com.example.listadeanimes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.listadeanimes.database.BancoController;
import com.example.listadeanimes.database.anime;

public class Activity_Alterar extends AppCompatActivity {

    private EditText editNome, editTemporada;
    private String status = "", meuStatus = "";
    private Button btnAlterar, btnAlDeletar;
    private Cursor cursor;
    private BancoController crud;
    private String codigo;
    private anime anime;
    private RadioButton radAniFinalizado, radAniAndamento, radMeuAssisti, radMeuAndamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__alterar);

        codigo = this.getIntent().getStringExtra("codigo");

        editNome = findViewById(R.id.editAltNome);
        editTemporada = findViewById(R.id.editAltTemporadas);

        radAniFinalizado = findViewById(R.id.radioAniFinalizado);
        radAniAndamento = findViewById(R.id.radioAniAndamento);
        radMeuAssisti = findViewById(R.id.radMeuAssisti);
        radMeuAndamento = findViewById(R.id.radMeuAdamento);

        btnAlterar = findViewById(R.id.btnAlterar);
        btnAlDeletar = findViewById(R.id.btnDeletar);

        anime = new anime();

        crud = new BancoController(this);
        cursor = crud.carregarDadoById(Integer.parseInt(codigo));
        editNome.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        editTemporada.setText(cursor.getString(cursor.getColumnIndexOrThrow("temporadas")));
        status = cursor.getString(cursor.getColumnIndexOrThrow("status_anime"));
        meuStatus = cursor.getString(cursor.getColumnIndexOrThrow("status_pessoal"));
        radAtivado();


        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNome.getText().toString().equals("") ||
                editTemporada.getText().toString().equals("")||
                status.equals("")||
                meuStatus.equals("")){
                    Toast.makeText(Activity_Alterar.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else {

                    anime.setId(Integer.parseInt(codigo));
                    anime.setNome(editNome.getText().toString());
                    anime.setTemporadas(Integer.parseInt(editTemporada.getText().toString()));
                    anime.setStatus_anime(status);
                    anime.setStatus_pessoal(meuStatus);

                    alterar();
                }
            }
        });

        btnAlDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar();
            }
        });

        getSupportActionBar().setTitle("Alterar");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Activity_Alterar.this, Activity_lista.class);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioAniFinalizado:
                if (checked)
                    // Pirates are the best
                    status = "Finalizado";
                break;
            case R.id.radioAniAndamento:
                if (checked)
                    // Ninjas rule
                    status = "Em andamento";
                break;
            case R.id.radMeuAssisti:
                if (checked)
                    meuStatus = "Assisti";
                break;
            case R.id.radMeuAdamento:
                if (checked)
                    meuStatus = "Em andamento";
                break;
        }
    }

    public void radAtivado(){
        switch (status){
            case "Finalizado":
                radAniFinalizado.setChecked(true);
                break;
            case "Em andamento":
                radAniAndamento.setChecked(true);
                break;
        }

        switch (meuStatus){
            case "Assisti":
                radMeuAssisti.setChecked(true);
                break;
            case "Em andamento":
                radMeuAndamento.setChecked(true);
                break;
        }
    }

    public void deletar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Deletar");
        builder.setMessage("Têm certeza que deseja deletar ?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crud.deletarRegistro(Integer.parseInt(codigo));
                Intent intent = new Intent(Activity_Alterar.this, Activity_lista.class);
                startActivity(intent);
                Toast.makeText(Activity_Alterar.this, "Deletado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create();
        builder.show();

    }

    public void alterar(){
        AlertDialog.Builder diologo = new AlertDialog.Builder(this);

        diologo.setTitle("Alteração");
        diologo.setMessage("Deseja salvar alteração ?");
        diologo.setCancelable(false);

        diologo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crud.alterarRegistro(anime);
                Intent intent = new Intent(Activity_Alterar.this, Activity_lista.class);
                startActivity(intent);
                Toast.makeText(Activity_Alterar.this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        diologo.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        diologo.create();
        diologo.show();

    }

}