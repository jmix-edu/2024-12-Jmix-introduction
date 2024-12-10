package com.company.jmixpm.view.task;

import com.company.jmixpm.app.TaskImportService;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "tasks", layout = MainView.class)
@ViewController(id = "pm_Task.list")
@ViewDescriptor(path = "task-list-view.xml")
@LookupComponent("tasksDataGrid")
@DialogMode(width = "64em")
public class TaskListView extends StandardListView<Task> {

    @Autowired
    private TaskImportService taskImportService;
    @ViewComponent
    private CollectionLoader<Task> tasksDl;

    @Subscribe(id = "importBtn", subject = "clickListener")
    public void onImportBtnClick(final ClickEvent<JmixButton> event) {
        taskImportService.importTasks();
        tasksDl.load();
    }
}