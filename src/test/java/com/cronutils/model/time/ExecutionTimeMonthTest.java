package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionTimeMonthTest {
    private final CronDefinition cronDefinition = CronDefinitionBuilder.defineCron()
                                            .withSeconds().and()
                                            .withMinutes().and()
                                            .withHours().and()
                                            .withDayOfMonth().and()
                                            .withMonth().and()
                                            .withDayOfWeek().withValidRange(0, 7).withMondayDoWValue(1).withIntMapping(7, 0).and()
                                            .withYear().and()
                                            .lastFieldOptional()
                                            .instance();

    @Test
    public void testAll() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        for (int i = 0; i < 12; i++)
        {
            expectedDateTime = expectedDateTime.plusMonths(1);
            DateTime executionDataTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
            assertEquals(expectedDateTime, nextExecutionDateTime);
        }
    }

    @Test
    public void testSingle() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 10 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 10, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(12);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMany() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 5,7,9 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(8);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(2);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(2);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 7-10 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(9, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(9);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(8, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testPeriodEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 10-12 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 10, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(10);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testDoubleRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 2-4,10-11 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 10, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(3);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(6);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testOverlapRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 1-3,10-12 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 10, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(7);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testPattern() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 2/5 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 2, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(5);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(5);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(2);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRangePattern() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 2-9/3 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 9, 2, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(3);
        DateTime executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(3);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(8, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(6);
        executionDataTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDataTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}
