Columns
=======

Tablesaw is all about tables and tables are made of columns. To truly understand your data, you'll often need to work with a single column. Tablesaw provides a large collection of tools for that, and we'll cover the basics here. Each column type also has its own section that goes into greater detail. 

Let's start with a definition. A column is a named vector of data, all of a single type. Some elements may be missing, and it's important to deal with those. We show how to do that in a little bit. 

Here are the supported column types:

* Boolean, or true and false values
* String, as in "Hello, World," or "RN183-15F";
* Number 
* Date: A "local date". That is, a date without a timezone. April 10, 2018, for example.
* DateTime: A local date and time combined. April 10, 2018 at 9:07.
* Time: A local time, like 12:47:03

There is currently one specific type of number column, called DoubleColumn. As you'd expect, it holds 8-byte floating point numbers. 

We'll begin by looking at the operations that are common to all column types. 

#### Create a Column

Columns are usually created by calling one of the static create() methods defined on the class. For example, you can create an empty date column as follows:

```Java
DateColumn column = DateColumn.create("test");
```

The new column has the name "test", and a ColumnType of LOCAL_DATE. Names are important. We often refer to a column by its name, so we don't have to declare a variable to access it. All the columns within a given table must have unique names. You can always get the name of a column by calling *name()*, and its type by calling *type()*.

To create a column with data, you will generally read the data from a file or database, but you can also initialize the column with an array:

```Java
double[] values = {1, 2, 3, 7, 9.44242, 11};
DoubleColumn column = DoubleColumn.create("my numbers", values);
```

Once you have a column, 

#### Add an element to the end of the column

    DateColumn.append(LocalDate.of(2016, 2, 28));

#### Column IO

Generally, you will save data as table, but you may also want to save just a single column. You can do this using

Save a column as a CSV

### Other common operations:

Columns do all the things you expect, here’s an incomplete list of standard operations:

```Java
size()                          // returns the number of elements
isEmpty()                       // returns true if column has no data; false otherwise
first() and last()              // returns the first and last elements, respectively
first(n) and last(n)            // returns the first and last n elements
max() and min()                 // returns the largest and smallest elements
top(n) and bottom(n)            // returns the n largest and smallest elements
name()                          // returns the name of the column
type()                          // returns the ColumnType, e.g. LOCAL_DATE
print()                         // returns a String representation of the column
copy()						 	// returns a deep copy of the column
emptyCopy()						// returns a column of the same type and name, but no data
unique()
countUnique()
asSet()
summary()
void sortAscending()			// sorts the column in ascending order 
void sortDescending()			// sorts the column in ascending order 
Column append(value)            // Appends a single value 
append(Column)                  // Appends the data in other column to this one
```

These operations are available on nearly all column types, including date columns. Each operates on an entire column. 
To operate on the values of a column, you have two choices. You can work with individual values, or use column-wise operations to work with all the values in a column in the same way. To work with individual values, you can just iterate over the column:

    DateColumn weekLater = DateColumn.create("Week Later");
    for (LocalDate date: dates) {
       weekLater.append(date.plusDays(7));
    }

Just about anything you can do with an individual LocalDate you can do with an entire DateColumn, using column-wise operations. For example, the above loop could be written as:

    DateColumn weekLater = dates.plusDays(7);

This is an example of a mapping function. You can see the full list of date mapping functions in the interface DateMapUtils, most of the methods deal with adding and subtracting units of time (days, weeks, months, etc), and calculating the column-wise differences between two date columns.

### Filtering

You can filter two ways. The first is with the built-in predicates, like IsMonday

See the end of this post for a full list of the built-in predicates for LocalDateColumn.

You can filter a date column using #selectIf(IntPredicate).  For example, if you want only those dates after February 28, 2016.

    LocalDatePredicate after_2_28 = new LocalDatePredicate() {
      LocalDate date = LocalDate.of(2016, 2, 28);
      @Override
      public boolean test(LocalDate i) {
        return i.isAfter(date);
      }
    };

which you can use as:

    DateColumn filtered = dates.selectIf(after_2_28);

#### Built-in Date Predicates

There are numerous built-in date predicates. For example:

    DateColumn filtered = dates.isMonday();
    DateColumn filtered = dates.isInQ2();
    DateColumn filtered = dates.isLastDayOfTheMonth();

You can find a full list in the JavaDoc for DateColumn.

### Grouping

This code creates a splitter that groups dates by month. First we get a Splitter to divide the data.

    LocalDateSplitter monthSplitter = new LocalDateSplitter() {
    
      @Override
      public String groupKey(LocalDate date) {
        return groupKey(PackedLocalDate.pack(date));
      }
    
      @Override
      public String groupKey(int packedLocalDate) {
        return PackedLocalDate.getMonth(packedLocalDate).toString();
      }
    };
    LocalDateColumnGroup group 
        = new LocalDateColumnGroup(column, monthSplitter);
    List<LocalDateColumn> columns = group.getSubColumns();

### Aggregating

### Cleaning data

### Correcting values

The easiest way to correct values is using setIf(). 

### Formatting 

You can print data as individual values, columns or tables. The output format can be controlled by setting a type
 specific formatter on a column. For example, to change how numbers are displayed you can call setPrintFormatter()
 on a NumberColumn, passing in a NumberColumnFormatter. Each formatter serves two functions, displaying true values and handling of 
 missing ones. NumberColumnFormatter has several pre-configured options, including printing as currency or percents.


See the Tables documentation for how to add and remove columns

 