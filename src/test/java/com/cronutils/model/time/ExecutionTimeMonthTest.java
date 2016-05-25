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

        for (int i = 0; i < 12; i++) {
            expectedDateTime = expectedDateTime.plusMonths(1);
            DateTime executionDateTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
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
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
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
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(5, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(2);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(2);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
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
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(9);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(7, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(8, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 10-12 * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 10, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(10);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMultiRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0 0 1 2-4,10-11 *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 20, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 10, 1, 0, 0, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(3);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(4, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(6);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
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
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(3, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMonths(7);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.monthOfYear()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}