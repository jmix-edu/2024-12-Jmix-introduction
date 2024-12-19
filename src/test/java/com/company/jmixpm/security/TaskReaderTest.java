package com.company.jmixpm.security;

import com.company.jmixpm.JmixpmApplication;
import com.company.jmixpm.test_support.AuthenticatedAsTaskReader;
import com.company.jmixpm.view.task.TaskListView;
import com.vaadin.flow.component.Component;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@UiTest(authenticator = AuthenticatedAsTaskReader.class)
@SpringBootTest(classes = {JmixpmApplication.class, FlowuiTestAssistConfiguration.class})
public class TaskReaderTest {

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void create_button_unavailable(){
        viewNavigators.view(UiComponentUtils.getCurrentView(), TaskListView.class).navigate();
        TaskListView currentView = UiTestUtils.getCurrentView();

        JmixButton createBtn = findComponent(currentView, "createButton");

        Assertions.assertFalse(createBtn.isEnabled());

    }

    @Test
    void edit_button_changed(){
        viewNavigators.view(UiComponentUtils.getCurrentView(), TaskListView.class).navigate();
        TaskListView currentView = UiTestUtils.getCurrentView();

        JmixButton readBtn = findComponent(currentView, "editButton");
        String expectedCaption = "Read";
        Assertions.assertEquals(expectedCaption, readBtn.getText());

    }

    @SuppressWarnings("unchecked")
    private <T> T findComponent(View<?> view, String componentId) {
        Optional<Component> component = UiComponentUtils.findComponent(view, componentId);
        Assertions.assertTrue(component.isPresent());
        return (T) component.get();
    }
}
