package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionTimeDayOfMonthTest {
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
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        for (int i = 0; i < 30; i++)
        {
            expectedDateTime = expectedDateTime.plusDays(1);
            DateTime executionDataTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
            assertEquals(expectedDateTime, nextExecutionDateTime);
        }

        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
    }

    @Test
    public void testSingle() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 10 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 10, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMany() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 10,15,20 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 10, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(5);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(15, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(5);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(20, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(20);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 10-12 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 10, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(28);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testPeriodEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 28-30 * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 28, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(28, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(29, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(30, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(28);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(28, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testDoubleRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 10-12,19-20 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 10, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(7);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(19, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(20, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(20);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testOverlapRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1-3,28-30 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(25);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(28, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(29, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(30, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testPattern() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 9/9 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 9, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(9);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(18, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(9);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(27, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(12);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRangePattern() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 9-18/4 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 9, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(4);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(13, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(4);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(17, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusDays(21);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.dayOfMonth()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}
