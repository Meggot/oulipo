// Copyright (c) 2019 Travelex Ltd

package com.common.models.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ReadWriteUtils {


    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <I> I asObjectFromString(Class<I> classToConvertTo, String jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(jsonObject, classToConvertTo);
    }

    private static final String QUOTE = "\"";

    private static final Pattern NON_WORD = Pattern.compile(".*\\W.*");

    public String toCsv(final List<Object> list) {
        final StringBuilder sb = new StringBuilder();
        String sep = "";
        for (final Object object : list) {
            String s = object.toString();
            final Matcher m = NON_WORD.matcher(s);
            if (m.matches()) {
                if (s.contains(QUOTE)) {
                    s = s.replaceAll(QUOTE, "\"\"");
                }
                sb.append(sep).append(QUOTE).append(s).append(QUOTE);
            } else {
                sb.append(sep).append(s);
            }
            sep = ",";
        }
        return sb.toString();
    }

}
