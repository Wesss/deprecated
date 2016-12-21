package org.wesss.database_access.domain;

import org.wesss.general_utils.annotations.Nullable;

public class DatabaseColumn {

    private String name;
    private DataType dataType;
    @Nullable
    private Integer limit;

    protected DatabaseColumn(String name, DataType dataType, @Nullable Integer limit) {
        this.name = name;
        this.dataType = dataType;
        this.limit = limit;
    }

}
