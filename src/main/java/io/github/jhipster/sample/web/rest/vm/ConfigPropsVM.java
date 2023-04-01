package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.HashMap;
import java.util.Map;

@RegisterForReflection
public class ConfigPropsVM {

    public final Map<String, Context> contexts = new HashMap<>();

    public ConfigPropsVM() {
        this.contexts.put("JHipster", new Context());
    }

    @RegisterForReflection
    public class Context {

        public final Map<String, Object> beans = new HashMap<>();
    }
}
