//package kr.co.choi.etc.config;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@Configuration
//public class JacksonTrimConfig {
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer trimStringDeserializer() {
//        return builder -> {
//            SimpleModule module = new SimpleModule();
//            module.addDeserializer(String.class, new StdDeserializer<String>(String.class) {
//                @Override
//                public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//                    String value = p.getValueAsString();
//                    return value == null ? null : value.trim();
//                }
//            });
//            builder.modules(module);
//        };
//    }
//}
