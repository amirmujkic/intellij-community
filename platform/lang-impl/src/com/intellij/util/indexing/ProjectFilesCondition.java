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
package com.intellij.util.indexing;

import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWithId;
import com.intellij.psi.search.GlobalSearchScope;

class ProjectFilesCondition implements Condition<VirtualFile> {
  private static final int MAX_FILES_TO_UPDATE_FROM_OTHER_PROJECT = 2;
  private final VirtualFile myRestrictedTo;
  private final GlobalSearchScope myFilter;
  private int myFilesFromOtherProjects;
  private final FileBasedIndexImpl.ProjectIndexableFilesFilter myIndexableFilesFilter;

  public ProjectFilesCondition(FileBasedIndexImpl.ProjectIndexableFilesFilter indexableFilesFilter,
                               GlobalSearchScope filter,
                               VirtualFile restrictedTo,
                               boolean includeFilesFromOtherProjects
                               ) {
    myRestrictedTo = restrictedTo;
    myFilter = filter;
    myIndexableFilesFilter = indexableFilesFilter;
    if (!includeFilesFromOtherProjects) {
      myFilesFromOtherProjects = MAX_FILES_TO_UPDATE_FROM_OTHER_PROJECT;
    }
  }

  @Override
  public boolean value(VirtualFile file) {
    if (myIndexableFilesFilter != null && !myIndexableFilesFilter.containsFileId(((VirtualFileWithId)file).getId())) {
      if (myFilesFromOtherProjects >= MAX_FILES_TO_UPDATE_FROM_OTHER_PROJECT || myRestrictedTo != null) return false;
      ++myFilesFromOtherProjects;
      return true;
    }

    return FileBasedIndexImpl.belongsToScope(file, myRestrictedTo, myFilter);
  }
}
