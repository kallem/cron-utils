package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecutionTimeYearTest {
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
        Cron cron = parser.parse("0 0 0 1 1 ? *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2016, 1, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2016, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2017, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2018, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testSingle() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 1 * 2017");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2017, 1, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2017, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
        DateTime executionDataTime = nextExecutionDateTime;

        try {
            executionTime.nextExecution(executionDataTime);
        } catch (final IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMany() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 1 * 2017,2019,2020");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2017, 1, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2017, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(2);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2019, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2020, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        try {
            executionTime.nextExecution(executionDataTime);
        } catch (final IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 1 * 2017-2019");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2017, 1, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2017, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2018, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2019, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        try {
            executionTime.nextExecution(executionDataTime);
        } catch (final IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMultiRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 1 * 2017-2019,2024-2025");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2017, 1, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2017, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2018, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2019, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(5);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2024, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusYears(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2025, nextExecutionDateTime.get(DateTimeFieldType.year()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        try {
            executionTime.nextExecution(executionDataTime);
        } catch (final IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}
