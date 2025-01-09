package br.com.helenformighieri.dao;

import br.com.helenformighieri.dao.generic.IGenericDAO;
import br.com.helenformighieri.domain.Venda;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;

import java.util.Collection;

public interface IVendaDAO extends IGenericDAO<Venda, String> {

    void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    Collection<Venda> buscarTodos() throws DAOException;
}