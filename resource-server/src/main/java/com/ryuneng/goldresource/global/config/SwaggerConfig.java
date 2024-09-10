package com.ryuneng.goldresource.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {

        return new Info()
                .title("GoldMarket API Documentation: Resource Server")
                .description("금방주식회사 백엔드 입사과제 API 문서: 자원 서버 [원티드 프리온보딩 백엔드 인턴십]")
                .version("1.0");
    }
}
