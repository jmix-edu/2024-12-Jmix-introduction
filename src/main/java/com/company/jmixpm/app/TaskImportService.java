package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component("pm_TaskImportService")
public class TaskImportService {
    private static final Logger log = LoggerFactory.getLogger(TaskImportService.class);

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;

    public TaskImportService(DataManager dataManager, CurrentAuthentication currentAuthentication) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
    }

    public int importTasks() {
        List<String> taskNames = obtainTaskNames();
        Project defaultProject = loadDefaultProject();
        EntitySet entitySet = new EntitySet();
        if (defaultProject != null) {
            List<Task> tasks = taskNames.stream()
                    .map(name -> {
                        Task task = dataManager.create(Task.class);
                        final User user = (User) currentAuthentication.getUser();
                        task.setAssignee(user);
                        task.setName(name);
                        task.setProject(defaultProject);

                        return task;
                    })
                    .toList();

            entitySet = dataManager.save(new SaveContext().saving(tasks));
        }

        log.info("Tasks imported: " + entitySet.size());
        return entitySet.size();

    }

    private List<String> obtainTaskNames() {
        return Stream.iterate(0, i -> i).limit(5)
                .map(i -> "Task " + RandomStringUtils.randomAlphabetic(5))
                .toList();
    }

    public Project loadDefaultProject() {
        final Optional<Project> entity = dataManager.load(Project.class)
                .query("select p from pm_Project p where p.defaultProject = :default")
                .parameter("default", true)
                .optional();

        return entity.orElse(null);
    }
}