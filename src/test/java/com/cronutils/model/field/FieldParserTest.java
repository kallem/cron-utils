package com.cronutils.model.field;

import com.cronutils.model.field.constraint.FieldConstraintsBuilder;
import com.cronutils.model.field.expression.*;
import com.cronutils.model.field.value.IntegerFieldValue;
import com.cronutils.parser.field.FieldParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
public class FieldParserTest {
    private FieldParser parser;

    @Before
    public void setUp() {
        parser = new FieldParser(FieldConstraintsBuilder.instance().createConstraintsInstance());
    }

    @Test
    public void testParseAlways() throws Exception {
        assertEquals(1, (int)((Always) parser.parse("*")).getEvery().getRepeat().getValue());
    }

    @Test
    public void testParseAlwaysEveryX() throws Exception {
        int every = 5;
        assertEquals(every, (int)((Every) parser.parse("*/" + every)).getRepeat().getValue());
    }

    @Test
    public void testParseOn() throws Exception {
        int on = 5;
        assertEquals(on, (int)((On) parser.parse("" + on)).getTime().getValue());
    }

    @Test
    public void testParseAnd() throws Exception {
        int on1 = 3;
        int on2 = 4;
        And and = (And) parser.parse(String.format("%s,%s", on1, on2));
        assertEquals(2, and.getExpressions().size());
        assertEquals(on1, (int)((On) and.getExpressions().get(0)).getTime().getValue());
        assertEquals(on2, (int)((On) and.getExpressions().get(1)).getTime().getValue());
    }

    @Test
    public void testParseBetween() throws Exception {
        int from = 3;
        int to = 4;
        Between between = (Between) parser.parse(String.format("%s-%s", from, to));
        assertEquals(from, (int)((IntegerFieldValue)between.getFrom()).getValue());
        assertEquals(to, (int)((IntegerFieldValue)between.getTo()).getValue());
    }

    @Test
    public void testParseBetweenEveryX() throws Exception {
        int from = 10;
        int to = 40;
        int every = 5;
        Between between = (Between) parser.parse(String.format("%s-%s/%s", from, to, every));
        assertEquals(from, (int)((IntegerFieldValue)between.getFrom()).getValue());
        assertEquals(to, (int)((IntegerFieldValue)between.getTo()).getValue());
        assertEquals(every, (int)(between.getEvery().getRepeat()).getValue());
    }

    @Test(expected = NullPointerException.class)
    public void testCostructorNullConstraints() throws Exception {
        new FieldParser(null);
    }
}