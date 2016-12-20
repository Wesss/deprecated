package org.wesss.database_access.domain;

import org.wesss.general_utils.annotations.Nullable;

public class DatabaseColumnBuilder {

    private String name;
    private DataType dataType;
    @Nullable
    private Integer limit;

    /* public builder interface */

    public static DatabaseColumnBuilder buildIntegerColumn(String name) {
        return new DatabaseColumnBuilder(name, DataType.INTEGER);
    }

    public DatabaseColumn build() {
        return new DatabaseColumn(name, dataType, limit);
    }

    /* private builder field access */

    private DatabaseColumnBuilder(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
        limit = null;
    }

    private DatabaseColumnBuilder(DatabaseColumnBuilder other) {
        this.name = other.name;
        this.dataType = other.dataType;
        this.limit = other.limit;
    }
}
