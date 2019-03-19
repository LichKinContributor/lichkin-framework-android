package com.lichkin.framework.json.serializer;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lichkin.framework.utils.LKDateTimeUtils;

public class LKJsonSerializer4DateTime extends JsonSerializer<DateTime> {

	@Override
	public void serialize(DateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(LKDateTimeUtils.toString(value));
	}

}
