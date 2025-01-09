package br.com.helenformighieri.services;

import br.com.helenformighieri.dao.IProdutoDAO;
import br.com.helenformighieri.dao.generic.IGenericDAO;
import br.com.helenformighieri.domain.Produto;
import br.com.helenformighieri.services.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {

    public ProdutoService(IProdutoDAO dao) {
        super((IGenericDAO<Produto, String>) dao);
    }

}