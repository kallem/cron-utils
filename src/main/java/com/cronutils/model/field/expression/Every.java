package com.cronutils.model.field.expression;

/*
 * Copyright 2014 jmrozanec
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

import com.cronutils.model.field.constraint.FieldConstraints;
import com.cronutils.model.field.value.IntegerFieldValue;

/**
 * Represents every x time on a cron field.
 */
public class Every extends FieldExpression {
    private IntegerFieldValue from;
    private IntegerFieldValue repeat;

    public Every(FieldConstraints constraints, IntegerFieldValue repeat) {
        super(constraints);

        this.from = new IntegerFieldValue(0);
        this.repeat = repeat != null ? repeat : new IntegerFieldValue(1);
    }

    public Every(final FieldConstraints constraints, final IntegerFieldValue from, final IntegerFieldValue repeat) {
        super(constraints);

        this.from = from != null ? from : new IntegerFieldValue(0);
        this.repeat = repeat != null ? repeat : new IntegerFieldValue(1);
    }

    private Every(Every every){
        this(every.constraints, every.repeat);
    }

    public IntegerFieldValue getFrom() {
        return from;
    }

    public IntegerFieldValue getRepeat() {
        return repeat;
    }

    @Override
    public String asString() {
        if(repeat.getValue()==1){
            return "";
        }
        return String.format("/%s", getRepeat());
    }
}
