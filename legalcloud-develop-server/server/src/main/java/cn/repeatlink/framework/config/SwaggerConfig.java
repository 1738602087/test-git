package cn.repeatlink.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ConditionalOnProperty(name = "spring.swagger.enable", havingValue = "true", matchIfMissing = false)
@EnableSwagger2
@Configuration
@EnableKnife4j
public class SwaggerConfig {

}
