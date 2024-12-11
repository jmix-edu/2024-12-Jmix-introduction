package com.company.jmixpm.view.user;

import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.view.*;

import java.io.ByteArrayInputStream;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "pm_User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @ViewComponent
    private JmixImage<Object> avatarImg;

    @Subscribe("usersDataGrid")
    public void onUsersDataGridItemClick(final ItemClickEvent<User> event) {
        byte[] avatarBytes = event.getItem().getAvatar();
        if (avatarBytes != null && avatarBytes.length > 0) {
            StreamResource resource = new StreamResource("avatar",
                    () -> new ByteArrayInputStream(avatarBytes));
            avatarImg.setSrc(resource);
            avatarImg.setVisible(true);
        } else {
            avatarImg.setVisible(false);
        }
    }
}