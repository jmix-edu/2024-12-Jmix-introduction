package com.company.jmixpm.view.project;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.view.main.MainView;
import com.company.jmixpm.view.user.UserListView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;

import java.util.List;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController(id = "pm_Project.detail")
@ViewDescriptor(path = "project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {
    @ViewComponent
    private TypedTextField<ProjectLabels> projectLabelsField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        projectLabelsField.setReadOnly(false);

        event.getEntity().setProjectLabels(new ProjectLabels(List.of("bug", "enhancement", "task")));
    }

    @Install(to = "participantsDataGrid.add", subject = "viewConfigurer")
    private void participantsDataGridAddViewConfigurer(final UserListView view) {
        view.setFilterProject(getEditedEntity());
    }
    
    
}