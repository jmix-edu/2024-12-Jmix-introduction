package com.company.jmixpm.view.mytimeentrylist;


import com.company.jmixpm.app.TimeEntrySupport;
import com.company.jmixpm.entity.TimeEntry;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "my-time-entries", layout = MainView.class)
@ViewController(id = "pm_TimeEntry.my")
@ViewDescriptor(path = "my-time-entry-list-view.xml")
public class MyTimeEntryListView extends StandardView {
    @ViewComponent
    private DataGrid<TimeEntry> timeEntriesDataGrid;
    @Autowired
    private TimeEntrySupport timeEntrySupport;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Notifications notifications;

    @Subscribe("timeEntriesDataGrid.copy")
    public void onTimeEntriesDataGridCopy(final ActionPerformedEvent event) {
        TimeEntry selectedItem = timeEntriesDataGrid.getSingleSelectedItem();

        if (selectedItem == null) {
            return;
        }

        TimeEntry copiedItem = timeEntrySupport.copy(selectedItem);

        dialogWindows.detail(timeEntriesDataGrid)
                .newEntity(copiedItem)
                .open();
    }

//    private int seconds = 0;
//
//    @Subscribe("timer")
//    public void onTimerTimerAction(final Timer.TimerActionEvent event) {
//        seconds += event.getSource().getDelay() / 1000;
//        notifications.show("Timer tick", seconds + " seconds passed");
//    }
}