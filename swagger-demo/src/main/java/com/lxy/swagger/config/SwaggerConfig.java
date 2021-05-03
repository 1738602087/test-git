package com.lxy.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
/*
* 我们使用这个Docket对象去接收我们的这个APIInfo的默认配置，这个Docket对象
* 有很多配置，我们配置的这个APIInfo也就是我们swagger的帮助文档信息。*/


//swaggerconfig是一个配置文件，它要想被这个加载到springboot项目里面
//就需要添加这个@configuration注解
@Configuration
//开启swagger2
@EnableSwagger2
public class SwaggerConfig {
    /*注意这里如果我们配置了多个分组，那么我们再进行这个项目的访问时候，我们如果
    * 对于配置文件中的属性如果我们激活的是dev或者是test，就设置这个flag的
    * 标志位为true(那么我们项目启动的时候，我们在浏览器中输入
    * http://localhost:8081/swagger-ui.html就会查看到我们项目的接口
    * 不仅仅是包含这个A分组还有这个B分组以及C分组，还有我们的这个默认的
    * default分组，相反如果我们设置的这个flag的标志位为false
    * （那么我们项目启动的时候，我们在浏览器中输入
    * http://localhost:8082/swagger-ui.html这里我们仍然会查看到
    * 我们项目的接口，只不过是我们查看不到我们默认分组的接口，而是只能够查看到这
    * 个A,B以及这个C分组的接口。这里的原理也就是我们下面所注入的Docket对象和我们
    * 上面的这三个并没有什么关联，我们所配置的在开发环境显示我们的swagger接口，
    * 而在上线环境不显示我们的swagger接口仅仅针对我们的默认接口。）*/

    /*这里注意如果说我们是多人协作开发那么我们的下面的这个Bean可能是来自于很多类，最终
    *都会被spring所保管，A程序员写的Bean是扫描自己的，B程序员写的Bean是扫描B的，
    * 这个也就是我们系统的协同合作开发。*/
    @Bean
    public Docket docket1(){
         return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }
    //配置swagger的Docket的bean实例。
    @Bean
    public Docket docket(Environment environment) {
        //这里我们需要首先获得这个项目的环境也就是看一下这个项目的环境是开发环境还是
        //上线环境还是默认的环境，我们一般都是通过在这个配置文件中进行激活。激活的时候
        // 需要一个值environment。

        //设置要显示的swagger环境（对于配置文件中的属性如果我们激活的是dev或者是test，
        // 就设置这个flag的标志位为true(那么我们项目启动的时候，我们在浏览器中输入
        // http://localhost:8081/swagger-ui.html就会查看到我们项目的接口)，否则的话就
        // 设置我们的这个flag的标志位为false（那么我们项目启动的时候，我们在浏览器中输入
        // http://localhost:8082/swagger-ui.html就不会查看到我们项目的接口）。）
        Profiles proFiles= Profiles.of("dev","test");
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(proFiles);
        System.out.println(flag);

        return new Docket(DocumentationType.SWAGGER_2)
                //详细定制,这里的这个方法需要接收一个ApiInfo，我们就在这个
                //apiInfo()方法中new一个ApiInfo，RequestHandlerSelectors
                //配置要扫描的接口的方式，RequestHandlerSelectors.basePackage
                //基于某一个扫描包去进行扫描(常用)，any()扫描全部；none()都不扫描
                .apiInfo(apiInfo())
                .groupName("李向阳")
                .enable(flag) //enable是否启动swagger，如果为false则Swagger不能够在浏览器中访问
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lxy.swagger"))
                //.paths()过滤什么路径
                .paths(PathSelectors.any())
                .build();
    }
    //配置swagger信息apiInfo
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("lxy", "http://www.apache.org/licenses/LICENSE-2.0", "1738602087@qq.com");
        return new ApiInfo(
                "李向阳的Swagger Api说明文档",
                "勤思好学善总结",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
