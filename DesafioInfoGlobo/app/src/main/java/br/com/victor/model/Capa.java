
package br.com.victor.model;

import java.io.Serializable;
import java.util.List;

import br.com.victor.annotation.Path;
import br.com.victor.interfaces.PersistBD;

@Path("capa.json")
public class Capa implements PersistBD, Serializable {

    private Object id;
    private List<Conteudo> conteudos = null;
    private String produto;

    public List<Conteudo> getConteudos() {
        return conteudos;
    }

    public void setConteudos(List<Conteudo> conteudos) {
        this.conteudos = conteudos;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }
}
