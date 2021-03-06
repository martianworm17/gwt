/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.core.ext.linker;

import com.google.gwt.core.ext.Linker;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Artifacts created by {@link AbstractLinker}.
 */
public class SyntheticArtifact extends EmittedArtifact {

  private final long lastModified;
  private final byte[] data;

  public SyntheticArtifact(Class<? extends Linker> linkerType,
      String partialPath, byte[] data) {
    this(linkerType, partialPath, data, System.currentTimeMillis());
  }

  public SyntheticArtifact(Class<? extends Linker> linkerType,
      String partialPath, byte[] data, long lastModified) {
    super(linkerType, partialPath);
    assert data != null;
    this.lastModified = lastModified;
    this.data = data;
  }

  @Override
  public InputStream getContents(TreeLogger logger) {
    return new ByteArrayInputStream(data);
  }

  @Override
  public long getLastModified() {
    return lastModified;
  }

  @Override
  public void writeTo(TreeLogger logger, OutputStream out)
      throws UnableToCompleteException {
    try {
      out.write(data);
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, "Unable to copy artifact: " + getPartialPath(), e);
      throw new UnableToCompleteException();
    }
  }
}
