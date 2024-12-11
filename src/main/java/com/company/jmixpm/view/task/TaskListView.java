package com.company.jmixpm.view.task;

import com.company.jmixpm.app.TaskImportService;
import com.company.jmixpm.entity.Task;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;


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
    @Autowired
    private TemporaryStorage temporaryStorage;
    @ViewComponent
    private DataContext dataContext;
    @ViewComponent
    private CollectionContainer<Task> tasksDc;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataManager dataManager;

    @Subscribe(id = "importBtn", subject = "clickListener")
    public void onImportBtnClick(final ClickEvent<JmixButton> event) {
        taskImportService.importTasks();
        tasksDl.load();
    }

    @Subscribe("uploadTasksFromFileBtn")
    public void onUploadTasksFromFileBtnFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) throws IOException {
        Receiver receiver = event.getReceiver();

        if (receiver instanceof FileTemporaryStorageBuffer storageBuffer) {
            UUID fileId = storageBuffer.getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileId);
            if (file != null) {
                processFife(file);
                temporaryStorage.deleteFile(fileId);
            }
        }
    }

    private void processFife(File file) throws IOException {
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        for (String line : lines) {
            Task task = dataContext.create(Task.class);
            task.setName(line);
            task.setProject(taskImportService.loadDefaultProject());
            final User user = (User) currentAuthentication.getUser();
            task.setAssignee(user);
            tasksDc.getMutableItems().add(task);
            dataManager.save();
        }

    }
}