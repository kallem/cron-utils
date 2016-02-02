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

        for (int i = 0; i < 7; i++)
        {
            expectedDateTime = expectedDateTime.plusDays(1);
            DateTime executionDataTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
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
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
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
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(4);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
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
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(5);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testPeriodEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * FRI-SUN");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 4, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(5);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testDoubleRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * MON-TUE,THU-SAT");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
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
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(6, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testPattern() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * MON/3");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 3, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(3);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(3);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRangePattern() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 * * MON-SAT/2");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 2, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(3);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(2);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfWeek()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}
