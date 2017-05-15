package br.com.victor.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.victor.desafioinfoglobo.R;
import br.com.victor.model.Capa;

/**
 * @author Victor Oliveira
 */
public class NoticiaAdapter extends BaseAdapter {

    private Context context;
    private List<Capa> capas;

    public NoticiaAdapter(Context context, List<Capa> capas) {
        this.context = context;
        this.capas = capas;
    }

    @Override
    public int getCount() {
        return capas.size();
    }

    @Override
    public Object getItem(int position) {
        return capas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.item_list_noticia, null);

        Capa capa = capas.get(position);

        TextView txtEditoria = (TextView) convertView.findViewById(R.id.txtEditoria);
        TextView txtTituloNoticia = (TextView) convertView.findViewById(R.id.txtTituloNoticia);

        txtEditoria.setText(capa.);
        txtTituloNoticia.setText(instituicao.getNome());

        return convertView;
    }
}
