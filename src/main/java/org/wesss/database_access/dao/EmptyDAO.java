package org.wesss.database_access.dao;

import org.wesss.database_access.domain.DatabaseTable;
import org.wesss.database_access.exceptions.DAOException;

public class EmptyDAO implements DatabaseAccessObject {

    @Override
    public void createTable(DatabaseTable table) throws DAOException {

    }

    @Override
    public boolean tableExists(DatabaseTable table) {
        return false;
    }

    @Override
    public void dropTable(DatabaseTable table) {

    }
}
