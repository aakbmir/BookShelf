package com.stackroute.zuulservice.config;

import com.stackroute.zuulservice.filter.PreFilter;
import com.stackroute.zuulservice.filter.PostFilter;
import com.stackroute.zuulservice.filter.RouterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class BeanConfiguration
{
  @Bean
  public PreFilter preFilter()
  {
    return new PreFilter();
  }

  @Bean
  public PostFilter postFilter()
  {
    return new PostFilter();
  }

  public RouterFilter routeFilter()
  {
    return new RouterFilter();
  }


  public CorsFilter corsFilter()
  {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("*", config);
    return new CorsFilter(source);

  }
}
