package com.company.jmixpm.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "CombinedObserverRole", code = CombinedObserverRole.CODE, scope = "UI")
public interface CombinedObserverRole extends TimeEntryReadRole, TaskReadRole, ProjectReadRole, UiMinimalRole {
    String CODE = "combined-observer-role";

}