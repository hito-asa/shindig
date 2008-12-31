package org.apache.shindig.gadgets.stax;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shindig.gadgets.stax.model.LocaleMsg;
import org.json.JSONObject;

public class MessageBundle {

  public static final MessageBundle EMPTY = new MessageBundle(Collections.<String, String> emptyMap());

  private final Direction languageDirection;

  private final Map<String, String> messages;

  private final String jsonString;

  public MessageBundle(final Map<String, String> messages) {
    this(messages, Direction.LTR);
  }

  public MessageBundle(final Map<String, String> messages,
      final Direction languageDirection) {
    this.languageDirection = languageDirection;
    this.messages = Collections.unmodifiableMap(messages);
    this.jsonString = new JSONObject(this.messages).toString();
  }

  public MessageBundle(final MessageBundleSource messageBundleSource) {

    this(Collections.unmodifiableMap(convertToHash(null, messageBundleSource)), messageBundleSource.getLanguageDirection());
  }

  public MessageBundle(final Map<String, String> parentMessages, final Map<String, String> childMessages, final Direction languageDirection)
  {
    Map<String, String> messages = new HashMap<String, String>(parentMessages.size() + childMessages.size());
    messages.putAll(parentMessages);
    messages.putAll(childMessages);

    this.languageDirection = languageDirection;
    this.messages = Collections.unmodifiableMap(messages);
    this.jsonString = new JSONObject(this.messages).toString();
  }

  public MessageBundle(final MessageBundleSource parent, final MessageBundleSource child)
  {
    this(convertToHash(null, parent), convertToHash(null, child), child.getLanguageDirection());
  }

  public MessageBundle(final MessageBundle parent, final MessageBundle child) {
    this(parent.getMessages(), child.getMessages(), child.getLanguageDirection());
  }

  private static Map<String, String> convertToHash(final Map<String, String> messages, final MessageBundleSource messageBundleSource) {

    if (messageBundleSource == null || messageBundleSource.getLocaleMsgs().size() == 0) {
      return messages != null ? messages : new HashMap<String, String>();
    }

    final Map<String, String> localMessages = (messages != null) ? messages : new HashMap<String, String>(messageBundleSource.getLocaleMsgs().size());

    for (final LocaleMsg msg : messageBundleSource.getLocaleMsgs()) {
        localMessages.put(msg.getName(), msg.getText());
    }

    return localMessages;
  }

  public Direction getLanguageDirection() {
    return languageDirection;
  }

  /**
   * @return A read-only view of the message bundle.
   */
  public Map<String, String> getMessages() {
    return messages;
  }

  /**
   * Return the contents as a JSON encoded string
   */
  public String toJSONString() {
    return jsonString;
  }

  public static enum Direction {
    LTR, RTL;

    public static Direction parse(final String value) {
      if (value == null) {
        return LTR;
      }
      for (Direction direction : Direction.values()) {
        if (StringUtils.equalsIgnoreCase(direction.toString(), value)) {
          return direction;
        }
      }
      return null;
    }

    @Override
    public String toString() {
      return name().toLowerCase();
    }
  }


  public static interface MessageBundleSource {
    Set<LocaleMsg> getLocaleMsgs();

    Direction getLanguageDirection();
  }
}