package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.ProjectStats;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("pm_ProjectStatsService")
public class ProjectStatsService {
    private static final Logger log = LoggerFactory.getLogger(ProjectStatsService.class);
    private final DataManager dataManager;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchProjectStatistics() {
        List<Project> projects = dataManager.load(Project.class)
                .all()
                .fetchPlan("project-with-tasks")
                .list();


        List<ProjectStats> projectStats = projects.stream()
                .map(project -> {

                    ProjectStats stats = dataManager.create(ProjectStats.class);
                    stats.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    stats.setTasksCount(tasks.size());

                    log.info("Task [0] name: " + tasks.get(0).getName());

                    Integer estimatedEfforts = tasks.stream()
                            .map(task -> task.getEstimatedEfforts() == null ? 0 : task.getEstimatedEfforts())
                            .reduce(0, Integer::sum);

                    stats.setPlannedEfforts(estimatedEfforts);

                    stats.setActualEfforts(getActualEfforts(project.getId()));
                    return stats;
                }).toList();
        return projectStats;

    }

    private Integer getActualEfforts(UUID id) {

        return dataManager.loadValue("select sum(te.timeSpent) from pm_TimeEntry te " +
                        "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", id)
                .one();
    }
}