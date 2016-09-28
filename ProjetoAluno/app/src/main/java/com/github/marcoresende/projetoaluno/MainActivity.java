package com.github.marcoresende.projetoaluno;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.marcoresende.projetoaluno.adapter.AdapterAlunos;
import com.github.marcoresende.projetoaluno.model.Aluno;
import com.github.marcoresende.projetoaluno.model.dto.AlunoResponseDto;
import com.github.marcoresende.projetoaluno.service.AlunoAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String GEO_URL = "geo:0,0?q=";
    final String TAG_API = "API";
    final int ADD_USER_CODE = 101;

    ListView listViewAlunos;
    ProgressDialog pDialog;
    Context context = this;
    AdapterAlunos adapterAlunos;
    AlunoAPI alunoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(AlunoAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        alunoApi = retrofit.create(AlunoAPI.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, AddAlunoActivity.class);
                startActivityForResult(it, ADD_USER_CODE);
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        listViewAlunos = (ListView) findViewById(R.id.listViewAluno);

        //recupera lista de alunos
        findAlunos();
    }

    public void deleteAluno(Aluno aluno){
        if(aluno != null && aluno.getObjectId() != null && !aluno.getObjectId().isEmpty()) {
            pDialog.setMessage("Excluindo...");
            pDialog.show();
            Call<Void> call = alunoApi.deleteAluno(aluno.getObjectId());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        pDialog.dismiss();
                        findAlunos();
                        Toast.makeText(context, "Excluído com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        pDialog.dismiss();
                        Log.e(TAG_API, response.message());
                        Toast.makeText(context, "Não foi possível remover o aluno!", Toast.LENGTH_LONG).show();;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG_API, t.getMessage());
                    pDialog.dismiss();
                    Toast.makeText(context, "Não foi possível remover o aluno!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(this, "Não foi possível remover o aluno!", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_USER_CODE) {
            if(resultCode == RESULT_OK){
                Toast.makeText(context, "Usuário adicionado com sucesso!", Toast.LENGTH_LONG).show();
                findAlunos();
            }
        }
    }

    /**
     * Recupera lista de alunos
     */
    private void findAlunos() {
        pDialog.setMessage("Atualizando lista de alunos...");
        pDialog.show();

        Call<AlunoResponseDto> call = alunoApi.getAlunos();
        call.enqueue(new Callback<AlunoResponseDto>() {
            @Override
            public void onResponse(Call<AlunoResponseDto> call, Response<AlunoResponseDto> response) {
                if (response.isSuccessful()) {
                    try {
                        AlunoResponseDto responseAluno = response.body();
                        makeAdapterListView(responseAluno.getResults());
                    } catch (Exception e) {
                        Log.e(TAG_API, e.getMessage());
                    }
                } else {
                    Log.e(TAG_API, response.message());
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AlunoResponseDto> call, Throwable t) {
                Log.e(TAG_API, t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    private void makeAdapterListView(List<Aluno> alunos) {
        adapterAlunos = new AdapterAlunos(this, alunos);
        listViewAlunos.setAdapter(adapterAlunos);
        listViewAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Aluno aluno = (Aluno) adapterAlunos.getItem(position);
                    Uri gmmIntentUri = Uri.parse(GEO_URL + aluno.getEndereco());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    // mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        throw new Exception("Erro ao exibir mapa!");
                    }
                } catch(Exception e) {
                    Toast.makeText(context, "Erro ao exibir mapa!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
