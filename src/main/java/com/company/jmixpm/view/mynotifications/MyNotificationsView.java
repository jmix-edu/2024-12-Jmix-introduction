package com.company.jmixpm.view.mynotifications;


import com.company.jmixpm.entity.Notification;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "my-notifications-view", layout = MainView.class)
@ViewController(id = "pm_MyNotificationsView")
@ViewDescriptor(path = "my-notifications-view.xml")
public class MyNotificationsView extends StandardView {

    @ViewComponent
    private DataGrid<Notification> notificationsDataGrid;
//    @Autowired
//    private DataManager dataManager;
    @Autowired
    private UnconstrainedDataManager dataManager;
    @ViewComponent
    private CollectionLoader<Notification> notificationsDl;

    @Subscribe("notificationsDataGrid.markAsRead")
    public void onNotificationsDataGridMarkAsRead(final ActionPerformedEvent event) {
        Notification selected = notificationsDataGrid.getSingleSelectedItem();
        if (selected == null) {
            return;
        }

        selected.setIsRead(true);
        dataManager.save(selected);
        notificationsDl.load();
    }
}