import java.util.List;

public class FirstFit implements Heuristic{
    @Override 
    public String name() {
        return "FirstFit";
    }

    @Override 
    public Server choose(Vm vm, List<Server> openServers) {
        for (Server server: openServers) {
            if (server.fits(vm)) {
                return server;
            }
        }
        return null;
    }
}
