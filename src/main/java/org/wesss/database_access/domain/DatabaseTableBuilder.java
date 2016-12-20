package org.wesss.database_access.domain;

import org.wesss.general_utils.exceptions.NotImplementedException;

import java.util.ArrayList;

public class DatabaseTableBuilder {

    private String name;
    private ArrayList<DatabaseColumn> columns;

    /* public builder interface */

    public static DatabaseTableBuilder buildTable(String name) {
        return new DatabaseTableBuilder(name);
    }

    public static DatabaseColumn buildColumn(String name) {
        return new DatabaseColumn(name);
    }

    public DatabaseTableBuilder with(DatabaseColumn column) {
        DatabaseTableBuilder newTable = new DatabaseTableBuilder(this);
        newTable.addColumn(column);

        return newTable;
    }

    public DatabaseTable build() {
        return new DatabaseTable(name, columns);
    }

    /* private builder field access */

    private DatabaseTableBuilder(String name) {
        this.name = name;
        this.columns = new ArrayList<>();
    }

    private DatabaseTableBuilder(DatabaseTableBuilder other) {
        this.name = other.name;
        this.columns = new ArrayList<>(other.columns);
    }

    private void addColumn(DatabaseColumn column) {
        columns.add(column);
    }
}
