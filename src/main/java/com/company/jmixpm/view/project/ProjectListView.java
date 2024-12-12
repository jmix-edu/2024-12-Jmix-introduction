package com.company.jmixpm.view.project;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;
import com.company.jmixpm.view.projecttasks.ProjectTasksView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.flowui.view.builder.WindowBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


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
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private CollectionContainer<Project> projectsDc;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    @Subscribe("projectsDataGrid.showTasks")
    public void onProjectsDataGridShowTasks(final ActionPerformedEvent event) {
        DialogWindow<ProjectTasksView> dialogWindow = dialogWindows.view(this, ProjectTasksView.class).build();
        dialogWindow.getView().setProjectId(projectsDataGrid.getSingleSelectedItem());
        dialogWindow.setResizable(true);
        dialogWindow.open();
    }

    @Subscribe(id = "createWithDM", subject = "clickListener")
    public void onCreateWithDMClick(final ClickEvent<JmixButton> event) {
        Project project = dataManager.create(Project.class);
        project.setName("Project " + RandomStringUtils.randomAlphabetic(5));

        User user = (User) currentAuthentication.getUser();
        project.setManager(user);
        // Bean validation is invoked while saving entity instance
        project.setProjectLabels(new ProjectLabels(List.of("task", "enhancement", "bug")));

        Project saved = unconstrainedDataManager.save(project);
        projectsDc.getMutableItems().add(saved);
    }
}