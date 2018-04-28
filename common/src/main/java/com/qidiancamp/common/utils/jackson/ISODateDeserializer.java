package com.qidiancamp.common.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.qidiancamp.common.utils.DateUtils;
import java.io.IOException;
import java.util.Date;

/**
 * Deserializes an ISO formatted Date String to a Java Date ISO format:
 * 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z''
 *
 * @author jamespedwards42
 */
public class ISODateDeserializer extends JsonDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser jp, final DeserializationContext ctxt) throws IOException {

    return DateUtils.fromISODateString(jp.getValueAsString());
  }
}
