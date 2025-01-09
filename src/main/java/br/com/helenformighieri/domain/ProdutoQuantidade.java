package br.com.helenformighieri.domain;

import br.com.helenformighieri.anotacao.ColunaTabela;
import br.com.helenformighieri.anotacao.Tabela;

import java.math.BigDecimal;

@Tabela("TB_PRODUTO_QUANTIDADE")
public class ProdutoQuantidade {

    @ColunaTabela(dbName = "id", setJavaName = "setId")
    private Long id;

    @ColunaTabela(dbName = "id_produto_fk", setJavaName = "setProduto")
    private Produto produto;

    @ColunaTabela(dbName = "quantidade", setJavaName = "setQuantidade")
    private Integer quantidade;

    @ColunaTabela(dbName = "valor_total", setJavaName = "setValorTotal")
    private BigDecimal valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionar(Integer quantidade) {
        this.quantidade += quantidade;
        recalcularValorTotal();
    }

    public void remover(Integer quantidade) {
        this.quantidade -= quantidade;
        recalcularValorTotal();
    }

    private void recalcularValorTotal() {
        this.valorTotal = produto.getValor().multiply(BigDecimal.valueOf(this.quantidade));
    }
}