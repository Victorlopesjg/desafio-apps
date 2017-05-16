package br.com.victor.desafioinfoglobo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.victor.model.Conteudo;
import br.com.victor.model.Imagem;
import br.com.victor.utils.CalendarUtils;
import br.com.victor.utils.ImageUtils;

import static br.com.victor.utils.CalendarUtils.formatLongToString;
import static br.com.victor.utils.CalendarUtils.formatStringToDate;

public class NoticiaDetalhadaActivity extends AppCompatActivity {

    private ImageLoader mImageLoader;
    private TextView txtTitulo;
    private TextView txtSubTitulo;
    private TextView txtAutor;
    private TextView txtData;
    private RelativeLayout rlImagem;
    private ImageView imgNoticia;
    private TextView txtLegandaImagem;
    private TextView txtNoticia;
    private Conteudo conteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_detalhada);

        conteudo = (Conteudo) getIntent().getSerializableExtra(getString(R.string.key_intent_noticia));
        if (conteudo != null) {
            getSupportActionBar().setTitle(conteudo.getSecao().getNome());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            setup();
            init(conteudo);
        }
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

        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(ImageUtils.getImage(R.drawable.sem_imagem, getApplicationContext()))
                .showImageOnFail(ImageUtils.getImage(R.drawable.sem_imagem, getApplicationContext()))
                .showImageOnLoading(ImageUtils.getImage(R.drawable.sem_imagem, getApplicationContext()))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(mDisplayImageOptions)
                .memoryCacheSize(50 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .threadPoolSize(5)
                .writeDebugLogs()
                .build();

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }

    private void init(Conteudo conteudo) {
        if (conteudo.getTitulo() != null) {
            txtTitulo.setText(conteudo.getTitulo());
        }

        if (conteudo.getSubTitulo() != null) {
            txtSubTitulo.setText(conteudo.getSubTitulo());
        } else {
            txtSubTitulo.setVisibility(View.GONE);
        }

        if (conteudo.getTexto() != null) {
            txtNoticia.setText(conteudo.getTexto());
        } else {
            txtNoticia.setVisibility(View.GONE);
        }

        // Exibindo os autores da noticia
        setAutores(conteudo.getAutores());

        // Exibindo a data da publicação
        setDataNoticia(conteudo.getPublicadoEm());

        // Exibindo a imagem da noticia
        setImagemNoticia(conteudo.getImagens());
    }

    private void setImagemNoticia(List<Imagem> imagens) {
        if (imagens != null && !imagens.isEmpty()) {
            Imagem imagem = imagens.get(0);
            txtLegandaImagem.setText(imagem.getLegenda() + ". " + imagem.getFonte());
            mImageLoader.displayImage(imagem.getUrl(), imgNoticia, null, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            }, new ImageLoadingProgressListener() {
                @Override
                public void onProgressUpdate(String imageUri, View view, int current, int total) {

                }
            });
        } else {
            rlImagem.setVisibility(View.GONE);
        }
    }

    private void setDataNoticia(String data) {
        if (data != null && !data.isEmpty()) {
            Date date = formatStringToDate("2017-03-08T14:39:44-0300", "yyyy-MM-dd'T'HH:mm:ss-SSSS", Locale.ENGLISH);
            String dataNoticia = formatLongToString(date.getTime(), "dd/MM/YY HH:mm");
            txtData.setText(dataNoticia);
        } else {
            txtData.setVisibility(View.GONE);
        }
    }

    private void setAutores(List<String> autores) {
        if (autores != null && !autores.isEmpty()) {
            String auts = "";
            for (String s : autores) {
                auts += s + ", ";
            }
            txtAutor.setText("POR: " + auts);
        } else {
            txtAutor.setVisibility(View.GONE);
        }
    }

    public void compartilharNoticia() {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_TEXT, conteudo.getTitulo() +"\n\n" +
                conteudo.getUrl() + "\n\nVia O GLOBO");
        startActivity(Intent.createChooser(i,"Compartilhar com"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_noticia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_noticia_compartilhar:
                compartilharNoticia();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
