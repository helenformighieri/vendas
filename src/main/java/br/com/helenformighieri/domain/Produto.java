package br.com.helenformighieri.domain;

import br.com.helenformighieri.dao.Persistente;
import br.com.helenformighieri.anotacao.ColunaTabela;
import br.com.helenformighieri.anotacao.Tabela;
import br.com.helenformighieri.anotacao.TipoChave;

import java.math.BigDecimal;

@Tabela("TB_PRODUTO")
public class Produto implements Persistente {

    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;

    @ColunaTabela(dbName = "codigo", setJavaName = "setCodigo")
    @TipoChave("getCodigo")
    private String codigo;

    @ColunaTabela(dbName = "nome", setJavaName = "setNome")
    private String nome;

    @ColunaTabela(dbName = "descricao", setJavaName = "setDescricao")
    private String descricao;

    @ColunaTabela(dbName = "valor", setJavaName = "setValor")
    private BigDecimal valor;

    @ColunaTabela(dbName = "categoria", setJavaName = "setCategoria") // Novo campo
    private String categoria;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}