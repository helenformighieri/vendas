package br.com.helenformighieri.dao.generic;

import br.com.helenformighieri.dao.Persistente;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.MaisDeUmRegistroException;
import br.com.helenformighieri.exceptions.TableException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO <T extends Persistente, E extends Serializable> {

    public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public void excluir(E valor) throws DAOException;

    public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    public T consultar(E valor) throws MaisDeUmRegistroException, TableException, DAOException;

    public Collection<T> buscarTodos() throws DAOException;
}