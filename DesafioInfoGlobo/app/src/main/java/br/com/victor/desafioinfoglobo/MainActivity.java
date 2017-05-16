package br.com.victor.desafioinfoglobo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.victor.adapter.NoticiaAdapter;
import br.com.victor.business.WebService;
import br.com.victor.model.Capa;
import br.com.victor.model.Conteudo;
import br.com.victor.utils.ImageUtils;

public class MainActivity extends AppCompatActivity {
    private ImageLoader mImageLoader;
    private ProgressBar pb;
    private ListView listNoticias;
    private NoticiasAsyncTask noticiasAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

    }

    private void setup() {
        pb = (ProgressBar) findViewById(R.id.carregar);
        listNoticias = (ListView) findViewById(R.id.listParticipantes);

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

        noticiasAsyncTask = new NoticiasAsyncTask();
        noticiasAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    private class NoticiasAsyncTask extends AsyncTask<Void, List<Capa>, List<Capa>> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected List<Capa> doInBackground(Void... params) {
            try {
                WebService webService = new WebService();
                return webService.toList(Capa.class, getApplicationContext());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Capa> result) {
            pb.setVisibility(ProgressBar.GONE);

            runOnUiThread(new Runnable() {
                public void run() {
                    if (result != null && !result.isEmpty()) {
                        List<Conteudo> conteudosList = new ArrayList<Conteudo>();
                        for (Capa capa : result) {
                            conteudosList.addAll(capa.getConteudos());
                        }

                        NoticiaAdapter adapter = new NoticiaAdapter(getApplicationContext(), conteudosList, mImageLoader);
                        listNoticias.setAdapter(adapter);

                        listNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                Conteudo conteudo = (Conteudo) listNoticias.getItemAtPosition(position);

                                Intent intent = new Intent(MainActivity.this, NoticiaDetalhadaActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable(getString(R.string.key_intent_noticia), conteudo);
//                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }
            });
        }
    }
}
