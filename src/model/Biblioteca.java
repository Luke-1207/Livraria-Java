package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Biblioteca {
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    public Biblioteca(List<Livro> livros, List<Autor> autores) {
        this.livros = livros;
        this.autores = autores;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biblioteca that = (Biblioteca) o;
        return Objects.equals(livros, that.livros) && Objects.equals(autores, that.autores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(livros, autores);
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "livros=" + livros +
                ", autores=" + autores +
                '}';
    }
}
