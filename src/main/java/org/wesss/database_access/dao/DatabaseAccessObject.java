package org.wesss.database_access.dao;

import org.wesss.database_access.domain.DatabaseTable;
import org.wesss.database_access.exceptions.DAOException;

public interface DatabaseAccessObject {

    public void createTable(DatabaseTable table) throws DAOException;

    public boolean tableExists(DatabaseTable table);

    public void dropTable(DatabaseTable table);
}
