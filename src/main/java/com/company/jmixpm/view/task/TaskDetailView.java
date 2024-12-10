package com.company.jmixpm.view.task;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;

@Route(value = "tasks/:id", layout = MainView.class)
@ViewController(id = "pm_Task.detail")
@ViewDescriptor(path = "task-detail-view.xml")
@EditedEntityContainer("taskDc")
public class TaskDetailView extends StandardDetailView<Task> {
//    @Subscribe(id = "taskDc", target = Target.DATA_CONTAINER)
//    public void onTaskDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<Task> event) {
//        if ("project".equals(event.getProperty())) {
//            Project changedProject = (Project) event.getValue();
//            if (changedProject != null) {
//                event.getItem().setPriority(changedProject.getDefaultTaskPriority());
//            }
//        }
//    }

    @Subscribe("projectField")
    public void onProjectFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Project>, Project> event) {
        if (event.isFromClient()) {
            Project project = event.getValue();
            if (project != null) {
                getEditedEntity().setPriority(project.getDefaultTaskPriority());
            }
        }
    }
 
}