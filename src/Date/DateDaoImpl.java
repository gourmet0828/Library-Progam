package Date;

import LoanList.LoanDurationDao;
import LoanList.LoanDurationDaoImpl;

import java.io.*;

import static java.lang.System.exit;

public class DateDaoImpl implements DateDao {

    private final String FILEPATH = "LocalDate.txt";
    private static DateDao instance;

    private DateDaoImpl() {}

    public static DateDao getInstance() {
        if (instance == null) {
            instance = new DateDaoImpl();
        }
        return instance;
    }

    @Override
    public void update() {

        // 1. get Date from localDate
        LocalDate localDate = LocalDate.getInstance();
        Date date = localDate.getDate();

        // 2. update dataFile
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH))) {
            writer.write(date.toString());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Date get() {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            return new Date(reader.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean verifyFileIntegrity(int n) // 유효성 검사
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

                    BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH));
                    writer.write("000101");
                    writer.close();
                    instance.update();

                    LoanDurationDao loanDurationDao = LoanDurationDaoImpl.getInstance();
                    loanDurationDao.setLoanDuration(7);

                    System.out.println("LocalDate.txt doesn't exist. Initialize files");

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
        return file.exists();
    }
    public boolean verifyDate(Date date) {
        LocalDate localDate = LocalDate.getInstance();

        return Integer.parseInt(localDate.getDate().toString()) <= Integer.parseInt(date.toString());
    }

    @Override
    public void setInitialDate()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH));
            writer.write("000101");
            writer.close();
        } catch (Exception e) {
            exit(0);
        }

        Date initialDate = new Date("000101");
        LocalDate localDate = LocalDate.getInstance();
        localDate.setDate(initialDate);
        instance.update();
    }

}
