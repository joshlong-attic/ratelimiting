package org.springframework.cloud.netflix.zuul;


import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.pre.RateLimitFilter;
import org.springframework.cloud.netflix.zuul.filters.ratelimiter.GuavaRateLimiter;
import org.springframework.cloud.netflix.zuul.filters.ratelimiter.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import static org.springframework.cloud.netflix.zuul.RateLimitProperties.ZUUL_RATELIMIT_SESSION_PREFIX;

/**
 * installs a rate-limiting filter. This filter delegates to
 * {@link RateLimiter} implementation. Users may activate per-user rate-limiting
 * with {@code rate}
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
@ConditionalOnWebApplication
@ConditionalOnClass(ZuulFilter.class)
public class RateLimitConfiguration {

    private final RateLimitProperties rateLimitProperties;

    @Autowired
    public RateLimitConfiguration(RateLimitProperties rateLimitProperties) {
        this.rateLimitProperties = rateLimitProperties;
    }

    @Bean
    public RateLimitProperties rateLimiterProperties() {
        return new RateLimitProperties();
    }

    @Bean
    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(value = ZUUL_RATELIMIT_SESSION_PREFIX + ".enabled", havingValue = "false", matchIfMissing = true)
    public RateLimiter rateLimiter() {
        return buildGuavaRateLimiter();
    }

    @Bean
    @SessionScope
    @ConditionalOnMissingBean(RateLimiter.class)
    @ConditionalOnProperty(value = ZUUL_RATELIMIT_SESSION_PREFIX + ".enabled", havingValue = "true")
    public RateLimiter sessionRateLimiter() {
        return buildGuavaRateLimiter();
    }

    // pre
    @Bean
    public RateLimitFilter rateLimitFilter(RateLimiter rateLimiter) {
        return new RateLimitFilter(rateLimiter);
    }

    private RateLimiter buildGuavaRateLimiter() {
        return new GuavaRateLimiter(com.google.common.util.concurrent.RateLimiter.create(
                this.rateLimitProperties.getPermitsPerSecond()));
    }
}

