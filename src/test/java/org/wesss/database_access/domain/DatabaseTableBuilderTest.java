package org.wesss.database_access.domain;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.wesss.database_access.domain.DatabaseColumnBuilder.*;
import static org.wesss.database_access.domain.DatabaseTableBuilder.*;
import static org.wesss.general_utils.collection.CollectionFactory.*;

public class DatabaseTableBuilderTest {

    @Test
    public void singleColumnTable() {
        DatabaseColumn column = buildIntegerColumn("HelloData")
                .build();

        DatabaseTable table = buildTable("HelloTable")
                .with(column)
                .build();

        ArrayList<DatabaseColumn> expectedColumns = arrayListOf(column);
        assertThat(table.getColumns(), is(expectedColumns));
    }

    // TODO databasetablebuildertest: multicolumn, column with limit
}
