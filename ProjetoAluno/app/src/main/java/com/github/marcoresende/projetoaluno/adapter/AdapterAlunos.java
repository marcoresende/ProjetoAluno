package com.github.marcoresende.projetoaluno.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.marcoresende.projetoaluno.MainActivity;
import com.github.marcoresende.projetoaluno.R;
import com.github.marcoresende.projetoaluno.model.Aluno;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by MarcoResende on 18/09/2016.
 */
public class AdapterAlunos extends BaseAdapter {
    private Context context;
    private List<Aluno> alunos;

    public AdapterAlunos(){}

    public AdapterAlunos(Context context, List<Aluno> alunos) {

        this.context = context;
        this.alunos = alunos;
    }

    public void setListAlunos(List<Aluno> listAlunos){
        this.alunos = listAlunos;
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos != null ? alunos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Infla a view da linha/item
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_aluno, parent, false);

        // findViewById das views que precisa atualizar
        TextView txtNomeAluno = (TextView) view.findViewById(R.id.txtNomeAluno);
        TextView txtIdadeAluno = (TextView) view.findViewById(R.id.txtIdade);
        TextView txtEndereco = (TextView) view.findViewById(R.id.txtEndereco);
        ImageView imgAluno = (ImageView) view.findViewById(R.id.imgAluno);

        final Aluno aluno = alunos.get(position);
        txtNomeAluno.setText(aluno.getNome());
        txtIdadeAluno.setText(aluno.getIdade() != null ? aluno.getIdade().toString() : "");
        txtEndereco.setText(aluno.getEndereco());

        ImageButton btnDelete = (ImageButton) view.findViewById(R.id.deleteBtn);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir o aluno?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(context instanceof MainActivity){
                                    ((MainActivity)context).deleteAluno(aluno);
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        btnDelete.setFocusable(false);

        Picasso.with(context)
                .load(aluno.getFotoUrl())
                .into(imgAluno, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {}
                    @Override
                    public void onError() {}
                });

        return view;
    }

}
