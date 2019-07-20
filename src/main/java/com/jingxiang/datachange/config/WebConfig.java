package com.jingxiang.datachange.config;//package com.supconit.its.prod.monitor_dataflow.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }
//
//    /**
//     * 功能描述: 解决浏览器的跨域访问问题
//     *
//     * @param: []
//     * @return: org.springframework.web.servlet.config.annotation.WebMvcConfigure
//     * @auther: DX
//     * @date: 2018-09-18 上午 09:53
//     */
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedHeaders("*")
////                        .allowedMethods("*")
//                        .allowedOrigins("*");
//            }
//        };
//    }
//    /* *
//     * @author luopeng
//     * @date 2018/10/29 11:21
//     * @param []
//     * @return org.springframework.web.servlet.handler.SimpleMappingExceptionResolver
//     * @description: 没有权限的时候跳转403页面
//     */
////    @Bean
////    public SimpleMappingExceptionResolver resolver() {
////        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
////        Properties properties = new Properties();
////        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/403");
////        properties.setProperty("com.supconit.its.prod.monitor_dataflow.config.exception.RestfulException", "/400");
////        resolver.setExceptionMappings(properties);
////        return resolver;
////    }
//}
