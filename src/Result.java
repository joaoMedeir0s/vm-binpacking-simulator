public record Result(String heuristic, int qantVms, int qantServers, 
                     int lowerBound, double cpuUtil, 
                     double memUtil, double mills) {

    int gap() { return qantServers - lowerBound; }
}
