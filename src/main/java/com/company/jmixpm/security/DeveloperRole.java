package com.company.jmixpm.security;

import com.company.jmixpm.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Developer", code = DeveloperRole.CODE, scope = "UI")
public interface DeveloperRole {
    String CODE = "developer";

    @MenuPolicy(menuIds = {"pm_Project.list", "pm_Task.list", "pm_TimeEntry.list", "pm_TimeEntry.my"})
    @ViewPolicy(viewIds = {"pm_Project.list", "pm_Task.list", "pm_TimeEntry.list", "pm_Project.detail", "pm_Task.detail", "pm_TimeEntry.detail", "pm_TimeEntry.my"})
    void screens();

    @EntityAttributePolicy(entityClass = Project.class, attributes = "tasks", action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = Project.class, attributes = {"id", "name", "manager", "description", "status", "defaultProject", "defaultTaskPriority", "participants", "projectLabels", "deletedBy", "deletedDate", "totalEstimatedEfforts"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Project.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void project();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.ALL)
    void task();

    @EntityAttributePolicy(entityClass = TimeEntry.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = TimeEntry.class, actions = EntityPolicyAction.ALL)
    void timeEntry();

    @EntityAttributePolicy(entityClass = Document.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void document();

    @EntityAttributePolicy(entityClass = User.class, attributes = {"firstName", "lastName", "username"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();
}