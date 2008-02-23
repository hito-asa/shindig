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

import static org.apache.shindig.gadgets.SyndicatorConfig.DEFAULT_SYNDICATOR;
import static org.apache.shindig.gadgets.SyndicatorConfig.SYNDICATOR_KEY;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 */
public class SyndicatorConfigTest extends TestCase {
  private SyndicatorConfig config;

  private static final String TOP_LEVEL_NAME = "Top level name";
  private static final String TOP_LEVEL_VALUE = "Top level value";

  private static final String NESTED_KEY = "ne$ted";
  private static final String NESTED_NAME = "Nested name";
  private static final String NESTED_VALUE = "Nested value";
  private static final String NESTED_ALT_VALUE = "Nested value alt";

  private static final String CHILD_SYNDICATOR = "child";

  private static final String ARRAY_NAME = "array value";
  private static final String[] ARRAY_VALUE = new String[]{"Hello", "World"};
  private static final String ARRAY_ALT_VALUE = "Not an array";

  @Override
  public void setUp() throws Exception {
    config = new SyndicatorConfig(null);
    // We use a JSON Object here to guarantee that we're well formed up front.
    JSONObject json = new JSONObject();
    json.put(SYNDICATOR_KEY, new String[]{DEFAULT_SYNDICATOR});
    json.put(TOP_LEVEL_NAME, TOP_LEVEL_VALUE);
    json.put(ARRAY_NAME, ARRAY_VALUE);

    // small nested data.
    JSONObject nested = new JSONObject();
    nested.put(NESTED_NAME, NESTED_VALUE);

    json.put(NESTED_KEY, nested);

    config.loadFromString(json.toString());
  }

  public void testBasic() throws Exception {
    // check to make sure that the default config was processed correctly.
    assertEquals(1, config.getSyndicators().size());
    for (String synd : config.getSyndicators()) {
      assertEquals(DEFAULT_SYNDICATOR, synd);
    }

    String value = config.get(DEFAULT_SYNDICATOR, TOP_LEVEL_NAME);
    assertEquals(TOP_LEVEL_VALUE, value);

    JSONObject nested = config.getJsonObject(DEFAULT_SYNDICATOR, NESTED_KEY);

    String nestedValue = nested.getString(NESTED_NAME);

    assertEquals(NESTED_VALUE, nestedValue);
  }

  public void testCascade() throws Exception {
    JSONObject json = new JSONObject();
    json.put(SYNDICATOR_KEY, new String[]{CHILD_SYNDICATOR});
    json.put(ARRAY_NAME, ARRAY_ALT_VALUE);

    // small nested data.
    JSONObject nested = new JSONObject();
    nested.put(NESTED_NAME, NESTED_ALT_VALUE);

    json.put(NESTED_KEY, nested);

    config.loadFromString(json.toString());

    String value = config.get(CHILD_SYNDICATOR, TOP_LEVEL_NAME);
    assertEquals(TOP_LEVEL_VALUE, value);

    JSONObject nestedObj = config.getJsonObject(CHILD_SYNDICATOR, NESTED_KEY);
    String nestedValue = nestedObj.getString(NESTED_NAME);
    assertEquals(NESTED_ALT_VALUE, nestedValue);

    String arrayValue = config.get(CHILD_SYNDICATOR, ARRAY_NAME);
    assertEquals(ARRAY_ALT_VALUE, arrayValue);

    // Verify that the parent value wasn't overwritten as well.

    JSONArray defaultArrayTest = config.getJsonArray(DEFAULT_SYNDICATOR,
                                                      ARRAY_NAME);
    JSONArray defaultArray = new JSONArray(ARRAY_VALUE);
    assertEquals(defaultArrayTest.toString(), defaultArray.toString());
  }
}