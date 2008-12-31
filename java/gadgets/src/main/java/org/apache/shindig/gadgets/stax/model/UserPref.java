package org.apache.shindig.gadgets.stax.model;

/*
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
 *
 */

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.shindig.common.uri.Uri;
import org.apache.shindig.gadgets.GadgetException;
import org.apache.shindig.gadgets.spec.SpecParserException;
import org.apache.shindig.gadgets.variables.Substitutions;

public class UserPref extends SpecElement {
  public static final String ELEMENT_NAME = "UserPref";

  public static final String ATTR_NAME = "name";
  public static final String ATTR_DISPLAY_NAME = "display_name";
  public static final String ATTR_DEFAULT_VALUE = "default_value";
  public static final String ATTR_REQUIRED = "required";
  public static final String ATTR_DATATYPE = "datatype";
  public static final String ATTR_URLPARAM = "urlparam";
  public static final String ATTR_AUTOCOMPLETE_URL = "autocomplete_url";
  public static final String ATTR_NUM_MINVAL = "num_minval";
  public static final String ATTR_NUM_MAXVAL = "num_maxval";
  public static final String ATTR_STR_MAXLEN = "str_maxlen";
  public static final String ATTR_RESTRICT_TO_COMPLETIONS = "restrict_to_completions";
  public static final String ATTR_PREFIX_MATCH = "prefix_match";
  public static final String ATTR_PUBLISH = "publish";
  public static final String ATTR_LISTEN = "listen";
  public static final String ATTR_ON_CHANGE = "on_change";
  public static final String ATTR_GROUP = "group";

  private final List<EnumValue> enumValues = new LinkedList<EnumValue>();

  public UserPref(final QName name, final Map<String, QName> attrNames,
      final Uri base) {
    super(name, attrNames, base);
  }

  protected UserPref(final UserPref userPref, final Substitutions substituter) {
    super(userPref, substituter);
    setAttr(ATTR_DISPLAY_NAME, substituter.substituteString(userPref
        .getDisplayName()));
    setAttr(ATTR_DEFAULT_VALUE, substituter.substituteString(userPref
        .getDefaultValue()));

    for (EnumValue enumValue : userPref.getEnumValues()) {
      enumValues.add(enumValue.substitute(substituter));
    }
  }

  @Override
  public UserPref substitute(final Substitutions substituter) {
    return new UserPref(this, substituter);
  }

  public String getName() {
    return attr(ATTR_NAME);
  }

  public String getDefaultValue() {
    return attr(ATTR_DEFAULT_VALUE);
  }

  public String getDisplayName() {
    return attr(ATTR_DISPLAY_NAME);
  }

  public boolean isRequired() {
    return attrBool(ATTR_REQUIRED);
  }

  public DataType getDataType() {
    return DataType.parse(attr(ATTR_DATATYPE));
  }

  public String getUrlparam() {
    return attr(ATTR_URLPARAM);
  }

  public String getAutocompleteUrl() {
    return attr(ATTR_AUTOCOMPLETE_URL);
  }

  public double getNumMinval() {
    return attrDouble(ATTR_NUM_MINVAL);
  }

  public double getNumMaxval() {
    return attrDouble(ATTR_NUM_MAXVAL);
  }

  public int getStrMaxlen() {
    return attrInt(ATTR_STR_MAXLEN);
  }

  public String getRestrictToCompletions() {
    return attr(ATTR_RESTRICT_TO_COMPLETIONS);
  }

  public boolean getPrefixMatch() {
    return attrBool(ATTR_PREFIX_MATCH);
  }

  public boolean getPublish() {
    return attrBool(ATTR_PUBLISH);
  }

  public boolean getListen() {
    return attrBool(ATTR_LISTEN);
  }

  public String getOnChange() {
    return attr(ATTR_ON_CHANGE);
  }

  public String getGroup() {
    return attr(ATTR_GROUP);
  }

  public List<EnumValue> getEnumValues() {
    return Collections.unmodifiableList(enumValues);
  }

  private void addEnumValue(final EnumValue enumValue) {
    this.enumValues.add(enumValue);
  }

