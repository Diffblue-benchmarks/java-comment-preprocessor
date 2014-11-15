/* 
 * Copyright 2014 Igor Maznitsa (http://www.igormaznitsa.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.jcp.expression.operators;

import com.igormaznitsa.jcp.expression.ExpressionItemPriority;
import com.igormaznitsa.jcp.expression.Value;

/**
 * The class implements the LESS operator handler
 *
 * @author Igor Maznitsa (igor.maznitsa@igormaznitsa.com)
 */
public final class OperatorLESS extends AbstractOperator {

  @Override
  public int getArity() {
    return 2;
  }

  @Override
  public String getReference() {
    return "indicates whether the value of the left operand is less than the value of the right operand";
  }

  @Override
  public String getKeyword() {
    return "<";
  }

  public Value executeIntInt(final Value arg1, final Value arg2) {
    return Value.valueOf(Boolean.valueOf(arg1.asLong().longValue() < arg2.asLong().longValue()));
  }

  public Value executeFloatInt(final Value arg1, final Value arg2) {
    return Value.valueOf(Boolean.valueOf(Float.compare(arg1.asFloat().floatValue(), arg2.asLong().floatValue()) < 0));
  }

  public Value executeIntFloat(final Value arg1, final Value arg2) {
    return Value.valueOf(Boolean.valueOf(Float.compare(arg1.asLong().floatValue(), arg2.asFloat().floatValue()) < 0));
  }

  public Value executeFloatFloat(final Value arg1, final Value arg2) {
    return Value.valueOf(Boolean.valueOf(Float.compare(arg1.asFloat().floatValue(), arg2.asFloat().floatValue()) < 0));
  }

  public Value executeStrStr(final Value arg1, final Value arg2) {
    return Value.valueOf(Boolean.valueOf(arg1.asString().compareTo(arg2.asString()) < 0));
  }

  public ExpressionItemPriority getExpressionItemPriority() {
    return ExpressionItemPriority.COMPARISON;
  }
}