package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionTimeHourTest {
    private final CronDefinition cronDefinition = CronDefinitionBuilder.defineCron()
                                                          .withSeconds().and()
                                                          .withMinutes().and()
                                                          .withHours().and()
                                                          .withDayOfMonth().and()
                                                          .withMonth().and()
                                                          .withDayOfWeek().withValidRange(1, 7).withMondayDoWValue(1).withIntMapping(7, 1).and()
                                                          .withYear().and()
                                                          .lastFieldOptional()
                                                          .instance();

    @Test
    public void testAll() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 14, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(14, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        for (int i = 0; i < 24; i++) {
            expectedDateTime = expectedDateTime.plusHours(1);
            DateTime executionDataTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
            assertEquals(expectedDateTime, nextExecutionDateTime);
        }

        assertEquals(14, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
    }

    @Test
    public void testSingle() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 10 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 10, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMany() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 10,15,20 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 10, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(5);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(15, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(5);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(20, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(14);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 10-12 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 10, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(22);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 21-23 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 21, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(21, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(22, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(23, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(22);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(21, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMultiRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 10-12,19-20 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 10, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(7);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(19, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(20, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(14);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testOverlapRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0-2,21-23 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 21, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(21, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(22, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(23, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(0, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusHours(19);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(21, nextExecutionDateTime.get(DateTimeFieldType.hourOfDay()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}
