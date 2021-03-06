package tech.tablesaw.columns.times;

import org.junit.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static tech.tablesaw.api.TimeColumn.create;
import static tech.tablesaw.columns.datetimes.fillers.TemporalRangeIterable.range;

public class TimeFillersTest {

    private void testValues(Iterable<LocalTime> times, LocalTime... expected) {
        int num = 0;
        for (LocalTime value : times) {
            assertEquals(expected[num], value);
            num++;
        }
        assertEquals(expected.length, num);
    }

    @Test
    public void testFromToBy() {
        testValues(create("times", new LocalTime[5]).fillWith(range(LocalTime.of(12, 30), // hour, minute
                LocalTime.of(21, 30), 2, ChronoUnit.HOURS)), LocalTime.of(12, 30), // year, month, day, hour, minute
                LocalTime.of(14, 30), LocalTime.of(16, 30), LocalTime.of(18, 30), LocalTime.of(20, 30));
    }
}
