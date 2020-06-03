package com.example.simplecrudddd.infrastructure.config.objectmapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.simplecrudddd.domain.Cpf;
import com.example.simplecrudddd.domain.Email;
import com.example.simplecrudddd.domain.Name;
import com.example.simplecrudddd.domain.folder.entity.document.Document;
import com.example.simplecrudddd.infrastructure.config.objectmapper.mixin.DocumentMixIn;
import com.example.simplecrudddd.infrastructure.config.objectmapper.serializer.CpfSerializer;
import com.example.simplecrudddd.infrastructure.config.objectmapper.serializer.EmailSerializer;
import com.example.simplecrudddd.infrastructure.config.objectmapper.serializer.NameSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.addMixIn(Document.class, DocumentMixIn.class);

        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(buildValueObjectModel());

        return mapper;
    }

    private SimpleModule buildValueObjectModel() {
        SimpleModule valueObjectsModule = new SimpleModule();
        valueObjectsModule.addSerializer(Name.class, new NameSerializer());
        valueObjectsModule.addSerializer(Cpf.class, new CpfSerializer());
        valueObjectsModule.addSerializer(Email.class, new EmailSerializer());

        return valueObjectsModule;
    }
}
