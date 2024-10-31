package io.github.daviddev16;

import java.io.File;

public class DadosPesquisa {

    private File arquivo;
    private String trecho;
    private int indice;
    
    public DadosPesquisa(File arquivo, String trecho, int indice) {
        this.arquivo = arquivo;
        this.trecho = trecho;
        this.indice = indice;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public String getTrecho() {
        return trecho;
    }

    public void setTrecho(String trecho) {
        this.trecho = trecho;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return "DadosPesquisa [arquivo=" + arquivo + ", trecho=" + trecho + ", indice=" + indice + "]";
    }
    
    
    
}
