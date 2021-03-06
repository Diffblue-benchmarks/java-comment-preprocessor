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

import javax.annotation.Nonnull;

/**
 * The class implements the //#postfix[+|-] directive handler
 *
 * @author Igor Maznitsa (igor.maznitsa@igormaznitsa.com)
 */
public class PostfixDirectiveHandler extends AbstractDirectiveHandler {

  @Override
  @Nonnull
  public String getName() {
    return "postfix";
  }

  @Override
  @Nonnull
  public String getReference() {
    return "turn on(+) or turn off(-) output into postfix section";
  }

  @Override
  @Nonnull
  public DirectiveArgumentType getArgumentType() {
    return DirectiveArgumentType.ONOFF;
  }

  @Override
  @Nonnull
  public AfterDirectiveProcessingBehaviour execute(@Nonnull final String string, @Nonnull final PreprocessorContext context) {
    final PreprocessingState state = context.getPreprocessingState();
    if (!string.isEmpty()) {
      switch (string.charAt(0)) {
        case '+': {
          state.setPrinter(PreprocessingState.PrinterType.POSTFIX);
        }
        break;
        case '-': {
          state.setPrinter(PreprocessingState.PrinterType.NORMAL);
        }
        break;
        default: {
          throw context.makeException("Unsupported ending [" + string + ']', null);
        }
      }
      return AfterDirectiveProcessingBehaviour.PROCESSED;
    }
    throw context.makeException(getFullName() + " needs ending [+|-]", null);
  }
}
