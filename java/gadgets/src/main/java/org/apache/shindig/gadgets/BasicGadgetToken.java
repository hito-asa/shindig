/*
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
package org.apache.shindig.gadgets;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

/**
 * Primitive token implementation that uses stings as tokens.
 */
class BasicGadgetToken implements GadgetToken {
  private final String token;

  /**
   * {@inheritDoc}
   */
  public String toSerialForm() {
    return token;
  }

  /**
   * Generates a token from an input string
   * @param token String form of token
   */
  public BasicGadgetToken(String token) {
    this.token = token;
  }

  /**
   * {@inheritDoc}
   * Signer that does not sign.
   */
  public URL signUrl(URL uri, String httpMethod,
      Map<String, Collection<String>> parameters) {
    return uri;
  }
}