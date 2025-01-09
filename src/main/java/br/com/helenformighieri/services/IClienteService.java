package br.com.helenformighieri.services;

import br.com.helenformighieri.domain.Cliente;
import br.com.helenformighieri.exceptions.DAOException;
import br.com.helenformighieri.services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {

    Cliente buscarPorCPF(Long cpf) throws DAOException;
}