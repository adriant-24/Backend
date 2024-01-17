package com.shop.onlineshop.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BeanContextStartupListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // TODO Auto-generated method stub
        Map serviceImplementations = event.getApplicationContext().getBeansOfType( ObjectMapper.class );
        if ( serviceImplementations.isEmpty() ) {
            ObjectMapper mapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            mapper.setDateFormat(df);
            mapper.setSerializationInclusion(Include.NON_NULL);
            beanFactory.autowireBean(mapper);
        }

    }



}
