package droolstutorial.service;

import java.util.ArrayList;
import java.util.List;

import droolstutorial.Cliente;

public class ClienteService {

	public List<Cliente> getListCliente() {
        List<Cliente> result = new ArrayList<Cliente>();
        result.add(new Cliente("Jo�o", "Ferreira", "Fr"));
        result.add(new Cliente("Jo�o", "Ferraz", "GB"));
        result.add(new Cliente("Jo�o", "Ferreira", "GB"));

        return result;
    }
}