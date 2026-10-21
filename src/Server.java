public class Server {
    private final int id;
    private final int cpuCapacity;
    private final int memCapacity;
    private int usedCpu;
    private int usedMem;
    private int vmCount;

    public Server(int id, int cpuCapacity, int memCapacity) {
        this.id = id;
        this.cpuCapacity = cpuCapacity;
        this.memCapacity = memCapacity;
    }

    public boolean fits(Vm vm) {
        int cpuEstimative = vm.cpu() + getUsedCpu();
        int memEstimative = vm.mem() + getUsedMem();
        return cpuEstimative <= cpuCapacity && memEstimative <= memCapacity;
    }

    public void place(Vm vm) {
        if (!fits(vm)) throw new IllegalArgumentException("VM: " + vm.id() + " não cabe no servidor " + getId());
        this.usedCpu += vm.cpu();
        this.usedMem += vm.mem();
        this.vmCount ++;
    }

    public double freeAfter(Vm vm) {
        double freeCpu = (getCpuCapacity() - getUsedCpu() - vm.cpu()) / (double) getCpuCapacity();
        double freeMem = (getMemCapacity() - getUsedMem() - vm.mem()) / (double) getMemCapacity();
        return freeCpu + freeMem;
    }

    public int getUsedMem() { return this.usedMem; }
    public int getUsedCpu() { return this.usedCpu; }
    public int getId() { return this.id; }
    public int getCpuCapacity() { return this.cpuCapacity; }
    public int getMemCapacity() { return this.memCapacity; }

}
