package AuthorList;
import Constant.Constants;
import Date.DateDao;
import Date.DateDaoImpl;
import LoanList.LoanDurationDao;
import LoanList.LoanDurationDaoImpl;
import Processor.ItemInputProcessor;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.exit;

public class AuthorDataDaoImpl implements AuthorDataDao{
    private final String FILE_PATH = "Author_Data.txt";
    public int addAuthor(String name) {
        if(ItemInputProcessor.processInput(name) != 1)
            return Constants.FAIL;

        int total_num = 0;

        FileReader reader = null;
        try {
            reader = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            // 파일이 없으면 실패하지 않고 새로 생성
            total_num = 0;
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(FILE_PATH, true)); // true: append mode
        } catch (IOException e) {
            return Constants.FAIL;
        }

        BufferedReader bufReader = null;
        if (reader != null) {
            bufReader = new BufferedReader(reader);
        }
        //기본 세팅 끝

        try {
            // 기존 데이터의 총 저자 수 계산
            if (bufReader != null) {
                while (bufReader.readLine() != null) {
                    if(total_num >= 2147483647)
                        return Constants.FAIL;
                    total_num++;
                }
            }

            // 데이터 추가
            writer.write(name + "," + (total_num + 1) + "\n");
            writer.flush(); // 버퍼 데이터를 강제로 작성
            return total_num+1;
        } catch (IOException e) {
            return Constants.FAIL;
        } finally {
            // 리소스 닫기
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                writer.close();
            } catch (IOException e) {
            }
        }
    }


    // 문제 발생시 null 반환
    public ArrayList<Integer> search_author(String name){


        ArrayList<Integer> arr = new ArrayList<>();

        FileReader reader = null;

        try {
            reader = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            return null;
        }

        BufferedReader bufReader = new BufferedReader(reader);
        //기본 세팅 끝

        String line = "";

        try {
            while ((line = bufReader.readLine()) != null) {
                String[] tmp = line.split(",");
                String tmp_name = tmp[0];
                if(name.equals(tmp_name)) {
                    arr.add(Integer.valueOf(tmp[1]));
                }

            }
            reader.close();
            bufReader.close();
        } catch (IOException e) {
            return null;
        }

        return arr;
    }

    //문제 발생시 null 반환
    public String search_author(int num){
        FileReader reader = null;

        try {
            reader = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            return null;
        }

        BufferedReader bufReader = new BufferedReader(reader);
        //기본 세팅 끝

        String line = "";

        try {
            while ((line = bufReader.readLine()) != null) {
                String[] tmp = line.split(",");
                if(Integer.parseInt(tmp[1]) == num) {
                    return tmp[0];
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
    @Override
    public void verifyFileIntegrity(int n) // 유효성 검사
    {
        //DateDao dateDao = DateDaoImpl.getInstance();
        File file = new File(FILE_PATH);
        if(n==0) // 프로그램 시작 시
        {
            if (!file.exists()) // 존재하지 않을 경우
            {
                try
                {
                    file.createNewFile(); // 파일 생성
                    File f1 = new File("LocalDate.txt");
                    File f2 = new File("BookLoanList.txt");
                    File f3 = new File("LoanDuration.txt");
                    File f4 = new File("Log.txt");
                    File f5 = new File("BookList.txt");
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
                    BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))
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
                    BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))
            ) {

            } catch (Exception e) {
                System.out.println("Error: Cannot access DataFile. Terminating the program.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}