  @Override
  protected void writeAttributes(final XMLStreamWriter writer)
      throws XMLStreamException {
    final String namespaceURI = name().getNamespaceURI();

    if (attr(ATTR_NAME) != null) {
      writer.writeAttribute(namespaceURI, ATTR_NAME, getName());
    }

    if (attr(ATTR_DISPLAY_NAME) != null) {
      writer.writeAttribute(namespaceURI, ATTR_DISPLAY_NAME, getDisplayName());
    }

    if (attr(ATTR_DEFAULT_VALUE) != null) {
      writer
          .writeAttribute(namespaceURI, ATTR_DEFAULT_VALUE, getDefaultValue());
    }

    if (attr(ATTR_REQUIRED) != null) {
      writer.writeAttribute(namespaceURI, ATTR_REQUIRED, String
          .valueOf(isRequired()));
    }

    if (attr(ATTR_DATATYPE) != null) {
      writer.writeAttribute(namespaceURI, ATTR_DATATYPE, getDataType()
          .toString());
    }

    if (attr(ATTR_URLPARAM) != null) {
      writer.writeAttribute(namespaceURI, ATTR_URLPARAM, getUrlparam());
    }

    if (attr(ATTR_AUTOCOMPLETE_URL) != null) {
      writer.writeAttribute(namespaceURI, ATTR_AUTOCOMPLETE_URL,
          getAutocompleteUrl());
    }

    if (attr(ATTR_NUM_MINVAL) != null) {
      writer.writeAttribute(namespaceURI, ATTR_NUM_MINVAL, String
          .valueOf(getNumMinval()));
    }

    if (attr(ATTR_NUM_MAXVAL) != null) {
      writer.writeAttribute(namespaceURI, ATTR_NUM_MAXVAL, String
          .valueOf(getNumMaxval()));
    }

    if (attr(ATTR_STR_MAXLEN) != null) {
      writer.writeAttribute(namespaceURI, ATTR_STR_MAXLEN, String
          .valueOf(getStrMaxlen()));
    }

    if (attr(ATTR_RESTRICT_TO_COMPLETIONS) != null) {
      writer.writeAttribute(namespaceURI, ATTR_RESTRICT_TO_COMPLETIONS,
          getRestrictToCompletions());
    }

    if (attr(ATTR_PREFIX_MATCH) != null) {
      writer.writeAttribute(namespaceURI, ATTR_PREFIX_MATCH, String
          .valueOf(getPrefixMatch()));
    }

    if (attr(ATTR_PUBLISH) != null) {
      writer.writeAttribute(namespaceURI, ATTR_PUBLISH, String
          .valueOf(getPublish()));
    }

    if (attr(ATTR_LISTEN) != null) {
      writer.writeAttribute(namespaceURI, ATTR_LISTEN, String
          .valueOf(getListen()));
    }

    if (attr(ATTR_ON_CHANGE) != null) {
      writer.writeAttribute(namespaceURI, ATTR_ON_CHANGE, getOnChange());
    }

    if (attr(ATTR_GROUP) != null) {
      writer.writeAttribute(namespaceURI, ATTR_GROUP, getGroup());
    }
  }

  @Override
  protected void writeChildren(final XMLStreamWriter writer)
      throws XMLStreamException {
    for (EnumValue enumValue : enumValues) {
      enumValue.toXml(writer);
    }
  }

  @Override
  public void validate() throws SpecParserException {
    if (attr(ATTR_NAME) == null) {
      throw new SpecParserException(name().getLocalPart()
          + "@name must be set!");
    }
  }

  /**
   * Possible values for UserPref@datatype
   */
  public static enum DataType {
    STRING, HIDDEN, BOOL, ENUM, LIST, NUMBER;

    /**
     * Parses a data type from the input string.
     *
     * @param value
     * @return The data type of the given value.
     */
    public static DataType parse(String value) {
      for (DataType type : DataType.values()) {
        if (StringUtils.equalsIgnoreCase(type.toString(), value)) {
          return type;
        }
      }
      return STRING;
    }
  }

  public static class Parser extends SpecElement.Parser<UserPref> {

    public Parser(final Uri base) {
      this(new QName(ELEMENT_NAME), base);
    }

    public Parser(final QName name, final Uri base) {
      super(name, base);
      register(new EnumValue.Parser(base));
      register(ATTR_NAME, ATTR_DISPLAY_NAME, ATTR_DEFAULT_VALUE, ATTR_REQUIRED,
          ATTR_DATATYPE, ATTR_URLPARAM, ATTR_AUTOCOMPLETE_URL, ATTR_NUM_MINVAL,
          ATTR_NUM_MAXVAL, ATTR_STR_MAXLEN, ATTR_RESTRICT_TO_COMPLETIONS,
          ATTR_PREFIX_MATCH, ATTR_PUBLISH, ATTR_LISTEN, ATTR_ON_CHANGE,
          ATTR_GROUP);
    }

    @Override
    protected UserPref newElement() {
      return new UserPref(name(), getAttrNames(), getBase());
    }

    @Override
    protected void addChild(XMLStreamReader reader, final UserPref userPref,
        final SpecElement child) throws GadgetException {
      if (child instanceof EnumValue) {
        userPref.addEnumValue((EnumValue) child);
      } else {
        super.addChild(reader, userPref, child);
      }
    }
  }
}