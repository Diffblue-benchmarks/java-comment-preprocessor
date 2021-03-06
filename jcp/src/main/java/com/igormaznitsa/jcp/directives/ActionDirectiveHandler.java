/*
 * Copyright 2002-2019 Igor Maznitsa (http://www.igormaznitsa.com)
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.igormaznitsa.jcp.directives;

import com.igormaznitsa.jcp.context.PreprocessingState;
import com.igormaznitsa.jcp.context.PreprocessorContext;
import com.igormaznitsa.jcp.exceptions.FilePositionInfo;
import com.igormaznitsa.jcp.expression.Expression;
import com.igormaznitsa.jcp.expression.ExpressionItem;
import com.igormaznitsa.jcp.expression.ExpressionParser;
import com.igormaznitsa.jcp.expression.ExpressionTree;
import com.igormaznitsa.jcp.expression.Value;
import com.igormaznitsa.meta.annotation.MustNotContainNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.igormaznitsa.meta.common.utils.Assertions.assertNotNull;

/**
 * The class implements the //#action directive handler
 *
 * @author Igor Maznitsa (igor.maznitsa@igormaznitsa.com)
 */
public class ActionDirectiveHandler extends AbstractDirectiveHandler {

  @Override
  @Nonnull
  public String getName() {
    return "action";
  }

  @Override
  @Nonnull
  public String getReference() {
    return "call user extension with comma separated arguments";
  }

  @Override
  @Nonnull
  public DirectiveArgumentType getArgumentType() {
    return DirectiveArgumentType.MULTIEXPRESSION;
  }

  @Override
  @Nonnull
  public AfterDirectiveProcessingBehaviour execute(@Nonnull final String string, @Nonnull final PreprocessorContext context) {
    if (context.getPreprocessorExtension() != null) {

      try {
        final List<ExpressionTree> args = parseString(string, context);

        final Value[] results = new Value[args.size()];
        int index = 0;
        for (final ExpressionTree expr : args) {
          final Value val = Expression.evalTree(expr, context);
          results[index++] = val;
        }

        if (!assertNotNull(context.getPreprocessorExtension()).processAction(context, results)) {
          throw context.makeException("Extension can't process action ", null);
        }
      } catch (IOException ex) {
        throw context.makeException("Unexpected string detected [" + string + ']', ex);
      }
    }
    return AfterDirectiveProcessingBehaviour.PROCESSED;
  }

  @Nonnull
  @MustNotContainNull
  private List<ExpressionTree> parseString(@Nonnull final String str, @Nonnull final PreprocessorContext context) throws IOException {
    final ExpressionParser parser = ExpressionParser.getInstance();

    final PushbackReader reader = new PushbackReader(new StringReader(str));
    final List<ExpressionTree> result = new ArrayList<>();

    final PreprocessingState state = context.getPreprocessingState();
    final FilePositionInfo[] stack;
    final String sources;
    stack = state.makeIncludeStack();
    sources = state.getLastReadString();

    while (!Thread.currentThread().isInterrupted()) {
      final ExpressionTree tree;
      tree = new ExpressionTree(stack, sources);
      final ExpressionItem delimiter = parser.readExpression(reader, tree, context, false, true);

      if (delimiter != null && ExpressionParser.SpecialItem.COMMA != delimiter) {
        throw context.makeException("Wrong argument format detected", null);
      }

      if (tree.isEmpty()) {
        if (delimiter == null) {
          break;
        } else {
          throw context.makeException("Empty argument", null);
        }
      } else {
        result.add(tree);
        if (delimiter == null) {
          break;
        }
      }
    }

    return result;
  }
}
