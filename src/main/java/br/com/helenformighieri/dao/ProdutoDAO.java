package br.com.helenformighieri.dao;

import br.com.helenformighieri.dao.generic.GenericDAO;
import br.com.helenformighieri.domain.Produto;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.MaisDeUmRegistroException;
import br.com.helenformighieri.exceptions.TableException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class ProdutoDAO extends GenericDAO<Produto, String> implements IProdutoDAO {

    public ProdutoDAO() {
        super();
    }

    @Override
    public Class<Produto> getTipoClasse() {
        return Produto.class;
    }

    @Override
    public void atualiarDados(Produto entity, Produto entityCadastrado) {
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setDescricao(entity.getDescricao());
        entityCadastrado.setValor(entity.getValor());
        entityCadastrado.setCategoria(entity.getCategoria()); // Novo campo
    }

    @Override
    protected String getQueryInsercao() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO ");
        sb.append("(ID, CODIGO, NOME, DESCRICAO, VALOR, CATEGORIA)"); // Novo campo
        sb.append("VALUES (nextval('sq_produto'),?,?,?,?,?)"); // Novo campo
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Produto entity) throws SQLException {
        stmInsert.setString(1, entity.getCodigo());
        stmInsert.setString(2, entity.getNome());
        stmInsert.setString(3, entity.getDescricao());
        stmInsert.setBigDecimal(4, entity.getValor());
        stmInsert.setString(5, entity.getCategoria()); // Novo campo
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_PRODUTO WHERE CODIGO = ?";
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stmExclusao, String valor) throws SQLException {
        stmExclusao.setString(1, valor);
    }

    @Override
    protected String getQueryAtualizacao() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO ");
        sb.append("SET NOME = ?,");
        sb.append("DESCRICAO = ?,");
        sb.append("VALOR = ?,");
        sb.append("CATEGORIA = ?"); // Novo campo
        sb.append(" WHERE CODIGO = ?");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Produto entity) throws SQLException {
        stmUpdate.setString(1, entity.getNome());
        stmUpdate.setString(2, entity.getDescricao());
        stmUpdate.setBigDecimal(3, entity.getValor());
        stmUpdate.setString(4, entity.getCategoria()); // Novo campo
        stmUpdate.setString(5, entity.getCodigo());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
        stmSelect.setString(1, valor);
    }

    @Override
    public Collection<Produto> buscarTodos() throws DAOException {
        return super.buscarTodos();
    }
}