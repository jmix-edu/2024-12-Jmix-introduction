package com.company.jmixpm.view.projecttasks;


import com.company.jmixpm.app.TaskImportService;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.ProjectStats;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.TimeEntry;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.List;

@ViewController(id = "pm_ProjectTasksView")
@ViewDescriptor(path = "project-tasks-view.xml")
@DialogMode(width = "64em")
public class ProjectTasksView extends StandardView {

    @ViewComponent
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TaskImportService taskImportService;
    @Autowired
    private Notifications notifications;

    public void setProjectId(@Nullable Project project) {
        if (project != null) {
            tasksDl.setParameter("projectId", project.getId());
        } else {
            tasksDl.removeParameter("projectId");
        }

        tasksDl.load();
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
//
//        final List<TimeEntry> myEntityList = dataManager.load(TimeEntry.class)
//                .query("select t from pm_TimeEntry t " +
// "where t.task.assignee = :assignee")
//                .parameter("assignee", "")
//                .list();
    }

    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(final ClickEvent<JmixButton> event) {

        int imported = taskImportService.importTasks();

        notifications.create("I m in tray!", "So this is working")
                .withType(Notifications.Type.DEFAULT)
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
    
    
}