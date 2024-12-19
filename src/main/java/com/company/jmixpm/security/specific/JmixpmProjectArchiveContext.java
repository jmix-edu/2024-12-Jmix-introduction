package com.company.jmixpm.security.specific;

import io.jmix.core.accesscontext.SpecificOperationAccessContext;

public class JmixpmProjectArchiveContext extends SpecificOperationAccessContext {

    public static final String NAME = "jmixpm.project.archive";

    public JmixpmProjectArchiveContext() {
        super(NAME);
    }
}
