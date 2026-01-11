package business;

import dao.ClientDao;

public class ClientBusiness {
    private final ClientDao clientDao;

    public ClientBusiness(ClientDao clientDao) {
        this.clientDao = clientDao;
    }
}
