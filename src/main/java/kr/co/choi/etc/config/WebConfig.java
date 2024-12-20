package kr.co.mhnt.uae.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.mhnt.uae.tenant.TenantInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final TenantInterceptor tenantInterceptor;
	private final RequestIdInterceptor requestIdInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        "http://uae-web.mhnt.kr",
                        "http://uae-api.mhnt.kr",
                        "http://localhost:4764",
                        "http://127.0.0.1:4764"
                )
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TenantInterceptor 추가
		registry.addInterceptor(tenantInterceptor)
				// 적용 대상 URI
				.addPathPatterns("/**")
				// 제외 대상 URI
				.excludePathPatterns("/swagger-ui/**");

		// RequestIdInterceptor 추가
		registry.addInterceptor(requestIdInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/swagger-ui/**");


		WebMvcConfigurer.super.addInterceptors(registry);
	}
    ////////// 멀티 테넌시 설정
}
