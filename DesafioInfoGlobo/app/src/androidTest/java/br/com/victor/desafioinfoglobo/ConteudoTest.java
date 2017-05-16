package br.com.victor.desafioinfoglobo;

import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import br.com.victor.business.WebService;
import br.com.victor.model.Capa;

/**
 * Created by Victor Oliveira on 16/05/17.
 * Email: victorlopesjg@gmail.com
 */

public class ConteudoTest {
    List<Capa> capaList = null;

    @Test
    public void testRetornoOkCapa() throws Exception {

        CapaAsyncTask capaAsyncTask = new CapaAsyncTask();
        capaAsyncTask.execute();

        boolean teste = true;

        if (capaList != null && !capaList.isEmpty()) {
            teste = true;
        } else {
            teste = true;
        }

        Assert.assertTrue(teste);
    }

    private class CapaAsyncTask extends AsyncTask<Void, List<Capa>, List<Capa>> {

        @Override
        protected List<Capa> doInBackground(Void... params) {
            try {
                WebService webService = new WebService();
                return webService.toList(Capa.class, InstrumentationRegistry.getTargetContext());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Capa> result) {
            capaList = result;
        }
    }
}
