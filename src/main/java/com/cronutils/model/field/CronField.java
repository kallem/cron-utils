package com.cronutils.model.field;

import com.cronutils.model.field.constraint.FieldConstraints;
import com.cronutils.model.field.expression.FieldExpression;
import org.apache.commons.lang3.Validate;

import java.util.Comparator;

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
public class CronField {
    private CronFieldName field;
    private FieldExpression expression;
    private FieldConstraints constraints;

    public CronField(CronFieldName field, FieldExpression expression, FieldConstraints constraints) {
        this.field = field;
        this.expression = Validate.notNull(expression, "FieldExpression must not be null");
        this.constraints = Validate.notNull(constraints, "FieldConstraints must not be null");
    }

    public CronFieldName getField() {
        return field;
    }

    public FieldExpression getExpression() {
        return expression;
    }

    public FieldConstraints getConstraints() {
        return constraints;
    }

    public static Comparator<CronField> createFieldComparator() {
        return new Comparator<CronField>() {
            @Override
            public int compare(CronField o1, CronField o2) {
                return o1.getField().getOrder() - o2.getField().getOrder();
            }
        };
    }
}

