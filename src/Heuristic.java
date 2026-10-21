import java.util.List;

public interface Heuristic {
    String name();

    Server choose(Vm vm, List<Server> openServers);
    
    default List<Vm> order(List<Vm> vms, int cpuCapacity, int memCapacity) {
        return vms;
    }
}
