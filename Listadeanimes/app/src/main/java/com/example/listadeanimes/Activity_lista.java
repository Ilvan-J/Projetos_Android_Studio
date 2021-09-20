package com.example.listadeanimes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.listadeanimes.database.BancoController;
import com.example.listadeanimes.database.CriaBanco;

public class Activity_lista extends AppCompatActivity{

    private ListView lista;
    private BancoController crud;
    private String codigo;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        crud = new BancoController(this);
       /* Cursor cursor = crud.carregarDados();
        String[] nomeCampos = new String[]{"_id", "nome", "temporadas"};
        int[] idView = new int[]{R.id.idAnime, R.id.nomeAnime, R.id.temporadaAnime};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.estilizar, cursor, nomeCampos, idView, 0);
        lista = findViewById(R.id.ListView);
        lista.setAdapter(adaptador);*/

        lista = findViewById(R.id.ListView);
        //ordenarId();
        ordemAlfabetica();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                abrirDialogo(view);

            }
        });

        getSupportActionBar().setTitle("Lista de animes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void ordenarId(){
        cursor = crud.carregarDados();
        String[] nomeCampos = new String[]{"_id", "nome", "temporadas"};
        int[] idView = new int[]{R.id.idAnime, R.id.nomeAnime, R.id.temporadaAnime};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.estilizar, cursor, nomeCampos, idView, 0);
        lista.setAdapter(adaptador);
    }

    public void ordemAlfabetica(){

        cursor = crud.carregarOrdemAlfabetica();
        String[] nomeCampos = new String[]{"_id", "nome", "temporadas"};
        int[] idView = new int[]{R.id.idAnime, R.id.nomeAnime, R.id.temporadaAnime};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.estilizar, cursor, nomeCampos, idView, 0);
        lista.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu_lista, menu);

        MenuItem pesquisa = menu.findItem(R.id.pesquisa);
        SearchView editDePesquisa = (SearchView) pesquisa.getActionView();
        editDePesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cursor = crud.pesquisar(query);
                String[] nomeCampos = new String[]{"_id", "nome", "temporadas"};
                int[] idView = new int[]{R.id.idAnime, R.id.nomeAnime, R.id.temporadaAnime};

                SimpleCursorAdapter adaptador = new SimpleCursorAdapter(Activity_lista.this, R.layout.estilizar, cursor, nomeCampos, idView, 0);
                lista.setAdapter(adaptador);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cursor = crud.pesquisar(newText);
                String[] nomeCampos = new String[]{"_id", "nome", "temporadas"};
                int[] idView = new int[]{R.id.idAnime, R.id.nomeAnime, R.id.temporadaAnime};

                SimpleCursorAdapter adaptador = new SimpleCursorAdapter(Activity_lista.this, R.layout.estilizar, cursor, nomeCampos, idView, 0);
                lista.setAdapter(adaptador);
                return false;
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.ordem_alfabetica:
                ordemAlfabetica();
                return true;
            case R.id.ordem_id:
                ordenarId();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void abrirDialogo(View view){

        //criando uma instância do aletr dialogo
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Opção");
        dialogo.setMessage("Deseja ver detalhes ou alterar ? ");

        //configurar o cancelamento
        //dialogo.setCancelable(false);

        // configurar o icone
        //dialogo.setIcon(android.R.drawable.btn_default);

        //configurar botões

        dialogo.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Activity_lista.this, Activity_Alterar.class);
                intent.putExtra("codigo", codigo);

                startActivity(intent);
                finish();

            }
        });

        dialogo.setNegativeButton("Detalhes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity_lista.this, Activity_Detalhes.class);
                intent.putExtra("codigo", codigo);

                startActivity(intent);
                finish();

            }
        });

        //criar e exibir o diolog
        dialogo.create();
        dialogo.show();

    }

}