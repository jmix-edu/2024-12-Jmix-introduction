package com.company.jmixpm.view.timeentry;

import com.company.jmixpm.entity.TimeEntry;

import com.company.jmixpm.entity.TimeEntryStatus;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Route(value = "timeEntries/:id", layout = MainView.class)
@ViewController(id = "pm_TimeEntry.detail")
@ViewDescriptor(path = "time-entry-detail-view.xml")
@EditedEntityContainer("timeEntryDc")
@DialogMode(width = "30em")
public class TimeEntryDetailView extends StandardDetailView<TimeEntry> {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @ViewComponent
    private JmixSelect<TimeEntryStatus> statusField;
    @ViewComponent
    private JmixTextArea rejectionReasonField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<TimeEntry> event) {
        event.getEntity().setStatus(TimeEntryStatus.NEW);
    }

    @Subscribe("userField.assignSelf")
    public void onUserFieldAssignSelf(final ActionPerformedEvent event) {
        final User user = (User) currentAuthentication.getUser();
        getEditedEntity().setUser(user);
    }

    @Subscribe(id = "timeEntryDc", target = Target.DATA_CONTAINER)
    public void onTimeEntryDcItemChange(final InstanceContainer.ItemChangeEvent<TimeEntry> event) {
        updateRejectionReasonField();
    }

    @Subscribe(id = "timeEntryDc", target = Target.DATA_CONTAINER)
    public void onTimeEntryDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<TimeEntry> event) {
        if ("status".equals(event.getProperty())) {
            updateRejectionReasonField();
        }
    }

    private void updateRejectionReasonField() {
        rejectionReasonField.setVisible(TimeEntryStatus.REJECTED == getEditedEntity().getStatus());
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        updateStatusFieldIcon();
    }

    @Subscribe("statusField")
    public void onStatusFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<TimeEntryStatus>, TimeEntryStatus> event) {
        updateStatusFieldIcon();
    }


    private void updateStatusFieldIcon() {
        TimeEntryStatus status = statusField.getValue();

        Icon icon = status == null ? null : switch (status) {
            case NEW -> {
                Icon newIcon = VaadinIcon.PLUS_CIRCLE_O.create();
                newIcon.setColor("var(--lumo-primary-color)");
                yield newIcon;
            }
            case APPROVED -> {
                Icon approvedIcon = VaadinIcon.CHECK_CIRCLE_O.create();
                approvedIcon.setColor("var(--lumo-success-color)");
                yield approvedIcon;
            }
            case REJECTED -> {
                Icon rejectedIcon = VaadinIcon.EXCLAMATION_CIRCLE_O.create();
                rejectedIcon.setColor("var(--lumo-error-color)");
                yield rejectedIcon;
            }
            case CLOSED -> VaadinIcon.CLOSE_CIRCLE_O.create();
        };

        statusField.setPrefixComponent(icon);
    }


}