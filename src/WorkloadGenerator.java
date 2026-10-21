import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorkloadGenerator {
    private static final int[][] FLAVORS = {
        {1, 4}, {2, 8}, {4, 16}, {8, 32}, {16, 64},
        {2, 32}, {4, 48}, // lots of memory
        {12, 12}, {24, 24} // lots of cpu
    };
    private static final int[] WEIGHTS = {15, 15, 15, 10, 5, 12, 8, 10, 5};

    private final Random random;

    public WorkloadGenerator(long seed) {
        this.random = new Random(seed);
    }


    public List<Vm> generate(int n) {
        int total = Arrays.stream(WEIGHTS).sum();    
        List<Vm> vms = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int r = random.nextInt(total);
            int idx = 0;
            while (r >= WEIGHTS[idx]) {
                r -= WEIGHTS[idx];
                idx++;
            }
            vms.add(new Vm(i, FLAVORS[idx][0], FLAVORS[idx][1]));
        }

        return vms;
    }

}
