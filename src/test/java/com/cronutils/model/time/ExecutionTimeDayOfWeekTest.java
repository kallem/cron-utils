package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionTimeDayOfWeekTest {
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
        Cron cron = parser.parse("0 0 0 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        for (int i = 0; i < 7; i++) {
            expectedDateTime = expectedDateTime.plusDays(1);
            DateTime executionDateTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
            assertEquals(expectedDateTime, nextExecutionDateTime);
        }

        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
    }

    @Test
    public void testSingle() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * WED");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 2, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(7);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMany() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * WED,FRI,SAT");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 2, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(4);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * WED-FRI");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 2, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(5);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * FRI-SUN");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 4, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(5);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMultiRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * MON-TUE,THU-SAT");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testOverlapRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * MON-TUE,SAT-SUN");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(4);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}
