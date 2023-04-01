package io.github.jhipster.sample.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "jhipster")
public interface JHipsterProperties {
    Info info();

    interface Info {
        Swagger swagger();

        interface Swagger {
            Boolean enable();
        }
    }

    Security security();

    interface Security {
        Authentication authentication();

        interface Authentication {
            Jwt jwt();

            interface Jwt {
                String issuer();
                long tokenValidityInSeconds();
                long tokenValidityInSecondsForRememberMe();
                PrivateKey privateKey();

                interface PrivateKey {
                    String location();
                }
            }
        }
    }

    Mail mail();

    interface Mail {
        String baseUrl();
    }
}
