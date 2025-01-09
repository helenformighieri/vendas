import br.com.helenformighieri.dao.IProdutoDAO;
import br.com.helenformighieri.dao.ProdutoDAO;
import br.com.helenformighieri.domain.Produto;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.MaisDeUmRegistroException;
import br.com.helenformighieri.exceptions.TableException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

public class ProdutoDAOTeste {

    private IProdutoDAO produtoDAO;
    private Produto produto;

    @Before
    public void setUp() {
        produtoDAO = new ProdutoDAO();
        produto = new Produto();
        produto.setCodigo("P123");
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");
        produto.setValor(BigDecimal.valueOf(100.0));
    }

    @Test
    public void testCadastrar() throws TipoChaveNaoEncontradaException, DAOException {
        Boolean result = produtoDAO.cadastrar(produto);
        Assert.assertTrue(result);
    }

    @Test
    public void testConsultar() throws DAOException, MaisDeUmRegistroException, TableException {
        Produto produtoConsultado = produtoDAO.consultar(produto.getCodigo());
        Assert.assertNotNull(produtoConsultado);
    }

    @Test
    public void testAlterar() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        produto.setNome("Produto Teste Alterado");
        produtoDAO.alterar(produto);
        Produto produtoAlterado = produtoDAO.consultar(produto.getCodigo());
        Assert.assertEquals("Produto Teste Alterado", produtoAlterado.getNome());
    }

    @Test
    public void testExcluir() throws DAOException, MaisDeUmRegistroException, TableException {
        produtoDAO.excluir(produto.getCodigo());
        Produto produtoExcluido = produtoDAO.consultar(produto.getCodigo());
        Assert.assertNull(produtoExcluido);
    }

    @Test
    public void testBuscarTodos() throws DAOException {
        Collection<Produto> produtos = produtoDAO.buscarTodos();
        Assert.assertNotNull(produtos);
        Assert.assertFalse(produtos.isEmpty());
    }
}