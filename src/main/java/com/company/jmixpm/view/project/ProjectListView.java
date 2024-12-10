package com.company.jmixpm.view.project;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.view.main.MainView;
import com.company.jmixpm.view.projecttasks.ProjectTasksView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import io.jmix.flowui.view.builder.WindowBuilder;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "projects", layout = MainView.class)
@ViewController(id = "pm_Project.list")
@ViewDescriptor(path = "project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
public class ProjectListView extends StandardListView<Project> {

    @Autowired
    private DialogWindows dialogWindows;
    @ViewComponent
    private DataGrid<Project> projectsDataGrid;

    @Subscribe("projectsDataGrid.showTasks")
    public void onProjectsDataGridShowTasks(final ActionPerformedEvent event) {
        DialogWindow<ProjectTasksView> dialogWindow = dialogWindows.view(this, ProjectTasksView.class).build();
        dialogWindow.getView().setProjectId(projectsDataGrid.getSingleSelectedItem());
        dialogWindow.setResizable(true);
        dialogWindow.open();
    }
}