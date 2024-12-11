package com.company.jmixpm.view.document;

import com.company.jmixpm.entity.Document;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "documents", layout = MainView.class)
@ViewController(id = "pm_Document.list")
@ViewDescriptor(path = "document-list-view.xml")
@LookupComponent("documentsDataGrid")
@DialogMode(width = "64em")
public class DocumentListView extends StandardListView<Document> {

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader;

    @Supply(to = "documentsDataGrid.file", subject = "renderer")
    private Renderer<Document> documentsDataGridFileRenderer() {
        return new ComponentRenderer<>(document -> {
            FileRef docFile = document.getFile();

            if (docFile != null) {
                JmixButton btn = uiComponents.create(JmixButton.class);
                btn.setText(docFile.getFileName());
                btn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                btn.addClickListener(click -> downloader.download(docFile));

                return btn;
            }

            return null;
        });
    }
}