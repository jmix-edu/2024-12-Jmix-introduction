package com.company.jmixpm.repository;

import com.company.jmixpm.entity.Project;
import io.jmix.core.repository.JmixDataRepository;

import java.util.UUID;


public interface ProjectRepository extends JmixDataRepository<Project, UUID> {
}