package com.jingxiang.datachange.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.jingxiang.datachange.listener.ShiroSessionListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfiguration {
    @Value("${shiro.anonUrl}")
    private String anonUrl;
    @Value("${shiro.loginUrl}")
    private String loginUrl;
    @Value("${shiro.successUrl}")
    private String successUrl;
    @Value("${shiro.logoutUrl}")
    private String logoutUrl;
    @Value("${shiro.unauthorizedUrl}")
    private String unauthorizedUrl;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.timeout}")
    private String timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
private String database;
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // 登录的 url
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 登录成功后跳转的 url
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        // 未授权 url
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        //        split()方法： 分割字符串过程中会自动忽略所有的空项；
//
//        splitByWholeSeparator方法：分割字符串过程中会忽略中间的空项，保留末尾的空项；
//
//        splitByWholeSeparatorPreserveAllToKens方法及splitPreserveAllToKens方法作用相同：分割字符串过程中会按照每个分隔符进行分割，不忽略任何空白项；
//
//        splitByCharacterType方法及splitByCharacterTypeCamelCase方法：按照字符类型进行分割。
        // 设置免认证 url
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(anonUrl, ",");
        for (String url : anonUrls) {
            filterChainDefinitionMap.put(url, "anon");
        }
        // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
        filterChainDefinitionMap.put(logoutUrl, "logout");
        // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put("/**", "user");//user指的是用户认证通过或者配置了Remember Me记住用户登录状态后可访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }


    @Bean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+Integer.parseInt(port));//host:port
        redisManager.setTimeout(Integer.parseInt(timeout));
        redisManager.setDatabase(Integer.parseInt(database));
        if (StringUtils.isNotBlank(password))
            redisManager.setPassword(password);
return redisManager;
    }

    /**
     * 开启缓存
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
//        cacheManager.setPrincipalIdFieldName("userId");
        return cacheManager;
    }

    /**
     * 身份认证的Realm,需要注入到SecurityManager
     */
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        //myRealm.setCacheManager(ehCacheManager());
        myRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        myRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        myRealm.setAuthenticationCacheName("defaultCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        myRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        myRealm.setAuthorizationCacheName("defaultCache");
        return myRealm;
    }

    /**
     * SecurityManager 安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myRealm());
        //自定义session管理
        securityManager.setSessionManager(sessionManager());
        //自定义缓存管理
        securityManager.setCacheManager(cacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * rememberMe cookie 效果是重开浏览器后无需重新登录
     *
     * @return SimpleCookie
     */
    private SimpleCookie rememberMeCookie() {
        // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置 cookie 的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }

    /**
     * cookie管理对象
     *
     * @return CookieRememberMeManager
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie 加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 用于开启 Thymeleaf 中的 shiro 标签的使用
     *
     * @return ShiroDialect shiro 方言对象
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


}
