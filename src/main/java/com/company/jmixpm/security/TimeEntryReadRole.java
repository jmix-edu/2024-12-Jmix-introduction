package com.company.jmixpm.security;

import com.company.jmixpm.entity.TimeEntry;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "TimeEntryRead", code = TimeEntryReadRole.CODE, scope = "UI")
public interface TimeEntryReadRole {
    String CODE = "time-entry-read";

    @MenuPolicy(menuIds = "pm_TimeEntry.list")
    @ViewPolicy(viewIds = "pm_TimeEntry.list")
    void screens();

    @EntityAttributePolicy(entityClass = TimeEntry.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = TimeEntry.class, actions = EntityPolicyAction.READ)
    void timeEntry();
}