package com.cronutils.model.time.generator;

import com.cronutils.model.field.expression.Every;
import com.cronutils.model.field.expression.FieldExpression;
import com.google.common.collect.Lists;

import java.util.List;
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
class EveryFieldValueGenerator extends FieldValueGenerator {
    private Every every;

    public EveryFieldValueGenerator(final Every every) {
        super(every);

        this.every = every;
    }

    @Override
    public int generateNextValue(int reference) throws NoSuchValueException {
        final int start = every.getFrom().getValue();
        if (reference < start) {
            return start;
        } else {
            int period = every.getRepeat().getValue();
            int remainder = reference % period;
            return start + reference + (period - remainder);
        }
    }

    @Override
    public int generatePreviousValue(int reference) throws NoSuchValueException {
        int period = every.getRepeat().getValue();
        int remainder = reference % period;
        if(remainder == 0){
            return reference - period;
        }else{
            return reference - remainder;
        }
    }

    @Override
    public List<Integer> generateCandidates(int start, int end) {
        List<Integer>values = Lists.newArrayList();
        try {
            int reference = generateNextValue(start);
            while(reference<=end){
                values.add(reference);
                reference=generateNextValue(reference);
            }
        } catch (NoSuchValueException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public boolean isMatch(int value) {
        return (value % every.getRepeat().getValue())==0;
    }

    @Override
    protected boolean matchesFieldExpressionClass(FieldExpression fieldExpression) {
        return fieldExpression instanceof Every;
    }
}
