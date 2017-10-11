package com.icezhg.h2.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ActiveSessionListener:
 *
 * @author zhongjibing 2017-10-11
 * @version 1.0
 */
@Component
public class ActiveSessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LOGGER.info("+++++++++++ session created");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.info("+++++++++++ session destoryed");
    }
}
