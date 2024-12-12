package com.company.jmixpm.view.user;

import com.company.jmixpm.app.UsersService;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import com.company.jmixpm.view.main.MainView;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.List;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "pm_User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @ViewComponent
    private JmixImage<Object> avatarImg;

    private Project filterProject;
    @Autowired
    private UsersService usersService;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private GenericFilter genericFilter;

    @Install(to = "usersDl", target = Target.DATA_LOADER)
    private List<User> usersDlLoadDelegate(final LoadContext<User> loadContext) {
        LoadContext.Query query = loadContext.getQuery();
        if (filterProject != null && query != null) {
            return usersService.getUsersNotInProject(filterProject, query.getFirstResult(), query.getMaxResults());
        }
        return dataManager.loadList(loadContext);
    }

    public void setFilterProject(Project project) {
        this.filterProject = project;
        genericFilter.setVisible(false);
    }

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