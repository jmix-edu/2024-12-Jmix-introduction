package com.company.jmixpm.view.project;

import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;
import com.company.jmixpm.view.user.UserListView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController(id = "pm_Project.detail")
@ViewDescriptor(path = "project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {
    @ViewComponent
    private TypedTextField<ProjectLabels> projectLabelsField;
//    @ViewComponent
//    private VerticalLayout tasksWrapper;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private DialogWindows dialogWindows;

    private DataGrid<Task> tasksDataGrid;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private CollectionContainer<Task> tasksDc;
    @ViewComponent
    private CollectionContainer<User> participantsDc;
    @ViewComponent
    private CollectionLoader<Task> tasksDl;
    @ViewComponent
    private CollectionLoader<User> participantsDl;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {
        projectLabelsField.setReadOnly(false);
        event.getEntity().setProjectLabels(new ProjectLabels(List.of("bug", "enhancement", "task")));
//        tasksWrapper.setEnabled(false);
    }

//    @Install(to = "participantsDataGrid.add", subject = "viewConfigurer")
//    private void participantsDataGridAddViewConfigurer(final UserListView view) {
//        view.setFilterProject(getEditedEntity());
//    }

    @Subscribe("tabSheet")
    public void onTabSheetSelectedChange(final JmixTabSheet.SelectedChangeEvent event) {
        if ("tasksTab".equals(event.getSelectedTab().getId().orElse(""))) {
            initTasks();
        }
        if ("participantsTab".equals(event.getSelectedTab().getId().orElse(""))) {
            initParticipants();
        }
    }

    private void initTasks() {
        tasksDl.setParameter("project", getEditedEntity());
        tasksDl.load();
        if (tasksDataGrid != null) {
            // It means that we've already opened this tab and initialized table and loader
            return;
        }
        tasksDataGrid =(DataGrid<Task>) getContent().findComponent("tasksDataGrid").get();
        BaseAction createAction = (BaseAction) tasksDataGrid.getAction("create");
        createAction.addActionPerformedListener(this::onTasksDataGridCreate);
        BaseAction editAction = (BaseAction) tasksDataGrid.getAction("edit");
        editAction.addActionPerformedListener(this::onTasksDataGridEdit);


    }

    private void initParticipants() {
        participantsDl.setParameter("project", getEditedEntity());
        participantsDl.load();
    }

    public void onTasksDataGridCreate(final ActionPerformedEvent event) {
        Task newTask = dataManager.create(Task.class);
        newTask.setProject(getEditedEntity());

        dialogWindows.detail(tasksDataGrid)
                .newEntity(newTask)
                .withParentDataContext(getViewData().getDataContext())
                .open();
    }

    public void onTasksDataGridEdit(final ActionPerformedEvent event) {
        Task selectedTask = tasksDataGrid.getSingleSelectedItem();
        if (selectedTask == null) {
            return;
        }

        dialogWindows.detail(tasksDataGrid)
                .editEntity(selectedTask)
                .withParentDataContext(getViewData().getDataContext())
                .open();
    }


    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        notifications.show("tasksDc items:" + tasksDc.getItems().size());
        notifications.show("participantsDc items:" + participantsDc.getItems().size());
    }

    @Subscribe(id = "tasksDc", target = Target.DATA_CONTAINER)
    public void onTasksDcCollectionChange(final CollectionContainer.CollectionChangeEvent<Task> event) {
        notifications.show("[tasksDc] CollectionChangeEvent", event.getChangeType() + "");
    }

    @Subscribe(id = "participantsDc", target = Target.DATA_CONTAINER)
    public void onParticipantsDcCollectionChange(final CollectionContainer.CollectionChangeEvent<User> event) {
        notifications.show("[participantsDc] CollectionChangeEvent", event.getChangeType() + "");
    }

//    @Install(to = "tasksDataGrid.create", subject = "initializer")
//    private void tasksDataGridCreateInitializer(final Task task) {
//        task.setProject(getEditedEntity());
//    }

//    @Subscribe
//    public void onAfterSave(final AfterSaveEvent event) {
//        tasksWrapper.setEnabled(true);
//    }


    
    
}