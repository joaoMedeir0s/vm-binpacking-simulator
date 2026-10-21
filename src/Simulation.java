import java.util.ArrayList;
import java.util.List;
public class Simulation {
    
    public static Result run (List<Vm> vms, Heuristic heuristic, int cpuCapacity, int memCapacity) {
        
        long start = System.nanoTime();
        List<Server> openServers = new ArrayList<>();

        for (Vm vm : heuristic.order(vms, cpuCapacity, memCapacity)) {
            if (vm.cpu() > cpuCapacity || vm.mem() > memCapacity)
                throw new IllegalArgumentException("VM: " + vm.id() + " É maior do que um servidor");

            Server server = heuristic.choose(vm, openServers);

            if (server == null) {
                server = new Server(openServers.size() , cpuCapacity, memCapacity);
                openServers.add(server);
            }

            server.place(vm);
        }

        double time = (System.nanoTime() - start) / 1e6;

        long usedCpu = openServers.stream().mapToLong(Server::getUsedCpu).sum();
        long usedMem = openServers.stream().mapToLong(Server::getUsedMem).sum();

        int qantServersInUse = openServers.size();

        double cpuUtil; double memUtil;
        if (qantServersInUse == 0) {
            cpuUtil = 0;
            memUtil = 0;
        }  else {
            cpuUtil = usedCpu / ((double) qantServersInUse * cpuCapacity);
            memUtil = usedMem / ((double) qantServersInUse * memCapacity);
        }

        return new Result(heuristic.name(), vms.size(), qantServersInUse,
                          lowerBound(vms, cpuCapacity, memCapacity), cpuUtil, memUtil, time);

    }

    public static int lowerBound(List<Vm> vms, int cpuCapacity, int memCapacity){
        long sumCpu = vms.stream().mapToLong(Vm::cpu).sum();
        long sumMem = vms.stream().mapToLong(Vm::mem).sum();

        long forCpu = (sumCpu + cpuCapacity - 1) / cpuCapacity;
        long forMem = (sumMem + memCapacity - 1) / memCapacity;
        return (int) Math.max(forCpu, forMem);
    }
}
