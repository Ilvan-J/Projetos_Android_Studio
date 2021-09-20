package com.example.listadeanimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.listadeanimes.database.BancoController;
import com.example.listadeanimes.database.anime;

public class MainActivity extends AppCompatActivity {

    private EditText editNome, editTemporada;
    private String status = "";
    private String meuStatus = "";
    private Button btnSalvar;
    private BancoController crud;
    private anime anime;
    private String resultado = "";
    private RadioButton radAniFinalizado, radAniAndamento, radMeuFinalizado, radMeuAndamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crud = new BancoController(this);
        anime = new anime();

        editNome = findViewById(R.id.editAltNome);
        editTemporada = findViewById(R.id.editAltTemporadas);

        radAniAndamento = findViewById(R.id.radioAniAndamento);
        radAniFinalizado = findViewById(R.id.radioAniFinalizado);
        radMeuFinalizado = findViewById(R.id.radMeuAssisti);
        radMeuAndamento = findViewById(R.id.radMeuAdamento);

        radAniFinalizado.setChecked(true);
        status = "Finalizado";
        radMeuAndamento.setChecked(true);
        meuStatus = "Em andamento";

        btnSalvar = findViewById(R.id.btnAlterar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNome.getText().toString().equals("") ||
                        editTemporada.getText().toString().equals("")
                || status.equals("")
                || meuStatus.equals("")){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{

                    anime.setNome(editNome.getText().toString());
                    anime.setTemporadas(Integer.parseInt(editTemporada.getText().toString()));
                    anime.setStatus_anime(status);
                    anime.setStatus_pessoal(meuStatus);

                    resultado = crud.salvar(anime);

                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();

                    editNome.setText("");
                    editTemporada.setText("");

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lista:
                Intent intent = new Intent(MainActivity.this, Activity_lista.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
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

}