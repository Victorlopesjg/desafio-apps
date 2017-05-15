package br.com.victor.desafioinfoglobo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import br.com.victor.business.WebService;
import br.com.victor.model.Capa;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InBackground();
    }


    private void InBackground() {
        new AsyncTask<Void, List<Capa>, List<Capa>>() {
            private String code = null;

            @Override
            protected List<Capa> doInBackground(Void... params) {
                try {
                    WebService webService = new WebService();
                    webService.toList(Capa.class, getApplicationContext());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(final List<Capa> result) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }.execute(null, null, null);
    }
}
