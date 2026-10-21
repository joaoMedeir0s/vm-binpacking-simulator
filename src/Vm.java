
public record Vm(int id, int cpu, int mem) {
    public Vm {
        if (cpu <= 0) {
            throw new IllegalArgumentException("Número inválido de CPU");
        } else if (mem <= 0) {
            throw new IllegalArgumentException("Tamanho de memória inválido");
        }
    }

}
