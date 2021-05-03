package cn.repeatlink.module.law.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class LawSwaggerConfig {
	@Bean
	public Docket law_api() {
		ParameterBuilder ticketPar = new ParameterBuilder();  
        List<Parameter> pars = new ArrayList<Parameter>();    
        ticketPar.name("Authorization").description("登录校验")//name表示名称，description表示描述  
        .modelRef(new ModelRef("string")).parameterType("header")   
        .required(false).defaultValue("Bearer ");
        pars.add(ticketPar.build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("组织管理（律师事务所）").select()
				// 为当前包路径
				.apis(RequestHandlerSelectors.basePackage("cn.repeatlink.module.law")).paths(PathSelectors.any()).build()
				.globalOperationParameters(pars);
//	        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
	}

	// 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
	private ApiInfo apiInfo() {
	    Contact contact = new Contact("tangliqiang", "http://www.repeatlink.cn", "123456@qq.com");
        return  new ApiInfo(
                "组织管理（律师事务所）",
                "组织管理（律师事务所）",
                "1.0",
                "user",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
	}
}
