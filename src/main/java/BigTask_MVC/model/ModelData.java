package BigTask_MVC.model;

import BigTask_MVC.bean.User;

import java.util.ArrayList;
import java.util.List;

public class ModelData {
    private List <User> users;
    private User activeUser;
    boolean displayDeletedUserList ;

    public void setDisplayDeletedUserList(boolean displayDeletedUserList) {
        this.displayDeletedUserList = displayDeletedUserList;
    }

    public boolean isDisplayDeletedUserList() {
        return displayDeletedUserList;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
