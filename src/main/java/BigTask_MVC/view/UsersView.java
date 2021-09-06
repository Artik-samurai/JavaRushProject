package BigTask_MVC.view;

import BigTask_MVC.bean.User;
import BigTask_MVC.controller.Controller;
import BigTask_MVC.model.ModelData;

public class UsersView implements View{
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventShowAllUsers(){
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers(){
        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id){
        controller.onOpenUserEditForm(id);
    }

    @Override
    public void refresh(ModelData modelData) {
        System.out.println("All " + (modelData.isDisplayDeletedUserList() ? "deleted ":"") + "users:");
        for (User user : modelData.getUsers()){
            System.out.println("\t" + user);
        }
        System.out.println("===================================================");
    }
}
