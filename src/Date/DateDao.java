package Date;

import java.io.IOException;

public interface DateDao {
    public void update();   // store data
    public Date get();      // load data
    public boolean verifyFileIntegrity(int n);
    public void setInitialDate() throws IOException;
}
