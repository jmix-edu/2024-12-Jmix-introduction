package com.company.jmixpm.view.document;

import com.company.jmixpm.entity.Document;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "documents/:id", layout = MainView.class)
@ViewController(id = "pm_Document.detail")
@ViewDescriptor(path = "document-detail-view.xml")
@EditedEntityContainer("documentDc")
public class DocumentDetailView extends StandardDetailView<Document> {
}