package br.com.helenformighieri.dao.generic;

import br.com.helenformighieri.anotacao.ColunaTabela;
import br.com.helenformighieri.anotacao.TipoChave;
import br.com.helenformighieri.dao.Persistente;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.MaisDeUmRegistroException;
import br.com.helenformighieri.exceptions.TableException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;
import br.com.helenformighieri.dao.generic.jdbc.ConnectionFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class GenericDAO<T extends Persistente, E extends Serializable> implements IGenericDAO<T, E> {

    public abstract Class<T> getTipoClasse();

    public abstract void atualiarDados(T entity, T entityCadastrado);

    protected abstract String getQueryInsercao();

    protected abstract String getQueryExclusao();

    protected abstract String getQueryAtualizacao();

    protected abstract void setParametrosQueryInsercao(PreparedStatement stmInsert, T entity) throws SQLException;

    protected abstract void setParametrosQueryExclusao(PreparedStatement stmDelete, E valor) throws SQLException;

    protected abstract void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, T entity) throws SQLException;

    protected abstract void setParametrosQuerySelect(PreparedStatement stmUpdate, E valor) throws SQLException;

    public GenericDAO() {

    }

    public E getChave(T entity) throws TipoChaveNaoEncontradaException {
        Field[] fields = entity.getClass().getDeclaredFields();
        E returnValue = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(TipoChave.class)) {
                TipoChave tipoChave = field.getAnnotation(TipoChave.class);
                String nomeMetodo = tipoChave.value();
                try {
                    Method method = entity.getClass().getMethod(nomeMetodo);
                    returnValue = (E) method.invoke(entity);
                    break;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new TipoChaveNaoEncontradaException("Erro ao recuperar a chave do objeto", e);
                }
            }
        }
        if (returnValue == null) {
            String msg = "Chave principal do objeto " + entity.getClass() + " não encontrada";
            throw new TipoChaveNaoEncontradaException(msg);
        }
        return returnValue;
    }

    @Override
    public Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement(getQueryInsercao());
            setParametrosQueryInsercao(stm, entity);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DAOException("Erro ao cadastrar o objeto", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public void excluir(E valor) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement(getQueryExclusao());
            setParametrosQueryExclusao(stm, valor);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir o objeto", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement(getQueryAtualizacao());
            setParametrosQueryAtualizacao(stm, entity);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao alterar o objeto", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public T consultar(E valor) throws MaisDeUmRegistroException, TableException, DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement(getQuerySelect());
            setParametrosQuerySelect(stm, valor);
            rs = stm.executeQuery();
            if (rs.next()) {
                T entity = getTipoClasse().newInstance();
                for (Field field : getTipoClasse().getDeclaredFields()) {
                    if (field.isAnnotationPresent(ColunaTabela.class)) {
                        ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                        String nomeColuna = coluna.dbName();
                        Object valorColuna = rs.getObject(nomeColuna);
                        Method metodoSet = getTipoClasse().getMethod(coluna.setJavaName(), field.getType());
                        metodoSet.invoke(entity, valorColuna);
                    }
                }
                return entity;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new DAOException("Erro ao consultar o objeto", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
        return null;
    }

    @Override
    public Collection<T> buscarTodos() throws DAOException {
        List<T> lista = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement(getQuerySelectAll());
            rs = stm.executeQuery();
            while (rs.next()) {
                T entity = getTipoClasse().newInstance();
                for (Field field : getTipoClasse().getDeclaredFields()) {
                    if (field.isAnnotationPresent(ColunaTabela.class)) {
                        ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                        String nomeColuna = coluna.dbName();
                        Object valorColuna = rs.getObject(nomeColuna);
                        Method metodoSet = getTipoClasse().getMethod(coluna.setJavaName(), field.getType());
                        metodoSet.invoke(entity, valorColuna);
                    }
                }
                lista.add(entity);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new DAOException("Erro ao buscar todos os objetos", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
        return lista;
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}