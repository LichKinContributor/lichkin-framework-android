package com.lichkin.framework.json.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lichkin.framework.utils.LKDateTimeUtils;

public class LKJsonSerializer4Date extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(LKDateTimeUtils.toString(value));
	}

}
