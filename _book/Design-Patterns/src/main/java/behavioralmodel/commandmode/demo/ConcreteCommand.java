package behavioralmodel.commandmode.demo;

/**
 * 描述:
 * 具体命令角色
 * @author lars
 * @date 2019/7/11 10:32
 */
public class ConcreteCommand implements Command {

    private Receiver receiver;

    public ConcreteCommand() {
        receiver = new Receiver();
    }

    @Override
    public void execute() {
        System.out.println("具体命令类A,调用实现者的action方法");
        receiver.action();
    }
}
