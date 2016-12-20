package org.wesss.database_access.dao;

import org.wesss.database_access.domain.DatabaseTable;
import org.wesss.database_access.exceptions.DAOException;
import org.wesss.general_utils.exceptions.NotImplementedException;

public class InMemoryDAO implements DatabaseAccessObject {

    // TODO implement InMemoryDAO

    @Override
    public void createTable(DatabaseTable table) throws DAOException {
        throw new NotImplementedException();
    }

    @Override
    public boolean tableExists(DatabaseTable table) {
        throw new NotImplementedException();
    }

    @Override
    public void dropTable(DatabaseTable table) {
        throw new NotImplementedException();
    }
}
