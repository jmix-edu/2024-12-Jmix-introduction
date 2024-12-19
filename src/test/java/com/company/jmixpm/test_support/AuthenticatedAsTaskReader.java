package com.company.jmixpm.test_support;

import com.company.jmixpm.entity.User;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.flowui.testassist.UiTestAuthenticator;
import org.springframework.context.ApplicationContext;

public class AuthenticatedAsTaskReader implements UiTestAuthenticator {

    @Override
    public void setupAuthentication(ApplicationContext context) {
        User dev3 = context.getBean(UnconstrainedDataManager.class).loadValue("select u from User u where u.username = :username", User.class)
                .parameter("username", "dev3").one();
        context.getBean(SystemAuthenticator.class).begin(dev3.getUsername());
    }

    @Override
    public void removeAuthentication(ApplicationContext context) {
        context.getBean(SystemAuthenticator.class).end();
    }
}
