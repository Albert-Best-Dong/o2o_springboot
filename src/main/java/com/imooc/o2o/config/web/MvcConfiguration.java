package com.imooc.o2o.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.imooc.o2o.interceptor.shopadmin.ShopLoginInterceptor;
import com.imooc.o2o.interceptor.shopadmin.ShopPerssionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletException;

/**
 * 开启Mvc，自动注入spring容器。WebMvcConfigurerAdapter：配置视图解析器
 * 当一个类实现了这个接口（ApplicationContextAware）
 */

@Configuration
//等价于<mvc:annotation-driven/>
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    //Spring容器
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源配置
     *
     *
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/albert/image/upload/");
    }

    /**
     * 定义默认的请求处理器
     * @param configurer
     */
    /**
     * 报错：正如Spring Boot2.4发行说明中所述，默认情况下，嵌入式Servlet容器提供的DefaultServlet不再注册。如果您的应用程序需要它，就像您的应用程序所做的那样，您可以通过将server.servlet.register-default-servlet设置为true来启用它。
     *
     * 或者，您可以使用WebServerFactoryCustomizerbean以编程方式配置它：
     *
     * @Bean
     * WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> enableDefaultServlet() {
     *     return (factory) -> factory.setRegisterDefaultServlet(true);
     * }
     * 请注意，必须通过自定义程序应用配置，以便默认的properties-based配置不会覆盖它。
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> enableDefaultServlet() {
          return (factory) -> factory.setRegisterDefaultServlet(true);
      }

    /**
     * 创建viewResolver
     *
     * @return
     */
    @Bean(name = "viewResolver")
    public ViewResolver createViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        //设置Spring容器
        viewResolver.setApplicationContext(this.applicationContext);
        //取消缓存
        viewResolver.setCache(false);
        //设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        //设置视图解析的后缀
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Value("${kaptcha.border}")
    private String border;

    @Value("${kaptcha.textproducer.font.color}")
    private String fcolor;

    @Value("${kaptcha.image.width}")
    private String width;

    @Value("{kaptcha.textproducer.char.string}")
    private String cString;

    @Value("${kaptcha.image.height}")
    private String height;

    @Value("${kaptcha.textproducer.font.size}")
    private String fsize;

    @Value("${kaptcha.noise.color}")
    private String nColor;

    @Value("${kaptcha.textproducer.char.length}")
    private String clength;

    @Value("${kaptcha.textproducer.font.names}")
    private String fname;
    /**
     * 文件上传解析器
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        //1024*1024*20=20M
        multipartResolver.setMaxUploadSize(20971520);
        multipartResolver.setMaxInMemorySize(20971520);
        return multipartResolver;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws ServletException {
        ServletRegistrationBean<KaptchaServlet> servletRegistrationBean = new ServletRegistrationBean<KaptchaServlet>(new KaptchaServlet(), "/Kaptcha");

        servletRegistrationBean.addInitParameter("kaptcha.border", border);
        servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.color", fcolor);
        servletRegistrationBean.addInitParameter("kaptcha.image.width", width);
        servletRegistrationBean.addInitParameter("kaptcha.textproducer.char.string", cString);
        servletRegistrationBean.addInitParameter("kaptcha.image.height", height);
        servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.size", fsize);
        servletRegistrationBean.addInitParameter("kaptcha.noise.color", nColor);
        servletRegistrationBean.addInitParameter("kaptcha.textproducer.char.length", clength);
        servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.names", fname);
        return servletRegistrationBean;
    }

    /**
     * 添加拦截器配置
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        //注册拦截器
        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
        //配置拦截的路径
        loginIR.addPathPatterns(interceptPath);
        //还可以注册其它的拦截器
        InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPerssionInterceptor());
        //配置拦截的路径
        permissionIR.addPathPatterns(interceptPath);
        //配置不拦截的路径
        /**shoplist page **/
        permissionIR.excludePathPatterns("/shopadmin/shoplist");
        permissionIR.excludePathPatterns("/shopadmin/getshoplist");
        /**shopregister page **/
        permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
        permissionIR.excludePathPatterns("/shopadmin/registershop");
        permissionIR.excludePathPatterns("/shopadmin/shopoperation");
        /**shopmanage page**/
        permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
        permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
    }
}
