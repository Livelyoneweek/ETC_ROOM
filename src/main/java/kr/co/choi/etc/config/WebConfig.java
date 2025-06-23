package kr.co.choi.etc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.choi.etc.config.trim.TrimStringDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestIdInterceptor requestIdInterceptor;

    /**
     * HTTP 요청·응답에만 쓰는 별도 MessageConverter
     * TrimStringDeserializer 는 역직렬화만 커스텀 구현함
     */
    @Bean
    public MappingJackson2HttpMessageConverter trimmingMessageConverter() {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .modules(TrimStringDeserializer.asModule()) // trim 모듈만 추가
                .build();
        return new MappingJackson2HttpMessageConverter(mapper);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://localhost:23512",
                        "http://127.0.0.1:23512"
                )
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestIdInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
