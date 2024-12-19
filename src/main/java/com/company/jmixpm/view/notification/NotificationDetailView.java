package com.company.jmixpm.view.notification;

import com.company.jmixpm.entity.Notification;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "notifications/:id", layout = MainView.class)
@ViewController(id = "pm_Notification.detail")
@ViewDescriptor(path = "notification-detail-view.xml")
@EditedEntityContainer("notificationDc")
public class NotificationDetailView extends StandardDetailView<Notification> {
}