package org.springframework.cloud.netflix.zuul;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@ConfigurationProperties(RateLimitProperties.ZUUL_RATELIMIT_PREFIX)
public class RateLimitProperties {

    public final static String ZUUL_RATELIMIT_PREFIX = "zuul.ratelimiter";
    public final static String ZUUL_RATELIMIT_SESSION_PREFIX = ZUUL_RATELIMIT_PREFIX + ".session" ;

    private double permitsPerSecond = 1 / 30.0;

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setPermitsPerSecond(double permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    public double getPermitsPerSecond() {
        return permitsPerSecond;
    }

    public static class Session {

        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

    }
}
