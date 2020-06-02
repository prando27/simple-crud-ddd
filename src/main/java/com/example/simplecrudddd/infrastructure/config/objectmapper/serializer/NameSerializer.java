package com.example.simplecrudddd.infrastructure.config.objectmapper.serializer;

import java.io.IOException;

import com.example.simplecrudddd.domain.Name;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class NameSerializer extends StdSerializer<Name> {

    public NameSerializer() {
        this(null);
    }

    protected NameSerializer(Class<Name> t) {
        super(t);
    }

    @Override
    public void serialize(Name name,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(name.getValue());
    }
}
