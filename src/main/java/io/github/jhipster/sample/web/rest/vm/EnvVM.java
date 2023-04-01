package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RegisterForReflection
public class EnvVM {

    public final Collection<String> activeProfiles;
    public final Collection<PropertySource> propertySources;

    public EnvVM(Collection<String> activeProfiles, Collection<PropertySource> propertySources) {
        this.activeProfiles = activeProfiles;
        this.propertySources = propertySources;
    }

    @RegisterForReflection
    public static class PropertySource {

        public final String name;
        public final Map<String, PropertyValue> properties;

        public PropertySource(String name, Map<String, String> properties) {
            this.name = name;
            this.properties = properties.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, PropertyValue::new));
        }

        @RegisterForReflection
        public static class PropertyValue {

            public final String value;

            public PropertyValue(Map.Entry<String, String> entry) {
                this.value = retrieveValue(entry);
            }

            public PropertyValue(String value) {
                this.value = value;
            }

            private String retrieveValue(Map.Entry<String, String> entry) {
                final String obfuscatedValue = "******";
                String[] prefixes = { "password", "credentials", "credential", "secret", "secrets" };

                for (String prefix : prefixes) {
                    String key = entry.getKey();
                    if (key != null && key.toLowerCase().contains(prefix)) {
                        return obfuscatedValue;
                    }
                }

                return entry.getValue();
            }
        }
    }
}
