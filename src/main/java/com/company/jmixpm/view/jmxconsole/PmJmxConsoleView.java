package com.company.jmixpm.view.jmxconsole;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DefaultMainViewParent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.jmxconsole.view.JmxConsoleView;

@Route(value = "jmxconsole", layout = DefaultMainViewParent.class)
@ViewController(id = "jmxcon_JmxConsoleView")
@ViewDescriptor(path = "pm-jmx-console-view.xml")
public class PmJmxConsoleView extends JmxConsoleView {
}