package BigTask_MVC.model;

import BigTask_MVC.bean.User;
import BigTask_MVC.model.service.UserService;
import BigTask_MVC.model.service.UserServiceImpl;
import BigTask_MVC.view.UsersView;

import java.util.List;

public class MainModel implements Model{

    private ModelData modelData = new ModelData();
    private UserService userService = new UserServiceImpl();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        List <User> users = getAllUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(false);
    }

    @Override
    public void loadDeletedUsers() {
        List<User> users = userService.getAllDeletedUsers();
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(true);
    }

    @Override
    public void loadUserById(long userId) {
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    @Override
    public void deleteUserById(long userId) {
        userService.deleteUser(userId);
        List <User> users = getAllUsers();
        modelData.setUsers(users);
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        userService.createOrUpdateUser(name,id, level);
        List <User> users = getAllUsers();
        modelData.setUsers(users);
    }

    private List <User> getAllUsers(){
        List <User> users = userService.getUsersBetweenLevels(1, 100);
        users = userService.filterOnlyActiveUsers(users);
        return users;
    }
}
