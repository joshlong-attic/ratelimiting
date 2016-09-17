package org.springframework.cloud.netflix.zuul.filters.ratelimiter;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public interface RateLimiter {

    boolean isPermitted();
}
