//package com.server.gymServerApplication.config;
//
//
//import org.ehcache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//
//    @Primary
//    @Bean
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(CacheManager.newInstance("ehcache.xml"));
//    }
//}
