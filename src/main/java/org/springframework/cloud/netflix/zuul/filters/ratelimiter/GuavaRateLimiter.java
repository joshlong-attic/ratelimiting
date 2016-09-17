package org.springframework.cloud.netflix.zuul.filters.ratelimiter;


/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class GuavaRateLimiter implements RateLimiter {

    private final com.google.common.util.concurrent.RateLimiter rateLimiter;

    public GuavaRateLimiter(com.google.common.util.concurrent.RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean isPermitted() {
        return this.rateLimiter.tryAcquire();
    }
}
