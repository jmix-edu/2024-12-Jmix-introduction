package com.company.jmixpm.view.notification;

import com.company.jmixpm.entity.Notification;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "notifications", layout = MainView.class)
@ViewController(id = "pm_Notification.list")
@ViewDescriptor(path = "notification-list-view.xml")
@LookupComponent("notificationsDataGrid")
@DialogMode(width = "64em")
public class NotificationListView extends StandardListView<Notification> {
}