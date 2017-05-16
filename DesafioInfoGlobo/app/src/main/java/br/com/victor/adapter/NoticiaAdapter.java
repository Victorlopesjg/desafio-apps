package br.com.victor.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.List;

import br.com.victor.desafioinfoglobo.R;
import br.com.victor.model.Conteudo;
import br.com.victor.utils.ImageUtils;

/**
 * @author Victor Oliveira
 */
public class NoticiaAdapter extends BaseAdapter {

    private Context context;
    private List<Conteudo> conteudos;
    private ImageLoader imageLoader;

    public NoticiaAdapter(Context context, List<Conteudo> capas, ImageLoader imageLoader) {
        this.context = context;
        this.conteudos = capas;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return conteudos.size();
    }

    @Override
    public Object getItem(int position) {
        return conteudos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.item_list_noticia, null);

        Conteudo conteudo = conteudos.get(position);

        ImageView imgNoticia = (ImageView) convertView.findViewById(R.id.imgNoticia);
        TextView txtEditoria = (TextView) convertView.findViewById(R.id.txtEditoria);
        TextView txtTituloNoticia = (TextView) convertView.findViewById(R.id.txtTitulo);

        txtEditoria.setText(conteudo.getSecao().getNome());
        txtTituloNoticia.setText(conteudo.getTitulo());

        if (conteudo.getImagens() != null && !conteudo.getImagens().isEmpty()) {
            imageLoader.displayImage(conteudo.getImagens().get(0).getUrl(), imgNoticia, null, new ImageLoadingListener() {
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
            imgNoticia.setImageDrawable(ImageUtils.getImage(R.drawable.sem_imagem, context));
        }

        return convertView;
    }
}
