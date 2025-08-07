package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Emprestimo {
    private Integer id;
    private Cliente cliente;
    private Livro livro;
    private LocalDateTime dataHoraEmprestimo;

    public Emprestimo(Integer id, Cliente cliente, Livro livro, LocalDateTime dataHoraEmprestimo) {
        this.id = id;
        this.cliente = cliente;
        this.livro = livro;
        this.dataHoraEmprestimo = dataHoraEmprestimo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDateTime getDataHoraEmprestimo() {
        return dataHoraEmprestimo;
    }

    public void setDataHoraEmprestimo(LocalDateTime dataHoraEmprestimo) {
        this.dataHoraEmprestimo = dataHoraEmprestimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(id, that.id) && Objects.equals(cliente, that.cliente) && Objects.equals(livro, that.livro) && Objects.equals(dataHoraEmprestimo, that.dataHoraEmprestimo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, livro, dataHoraEmprestimo);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", livro=" + livro +
                ", dataHoraEmprestimo=" + dataHoraEmprestimo +
                '}';
    }
}
