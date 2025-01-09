import br.com.helenformighieri.dao.IVendaDAO;
import br.com.helenformighieri.dao.VendaDAO;
import br.com.helenformighieri.domain.Cliente;
import br.com.helenformighieri.domain.Produto;
import br.com.helenformighieri.domain.Venda;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.MaisDeUmRegistroException;
import br.com.helenformighieri.exceptions.TableException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

public class VendaDAOTeste {

    private IVendaDAO vendaDAO;
    private Venda venda;
    private Cliente cliente;
    private Produto produto;

    @Before
    public void setUp() {
        vendaDAO = new VendaDAO();
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        produto = new Produto();
        produto.setId(1L);
        produto.setCodigo("P123");
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");
        produto.setValor(BigDecimal.valueOf(100.0));
        venda = new Venda();
        venda.setCodigo("V123");
        venda.setCliente(cliente);
        venda.setDataVenda(Instant.now());
        venda.setStatus(Venda.Status.INICIADA);
        venda.adicionarProduto(produto, 2);
    }

    @Test
    public void testCadastrar() throws TipoChaveNaoEncontradaException, DAOException {
        Boolean result = vendaDAO.cadastrar(venda);
        Assert.assertTrue(result);
    }

    @Test
    public void testConsultar() throws DAOException, MaisDeUmRegistroException, TableException {
        Venda vendaConsultada = vendaDAO.consultar(venda.getCodigo());
        Assert.assertNotNull(vendaConsultada);
    }

    @Test
    public void testBuscarTodos() throws DAOException {
        Collection<Venda> vendas = vendaDAO.buscarTodos();
        Assert.assertNotNull(vendas);
        Assert.assertFalse(vendas.isEmpty());
    }

    @Test
    public void testFinalizarVenda() throws TipoChaveNaoEncontradaException, DAOException {
        vendaDAO.finalizarVenda(venda);
        Venda vendaFinalizada = vendaDAO.consultar(venda.getCodigo());
        Assert.assertEquals(Venda.Status.CONCLUIDA, vendaFinalizada.getStatus());
    }

    @Test
    public void testCancelarVenda() throws TipoChaveNaoEncontradaException, DAOException {
        vendaDAO.cancelarVenda(venda);
        Venda vendaCancelada = vendaDAO.consultar(venda.getCodigo());
        Assert.assertEquals(Venda.Status.CANCELADA, vendaCancelada.getStatus());
    }
}