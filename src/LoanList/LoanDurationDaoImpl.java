package LoanList;

import Date.DateDao;
import Date.DateDaoImpl;

import java.io.*;

import static java.lang.System.exit;

public class LoanDurationDaoImpl implements LoanDurationDao {
    private static final String FILEPATH = "LoanDuration.txt";
    private static final int DEFUALTLOANDURATION = 7;
    private static LoanDurationDao instance;


    private LoanDurationDaoImpl() {}

    public static LoanDurationDao getInstance() {
        if (instance == null) {
            instance = new LoanDurationDaoImpl();
        }
        return instance;
    }

    @Override
    public void setLoanDuration(int days) {
        String day = Integer.toString(days);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH))) {
            writer.write(day);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public int getLoanDuration() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void verifyFileIntegrity(int n) // 유효성 검사
    {
        //DateDao dateDao = DateDaoImpl.getInstance();
        File file = new File(FILEPATH);
        if(n==0) // 프로그램 시작 시
        {
            if (!file.exists()) // 존재하지 않을 경우
            {
                try
                {
                    file.createNewFile(); // 파일 생성
                    File f1 = new File("LocalDate.txt");
                    File f2 = new File("BookList.txt");
                    File f3 = new File("Author_Data.txt");
                    File f4 = new File("Log.txt");
                    File f5 = new File("BookLoanList.txt");

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
                    loanDurationDao.setLoanDuration(DEFUALTLOANDURATION);

                    System.out.println("Error: DataFile Not Found. Create missed file and initialize existing file");

                }
                catch (Exception e)
                {
                    exit(0); // 오류 발생 시 프로그램 종료
                }
            }

            try (
                    BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH, true))
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
                    BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH, true))
            ) {

            } catch (Exception e) {
                System.out.println("Error: Cannot access DataFile. Terminating the program.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
