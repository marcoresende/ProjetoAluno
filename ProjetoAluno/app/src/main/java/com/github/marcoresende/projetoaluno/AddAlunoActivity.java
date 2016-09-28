package com.github.marcoresende.projetoaluno;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.opengl.EGLDisplay;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.marcoresende.projetoaluno.model.Aluno;
import com.github.marcoresende.projetoaluno.service.AlunoAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAlunoActivity extends AppCompatActivity {

    final String TAG_API = "API";
    final Context context = this;
    EditText edtNome;
    EditText edtTel;
    EditText edtIdade;
    EditText edtEndereco;
    EditText edtFoto;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aluno);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Salvando...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtTel = (EditText) findViewById(R.id.edtTel);
        edtIdade = (EditText) findViewById(R.id.edtIdade);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco);
        edtFoto = (EditText) findViewById(R.id.edtFoto);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });

        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED);
                finish();
            }
        });


    }

    private void salvarAluno(){
        Aluno aluno = new Aluno();
        aluno.setNome(edtNome.getText().toString());
        aluno.setTelefone(edtTel.getText().toString());
        aluno.setIdade(edtIdade.getText().toString().isEmpty() ? null : Integer.valueOf(edtIdade.getText().toString()));
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setFotoUrl(edtFoto.getText().toString());
        //verifica se aluno contem informacoes validas
        if(aluno.isValido()){
            pDialog.setMessage("Salvando...");
            pDialog.show();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(AlunoAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            AlunoAPI alunoApi = retrofit.create(AlunoAPI.class);

            Call<Object> call = alunoApi.addAluno(aluno);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        pDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        pDialog.dismiss();
                        Log.e(TAG_API, response.message());
                        Toast.makeText(context, "Ocorreu um erro ao adicionar aluno!", Toast.LENGTH_LONG).show();;
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e(TAG_API, t.getMessage());
                    if (pDialog != null)
                        pDialog.dismiss();
                    Toast.makeText(context, "Ocorreu um erro ao adicionar aluno!", Toast.LENGTH_LONG).show();;
                }
            });
        } else {
            Toast.makeText(context, "Campos obrigatórios devem ser preenchidos!", Toast.LENGTH_LONG).show();
        }
    }
}
