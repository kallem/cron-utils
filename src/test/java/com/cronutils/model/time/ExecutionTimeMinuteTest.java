package com.cronutils.model.time;

import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionTimeMinuteTest {
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
        Cron cron = parser.parse("0 * * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 13, 39, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(39, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        for (int i = 0; i < 60; i++) {
            expectedDateTime = expectedDateTime.plusMinutes(1);
            DateTime executionDateTime = nextExecutionDateTime;
            nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
            assertEquals(expectedDateTime, nextExecutionDateTime);
        }

        assertEquals(39, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
    }

    @Test
    public void testSingle() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 10 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 14, 10, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(60);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMany() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 10,15,20 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 14, 10, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(5);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(15, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(5);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(20, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(50);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 10-12 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 14, 10, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(58);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testEndRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 57-59 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 13, 57, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(57, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(58, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(59, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(58);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(57, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testMultiRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 10-12,31-32 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 14, 10, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(11, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(12, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(19);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(31, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(32, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(38);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(10, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }

    @Test
    public void testOverlapRange() {
        CronParser parser = new CronParser(cronDefinition);
        Cron cron = parser.parse("0 0-2,57-59 * * * *");

        DateTime startDateTime = new DateTime(2015, 8, 31, 13, 38, 0, 0);
        DateTime expectedDateTime = new DateTime(2015, 8, 31, 13, 57, 0, 0);

        ExecutionTime executionTime = ExecutionTime.forCron(cron);

        DateTime nextExecutionDateTime = executionTime.nextExecution(startDateTime);
        assertEquals(57, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        DateTime executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(58, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(59, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(0, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(1, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(1);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(2, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);

        expectedDateTime = expectedDateTime.plusMinutes(55);
        executionDateTime = nextExecutionDateTime;
        nextExecutionDateTime = executionTime.nextExecution(executionDateTime);
        assertEquals(57, nextExecutionDateTime.get(DateTimeFieldType.minuteOfHour()));
        assertEquals(expectedDateTime, nextExecutionDateTime);
    }
}
