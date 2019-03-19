package com.lichkin.framework.json.deserializer;

import java.io.IOException;
import java.sql.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.lichkin.framework.utils.LKDateTimeUtils;

public class LKJsonDeserializer4SqlDate extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final JsonNode node = p.getCodec().readTree(p);
		return LKDateTimeUtils.toSqlDate(node.asText());
	}

}
