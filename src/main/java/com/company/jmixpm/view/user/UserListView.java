package com.company.jmixpm.view.user;

import com.company.jmixpm.app.UsersService;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.app.inputdialog.DialogActions;
import io.jmix.flowui.app.inputdialog.DialogOutcome;
import io.jmix.flowui.app.inputdialog.InputParameter;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "pm_User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @ViewComponent
    private JmixImage<Object> avatarImg;

    private Project filterProject;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private GenericFilter genericFilter;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private DataGrid<User> usersDataGrid;
    @ViewComponent
    private CollectionContainer<User> usersDc;
    @Autowired
    private Notifications notifications;

    @Install(to = "usersDl", target = Target.DATA_LOADER)
    private List<User> usersDlLoadDelegate(final LoadContext<User> loadContext) {
        LoadContext.Query query = loadContext.getQuery();
        if (filterProject != null && query != null) {
            return usersService.getUsersNotInProject(filterProject, query.getFirstResult(), query.getMaxResults());
        }
        return dataManager.loadList(loadContext);
    }

    public void setFilterProject(Project project) {
        this.filterProject = project;
        genericFilter.setVisible(false);
    }

    @Subscribe("usersDataGrid")
    public void onUsersDataGridItemClick(final ItemClickEvent<User> event) {
        byte[] avatarBytes = event.getItem().getAvatar();
        if (avatarBytes != null && avatarBytes.length > 0) {
            StreamResource resource = new StreamResource("avatar",
                    () -> new ByteArrayInputStream(avatarBytes));
            avatarImg.setSrc(resource);
            avatarImg.setVisible(true);
        } else {
            avatarImg.setVisible(false);
        }
    }

    @Subscribe("usersDataGrid.sendEmail")
    public void onUsersDataGridSendEmail(final ActionPerformedEvent event) {
        dialogs.createInputDialog(this)
                .withHeader("Send email")
                .withLabelsPosition(Dialogs.InputDialogBuilder.LabelsPosition.TOP)
                .withParameters(
                        InputParameter.stringParameter("title")
                                .withLabel("Title")
                                .withRequired(true),
                        InputParameter.parameter("body")
                                .withLabel("Body")
                                .withField(() -> {
                                    JmixTextArea textArea = uiComponents.create(JmixTextArea.class);
                                    textArea.setRequired(true);
                                    textArea.setWidthFull();
                                    textArea.setHeight("9.5em");

                                    return textArea;
                                })
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String title = closeEvent.getValue("title");
                        String body = closeEvent.getValue("body");

                        Set<User> selected = usersDataGrid.getSelectedItems();
                        Collection<User> users = selected.isEmpty()
                                ? usersDc.getItems()
                                : selected;

                        doSendEmail(title, body, users);
                    }
                })
                .open();
    }

    private void doSendEmail(String title, String body, Collection<User> users) {

        notifications.create(title, body)
                .withType(Notifications.Type.SUCCESS)
                .withDuration(0)
                .withCloseable(true)
                .show();
    }
}