import br.com.helenformighieri.dao.ClienteDAO;
import br.com.helenformighieri.dao.IClienteDAO;
import br.com.helenformighieri.domain.Cliente;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.exceptions.MaisDeUmRegistroException;
import br.com.helenformighieri.exceptions.TableException;
import br.com.helenformighieri.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class ClienteDAOTeste {

    private IClienteDAO clienteDAO;
    private Cliente cliente;

    @Before
    public void setUp() {
        clienteDAO = new ClienteDAO();
        cliente = new Cliente();
        cliente.setNome("Teste");
        cliente.setCpf(12345678901L);
        cliente.setTel(987654321L);
        cliente.setEnd("Rua Teste");
        cliente.setNumero(123);
        cliente.setCidade("Cidade Teste");
        cliente.setEstado("Estado Teste");
    }

    @Test
    public void testCadastrar() throws TipoChaveNaoEncontradaException, DAOException {
        Boolean result = clienteDAO.cadastrar(cliente);
        Assert.assertTrue(result);
    }

    @Test
    public void testConsultar() throws DAOException, MaisDeUmRegistroException, TableException {
        Cliente clienteConsultado = clienteDAO.consultar(cliente.getCpf());
        Assert.assertNotNull(clienteConsultado);
    }

    @Test
    public void testAlterar() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        cliente.setNome("Teste Alterado");
        clienteDAO.alterar(cliente);
        Cliente clienteAlterado = clienteDAO.consultar(cliente.getCpf());
        Assert.assertEquals("Teste Alterado", clienteAlterado.getNome());
    }

    @Test
    public void testExcluir() throws DAOException, MaisDeUmRegistroException, TableException {
        clienteDAO.excluir(cliente.getCpf());
        Cliente clienteExcluido = clienteDAO.consultar(cliente.getCpf());
        Assert.assertNull(clienteExcluido);
    }

    @Test
    public void testBuscarTodos() throws DAOException {
        Collection<Cliente> clientes = clienteDAO.buscarTodos();
        Assert.assertNotNull(clientes);
        Assert.assertFalse(clientes.isEmpty());
    }
}