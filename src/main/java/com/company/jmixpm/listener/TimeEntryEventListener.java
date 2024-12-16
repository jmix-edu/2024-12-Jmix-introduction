package com.company.jmixpm.listener;

import com.company.jmixpm.entity.TimeEntry;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.event.TimeEntryStatusChangedEvent;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import io.jmix.core.event.AttributeChanges;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.flowui.UiEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;

@Component("pm_TimeEntryEventListener")
public class TimeEntryEventListener {

    private final DataManager dataManager;
    private final UiEventPublisher uiEventPublisher;

    public TimeEntryEventListener(DataManager dataManager, UiEventPublisher uiEventPublisher) {
        this.dataManager = dataManager;
        this.uiEventPublisher = uiEventPublisher;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onTimeEntryChangedAfterCommit(final EntityChangedEvent<TimeEntry> event) {
        AttributeChanges changes = event.getChanges();

        if (EntityChangedEvent.Type.DELETED != event.getType() && changes.isChanged("status")) {
            TimeEntry timeEntry = dataManager.load(event.getEntityId())
                    .fetchPlan(fetchPlanBuilder -> {
                        fetchPlanBuilder.add("user", FetchPlan.INSTANCE_NAME);
                    }).one();
            publishEvent(timeEntry.getUser().getUsername());

        }

        if (EntityChangedEvent.Type.DELETED == event.getType()) {
            Id<User> userId = changes.getOldReferenceId("user");
            if (userId != null) {
                User loaded = dataManager.load(userId).one();
                publishEvent(loaded.getUsername());
            }
        }
    }

    private void publishEvent(String username) {
        uiEventPublisher.publishEventForUsers(
                new TimeEntryStatusChangedEvent(this),
                Collections.singleton(username));
    }
}