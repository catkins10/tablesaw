/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.tablesaw.columns.strings.filters;

import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.strings.StringColumnReference;
import tech.tablesaw.filtering.ColumnFilter;
import tech.tablesaw.selection.Selection;

import java.util.Collection;

/**
 * Implements EqualTo testing for Category and Text Columns
 */
public class IsIn extends ColumnFilter {

    private final String[] filters;

    public IsIn(StringColumnReference reference, Collection<String> strings) {
        super(reference);
        this.filters = strings.toArray(new String[strings.size()]);
    }

    public IsIn(StringColumnReference reference, String... strings) {
        super(reference);
        this.filters = strings;
    }

    public Selection apply(Table relation) {
        return apply(relation.column(columnReference().getColumnName()));
    }

    @Override
    public Selection apply(Column columnBeingFiltered) {
        StringColumn stringColumn = (StringColumn) columnBeingFiltered;
        return stringColumn.isIn(filters);
    }
}
