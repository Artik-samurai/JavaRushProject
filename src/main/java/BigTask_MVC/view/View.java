package BigTask_MVC.view;

import BigTask_MVC.controller.Controller;
import BigTask_MVC.model.ModelData;

public interface View {
    void refresh(ModelData modelData);
    void setController(Controller controller);
}
