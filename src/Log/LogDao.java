package Log;

import java.util.List;

public interface LogDao {
    void addLog(String physicalId);
    void deleteLog(String physicalId);
    void findLog(String physicalId);
    void loanLog(String physicalId, String borrowerId, String returnDate);
    void lateLog(String physicalId, String borrowerId, String returnDate);
    void returnLog(String physicalId);
    void verifyFileIntegrity(int mode);
    public List<Log> getLogs(String physicalId);

}
