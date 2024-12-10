package com.company.jmixpm.security;

import com.company.jmixpm.entity.Project;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "ProjectRead", code = ProjectReadRole.CODE, scope = "UI")
public interface ProjectReadRole {
    String CODE = "project-read";

    @EntityAttributePolicy(entityClass = Project.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Project.class, actions = EntityPolicyAction.READ)
    void project();

    @MenuPolicy(menuIds = "pm_Project.list")
    @ViewPolicy(viewIds = "pm_Project.list")
    void screens();
}