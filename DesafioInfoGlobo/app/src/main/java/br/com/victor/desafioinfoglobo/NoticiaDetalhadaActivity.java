package br.com.victor.desafioinfoglobo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.victor.model.Conteudo;

public class NoticiaDetalhadaActivity extends AppCompatActivity {

    private TextView txtTitulo;
    private TextView txtSubTitulo;
    private TextView txtAutor;
    private TextView txtData;
    private RelativeLayout rlImagem;
    private ImageView imgNoticia;
    private TextView txtLegandaImagem;
    private TextView txtNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalhada);

//        Conteudo conteudo = (Conteudo) getIntent().getExtras().getSerializable("teste");
//        if (conteudo != null) {
//            System.out.println(conteudo.getTitulo());
//        }
    }

    private void setup() {
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtSubTitulo = (TextView) findViewById(R.id.txtSubTitulo);
        txtAutor = (TextView) findViewById(R.id.txtAutor);
        txtData = (TextView) findViewById(R.id.txtData);
        rlImagem = (RelativeLayout) findViewById(R.id.rlImagem);
        imgNoticia = (ImageView) findViewById(R.id.imgNoticia);
        txtLegandaImagem = (TextView) findViewById(R.id.txtLegandaImagem);
        txtNoticia = (TextView) findViewById(R.id.txtNoticia);
    }

    private void init(Conteudo conteudo) {
        txtTitulo.setText(conteudo.getTitulo());
        txtSubTitulo.setText(conteudo.getSubTitulo());

        setAutores(conteudo.getAutores());


    }

    private void setAutores(List<String> autores) {
        if (autores != null && !autores.isEmpty()) {
            String auts = "";
            for (String s : autores) {
                auts += s + ", ";
            }
            txtAutor.setText("POR: " + auts);
        }
    }
}
