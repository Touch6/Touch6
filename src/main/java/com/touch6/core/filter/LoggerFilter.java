package com.touch6.core.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Touch6 on 2017/4/7.
 */
public class LoggerFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent evt) {
        String msg = evt.getMessage();
        if (StringUtils.isBlank(msg)) {
            return FilterReply.DENY;
        }
        if (msg.length() > 4096 && msg.indexOf("Caused by") == -1) {
            return FilterReply.DENY;
        }
        return FilterReply.ACCEPT;
    }
}
