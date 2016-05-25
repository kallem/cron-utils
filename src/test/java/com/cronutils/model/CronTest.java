package com.cronutils.model;

import com.cronutils.mapper.CronMapper;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronField;
import com.cronutils.model.field.CronFieldName;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.parser.CronParser;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/*
 * Copyright 2015 jmrozanec
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CronTest {

    private Cron cron;
    private CronFieldName testName;
    private List<CronField> fields;
    @Mock
    private CronField mockField;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        testName = CronFieldName.SECOND;
        when(mockField.getField()).thenReturn(testName);
        fields = Lists.newArrayList();
        fields.add(mockField);
        cron = new Cron(mock(CronDefinition.class), fields);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullFieldsParameter() throws Exception {
        new Cron(mock(CronDefinition.class),null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorNullDefinitionParameter() throws Exception {
        new Cron(null, fields);
    }

    @Test
    public void testRetrieveNonNullParameter() throws Exception {
        assertEquals(mockField, cron.retrieve(testName));
    }

    @Test(expected = NullPointerException.class)
    public void testRetrieveNullParameter() throws Exception {
        cron.retrieve(null);
    }

    @Test
    public void testRetrieveFieldsAsMap() throws Exception {
        assertNotNull(cron.retrieveFieldsAsMap());
        assertEquals(1, cron.retrieveFieldsAsMap().size());
        assertTrue(cron.retrieveFieldsAsMap().containsKey(testName));
        assertEquals(mockField, cron.retrieveFieldsAsMap().get(testName));
    }

    @Test
    public void testAsString() throws Exception {
        String expressionString = "somestring";
        FieldExpression mockFieldExpression = mock(FieldExpression.class);
        when(mockField.getExpression()).thenReturn(mockFieldExpression);
        when(mockFieldExpression.asString()).thenReturn(expressionString);
        assertEquals(expressionString, cron.asString());
    }

    @Test
    public void testEquivalent(){
        CronDefinition unixcd = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX);
        CronDefinition quartzcd = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        CronParser unix = new CronParser(unixcd);
        CronParser quartz = new CronParser(quartzcd);
        Cron cron1 = unix.parse("* * * * MON");
        Cron cron2 = unix.parse("*/1 * * * 1");
        Cron cron3 = unix.parse("0 * * * *");
        Cron cron4 = quartz.parse("0 * * ? * MON *");

        assertTrue(cron1.equivalent(CronMapper.sameCron(unixcd), cron2));
        assertFalse(cron1.equivalent(CronMapper.sameCron(unixcd), cron3));
        assertTrue(cron1.equivalent(CronMapper.fromQuartzToCron4j(), cron4));
    }
}