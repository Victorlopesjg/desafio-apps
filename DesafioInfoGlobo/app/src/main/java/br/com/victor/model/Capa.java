
package br.com.victor.model;

import java.util.List;

import br.com.victor.annotation.Path;

@Path("capa.json")
public class Capa {

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
}
