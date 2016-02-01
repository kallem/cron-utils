package com.cronutils.model.time.generator;

import com.cronutils.model.field.constraint.FieldConstraints;
import com.cronutils.model.field.constraint.FieldConstraintsBuilder;
import com.cronutils.model.field.expression.Between;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.value.IntegerFieldValue;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
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
public class BetweenFieldValueGeneratorTest {
    private BetweenFieldValueGenerator fieldValueGenerator;

    private int from = 0;
    private int to = 2;
    private int outOfRange = 7;

    @Before
    public void setUp(){
        FieldConstraints constraints = FieldConstraintsBuilder.instance().createConstraintsInstance();
        fieldValueGenerator = new BetweenFieldValueGenerator(new Between(constraints, new IntegerFieldValue(from), new IntegerFieldValue(to)));
    }

    @Test(expected = NoSuchValueException.class)
    public void testGenerateNextValue() throws Exception {
        for(int j=from-1; j<(to+1); j++){
            assertEquals(j+1, fieldValueGenerator.generateNextValue(j));
        }
        fieldValueGenerator.generateNextValue(to);
    }

    @Test(expected = NoSuchValueException.class)
    public void testGeneratePreviousValue() throws Exception {
        for(int j=to+1; j>(from-1); j--){
            assertEquals(j-1, fieldValueGenerator.generatePreviousValue(j));
        }
        fieldValueGenerator.generatePreviousValue(from);
    }

    @Test
    public void testGenerateCandidatesNotIncludingIntervalExtremesFullInterval() throws Exception {
        List<Integer> candidates = fieldValueGenerator.generateCandidates(from, to);
        assertEquals(1, candidates.size());
        assertEquals(1, candidates.get(0), 0);
    }

    @Test
    public void testGenerateCandidatesNotIncludingIntervalRangesNotIntersectInterval() throws Exception {
        assertTrue(fieldValueGenerator.generateCandidates(to + 1, to + 10).isEmpty());
    }

    @Test
    public void testIsMatch() throws Exception {
        assertTrue(fieldValueGenerator.isMatch(from));
        assertTrue(fieldValueGenerator.isMatch(to));
        assertFalse(fieldValueGenerator.isMatch(outOfRange));
    }

    @Test
    public void testMatchesFieldExpressionClass() throws Exception {
        assertTrue(fieldValueGenerator.matchesFieldExpressionClass(mock(Between.class)));
        assertFalse(fieldValueGenerator.matchesFieldExpressionClass(mock(FieldExpression.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNotMatchesBetween() throws Exception {
        new BetweenFieldValueGenerator(mock(FieldExpression.class));
    }
}