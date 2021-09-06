package Patterns_ServiceLocator;

public class JMSServiceImpl implements Service {

    @Override
    public void execute() {
        System.out.println("Executing the JMSService");
    }

    @Override
    public String getName() {
        return "JMSService";
    }

}
