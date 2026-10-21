import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Vm> vms = new WorkloadGenerator(42).generate(1000);
        Result result = Simulation.run(vms, new FirstFit(), 64, 256);
        System.out.printf("%s: %d VMs em %d servidores (minimo teorico %d), CPU %.1f%%, memoria %.1f%%%n",
        result.heuristic(), result.qantVms(), result.qantServers(), result.lowerBound(),
        result.cpuUtil() * 100, result.memUtil() * 100);
    }
}
