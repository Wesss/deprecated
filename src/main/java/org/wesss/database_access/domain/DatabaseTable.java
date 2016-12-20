package org.wesss.database_access.domain;

import java.util.ArrayList;

public class DatabaseTable {

    private String name;
    private ArrayList<DatabaseColumn> columns;

    protected DatabaseTable(String name, ArrayList<DatabaseColumn> columns) {
        this.name = name;
        this.columns = columns;
    }

    public ArrayList<DatabaseColumn> getColumns() {
        return new ArrayList<>(columns);
    }
}
