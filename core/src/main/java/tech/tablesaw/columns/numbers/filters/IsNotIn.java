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

package tech.tablesaw.columns.numbers.filters;

import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.columns.ColumnReference;
import tech.tablesaw.filtering.ColumnFilter;
import tech.tablesaw.selection.Selection;

import java.util.List;

/**
 * Implements EqualTo testing for Number Columns
 */
public class IsNotIn extends ColumnFilter {

    private final double[] doubles;

    public IsNotIn(ColumnReference reference, List<Number> values) {
        super(reference);
        doubles = values.stream().mapToDouble(Number::doubleValue).toArray();
    }

    public IsNotIn(ColumnReference reference, double... values) {
        super(reference);
        this.doubles = values;
    }

    @Override
    public Selection apply(Table relation) {
        return apply(relation.column(columnReference().getColumnName()));
    }

    @Override
    public Selection apply(Column columnBeingFiltered) {
        NumberColumn numberColumn = (NumberColumn) columnBeingFiltered;
        return numberColumn.isNotIn(doubles);
    }
}
