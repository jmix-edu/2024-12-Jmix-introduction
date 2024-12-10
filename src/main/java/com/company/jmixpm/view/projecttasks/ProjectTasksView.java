package com.company.jmixpm.view.projecttasks;


import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.lang.Nullable;

@ViewController(id = "pm_ProjectTasksView")
@ViewDescriptor(path = "project-tasks-view.xml")
@DialogMode(width = "64em")
public class ProjectTasksView extends StandardView {

    @ViewComponent
    private CollectionLoader<Task> tasksDl;

    public void setProjectId(@Nullable Project project) {
        if (project != null) {
            tasksDl.setParameter("projectId", project.getId());
        } else {
            tasksDl.removeParameter("projectId");
        }

        tasksDl.load();
    }
}