package br.com.helenformighieri.dao.factory;

import br.com.helenformighieri.domain.Cliente;
import br.com.helenformighieri.domain.Venda;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaFactory {

    public static Venda convert(ResultSet rs) throws SQLException {
        Cliente cliente = ClienteFactory.convert(rs);
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setCodigo(rs.getString("CODIGO"));
        venda.setId(rs.getLong("ID_VENDA"));
        venda.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
        venda.setDataVenda(rs.getTimestamp("DATA_VENDA").toInstant());
        venda.setStatus(Venda.Status.getByName(rs.getString("STATUS_VENDA")));
        return venda;
    }
}