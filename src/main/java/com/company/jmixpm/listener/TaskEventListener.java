package com.company.jmixpm.listener;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("pm_TaskEventListener")
public class TaskEventListener {

    private final DataManager dataManager;

    public TaskEventListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @EventListener
    public void onTaskChangedBeforeCommit(final EntityChangedEvent<Task> event) {
        Project project;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Object> id = event.getChanges().getOldReferenceId("project");
            project = (Project) dataManager.load(id).optional().orElse(null);
        } else {
            Task task = dataManager.load(event.getEntityId()).one();
            project = task.getProject();
        }
        if (project == null) {
            return;
        }
        int totalEstimatedEfforts = project.getTasks().stream()
                .mapToInt(task -> task.getEstimatedEfforts() == null
                        ? 0 : task.getEstimatedEfforts())
                .sum();
        project.setTotalEstimatedEfforts(totalEstimatedEfforts);
        dataManager.save(project);
    }
}