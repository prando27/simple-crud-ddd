package com.example.simplecrudddd.infrastructure.config.objectmapper.serializer;

import java.io.IOException;

import com.example.simplecrudddd.domain.Email;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class EmailSerializer extends StdSerializer<Email> {

    public EmailSerializer() {
        this(null);
    }

    protected EmailSerializer(Class<Email> t) {
        super(t);
    }

    @Override
    public void serialize(Email email,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(email.getValue());
    }
}
