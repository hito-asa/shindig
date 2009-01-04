/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.apache.shindig.gadgets;

import org.apache.commons.lang.StringUtils;

/**
 * The supported auth modes for outbound requests.
 */
public enum AuthType {
  NONE, SIGNED, OAUTH;

  /**
   * @return The parsed value (defaults to NONE)
   */
  public static AuthType parse(final String value) {

    if (value != null) {
      for (AuthType authType : AuthType.values()) {
        if (StringUtils.equalsIgnoreCase(authType.toString(), StringUtils
            .trimToEmpty(value))) {
          return authType;
        }
      }
    }
    return NONE; // Explicit test for "garbage" -> NONE
  }

  /**
   * Use lowercase as toString form
   * 
   * @return string value of Auth type
   */
  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
