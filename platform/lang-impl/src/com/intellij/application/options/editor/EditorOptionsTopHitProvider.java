/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.application.options.editor;

import com.intellij.ide.ui.ConfigurableOptionsTopHitProvider;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;

/**
 * @author Sergey.Malenkov
 */
public class EditorOptionsTopHitProvider extends ConfigurableOptionsTopHitProvider {
  @Override
  public String getId() {
    return "editor";
  }

  @Override
  protected Configurable getConfigurable(Project project) {
    return new EditorOptions();
  }

  @Override
  protected String getName(Configurable configurable) {
    return null;
  }
}
