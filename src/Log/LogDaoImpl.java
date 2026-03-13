package Log;

import Date.DateDao;
import Date.DateDaoImpl;
import Date.LocalDate;
import LoanList.LoanDurationDao;
import LoanList.LoanDurationDaoImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class LogDaoImpl implements LogDao {
    private static final String LOG_FILE_PATH = "Log.txt";

    @Override
    public void addLog(String physicalId) {
        String logEntry = "add." + physicalId + "," + LocalDate.getInstance().getDate().toString();
        writeLog(logEntry);
    }

    @Override
    public void deleteLog(String physicalId) {
        String logEntry = "delete." + physicalId + "," + LocalDate.getInstance().getDate().toString();
        writeLog(logEntry);
    }

    @Override
    public void findLog(String physicalId) {
        String logEntry = "find." + physicalId + "," + LocalDate.getInstance().getDate().toString();
        writeLog(logEntry);
    }

    @Override
    public void loanLog(String physicalId, String borrowerId, String returnDate) {
        String logEntry = "loan." + physicalId + "," + LocalDate.getInstance().getDate().toString() + "," + borrowerId + "," + returnDate;
        writeLog(logEntry);
    }

    @Override
    public void lateLog(String physicalId, String borrowerId, String returnDate) {
        String logEntry = "late." + physicalId + "," + LocalDate.getInstance().getDate().toString() + "," + borrowerId + "," + returnDate;
        writeLog(logEntry);
    }

    @Override
    public void returnLog(String physicalId) {
        String logEntry = "return." + physicalId + "," + LocalDate.getInstance().getDate().toString();
        writeLog(logEntry);
    }


    private void writeLog(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyFileIntegrity(int n) // 유효성 검사
    {
        //DateDao dateDao = DateDaoImpl.getInstance();
        File file = new File(LOG_FILE_PATH);
        if(n==0) // 프로그램 시작 시
        {
            if (!file.exists()) // 존재하지 않을 경우
            {
                try
                {
                    file.createNewFile(); // 파일 생성
                    File f1 = new File("LocalDate.txt");
                    File f2 = new File("BookLoanList.txt");
                    File f3 = new File("Author_Data.txt");
                    File f4 = new File("BookList.txt");
                    File f5 = new File("LoanDuration.txt");

                    f1.delete();
                    f2.delete();
                    f3.delete();
                    f4.delete();
                    f5.delete();
                    f1.createNewFile();
                    f2.createNewFile();
                    f3.createNewFile();
                    f4.createNewFile();
                    f5.createNewFile();

                    DateDao dateDao = DateDaoImpl.getInstance();
                    dateDao.setInitialDate();

                    LoanDurationDao loanDurationDao = LoanDurationDaoImpl.getInstance();
                    loanDurationDao.setLoanDuration(7);

                    System.out.println("Error: DataFile Not Found. Create missed file and initialize existing file");

                }
                catch (Exception e)
                {
                    exit(0); // 오류 발생 시 프로그램 종료
                }
            }

            try (
                    BufferedReader br = new BufferedReader(new FileReader(LOG_FILE_PATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))
            ) {

            } catch (Exception e) {
                System.out.println("Error: Cannot access DataFile. Terminating the program.");
                e.printStackTrace();
                System.exit(0);
            }
        }
        else // 프로그램 실행 도중
        {
            if (!file.exists()) // 존재하지 않을 경우
            {
                try
                {
                    System.out.println("Error: DataFile not found. Terminate the program.");
                    exit(0); // 프로그램 종료
                }
                catch (Exception e)
                {
                    exit(0);
                }
            }

            try (
                    BufferedReader br = new BufferedReader(new FileReader(LOG_FILE_PATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))
            ) {

            } catch (Exception e) {
                System.out.println("Error: Cannot access DataFile. Terminating the program.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
    public List<Log> getLogs(String physicalId) {
        List<Log> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String action = parts[0].split("\\.")[0];
                String logPhysicalId = parts[0].split("\\.")[1];
                String date = parts[1];


                if (logPhysicalId.equals(physicalId)) {
                    if (action.equals("loan")) {
                        String borrowerId = parts[2];
                        String returnDate = parts[3];
                        result.add(new Log(action, logPhysicalId, date, borrowerId, returnDate));
                    }
                    else if (action.equals("Late")) {
                        String borrowerId = parts[2];
                        String returnDate = parts[3];
                        result.add(new Log(action, logPhysicalId, date, borrowerId, returnDate));
                    }
                    else {
                        result.add(new Log(action, logPhysicalId, date));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
