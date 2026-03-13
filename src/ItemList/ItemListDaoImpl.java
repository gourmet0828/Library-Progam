package ItemList;

import java.io.*;
import java.util.*;

import Constant.Constants;
import Date.DateDao;
import Date.DateDaoImpl;
import LoanList.LoanDurationDao;
import LoanList.LoanDurationDaoImpl;

import static java.lang.System.exit;

// 도서 추가
// 레코드 번호 중복 되지 않도록 추가
// 동명동저이책 -> 고유번호 다름 -> 행 추가해서 정렬하기
// 동명동저동책 -> 고유번호 동일 -> 행 추가가 아닌 도서 갯수 추가
// 동명이저, 이명이저, 이명동저 -> 다른 책이므로 고유번호 다름 -> 행 추가해서 정렬

public class ItemListDaoImpl implements ItemListDao
{
    private final String BOOKLIST_FIlEPATH = "BookList.txt";
    //private final String BOOKLIST_TMP_FILEPATH = "BookListTmp.txt";

    @Override
    public List<String> addBook(Item newBook, int quantityToAdd, int mode) // 책 추가하는 메소드, mode = 1 -> 새로운 isbn 책 추가, mode = 0 -> 기존 isbn 책 추가
    {
        // 추가할 때
        int newPhysicalId = -1; // 새롭게 할당할 물리적 고유번호 변수
        int newIsbnId = -1; // 새롭게 할당할 isbn 번호 변수

        List<String> addBookList = new ArrayList<>(); // 추가한 도서의 물리번호를 담아둘 list

        // 고유번호 생성
        try(BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH)))
        {
            String tmpRecord;

            while ((tmpRecord = br.readLine()) != null)
            {
                String[] parts = tmpRecord.split(",");
                String tmpPhysicalId = parts[0].trim();
                String tmpIsbnId = parts[1].trim();

                int tmpIsbnIdInt = Integer.parseInt(tmpIsbnId); // Isbn 번호 integer로
                if(tmpIsbnIdInt>=newIsbnId)
                    newIsbnId = tmpIsbnIdInt;
                newPhysicalId = Integer.parseInt(tmpPhysicalId); // 물리적 번호 integer로
            }
            //System.out.printf("%04d\n", i);
        }
        catch(Exception e)
        {
            exit(0);
        }
        // 고유번호 만들어짐

        newPhysicalId++;
        newIsbnId++;

        // 물리적 고유번호 7자리 초과하면 exit
        if(newPhysicalId+quantityToAdd>9999999)
        {
            System.out.println("Exceed Physical-Id");
            exit(0);
        }
        // isbn 번호 4자리 초과하면 exit
        if(newIsbnId>9999)
        {
            System.out.println("Exceed Isbn-Id");
            exit(0);
        }
        //newId = String.format("%04d", i);

        String addPhysicalId = String.format("%07d", newPhysicalId);
        String addIsbnId = String.format("%04d", newIsbnId);
        String addTitle = newBook.getTitle();
        String addAuthor = String.join("/", newBook.getAuthor());
        String addIsRegistered = "O";
        String record; // 추가할 레코드
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKLIST_FIlEPATH, true)))
        {
            if(mode==1) // mode == 1 이면 새로운 책 추가
            {
                for (int i = 0; i < quantityToAdd; i++)
                {
                    addPhysicalId = String.format("%07d", newPhysicalId + i);
                    record = addPhysicalId + "," + addIsbnId + "," + addTitle + "," + addAuthor + "," + addIsRegistered;
                    //System.out.println("new Record : "+ record);
                    bw.write(record);
                    bw.newLine(); // 줄 바꿈 추가
                    addBookList.add(addPhysicalId);
                }
            }
            else if(mode==0) // mo
            {
                for (int i = 0; i < quantityToAdd; i++)
                {
                    addPhysicalId = String.format("%07d", newPhysicalId + i);
                    record = addPhysicalId + "," + newBook.getIsbnId() + "," + addTitle + "," + addAuthor + "," + addIsRegistered;
                    //System.out.println("new Record : "+ record);
                    bw.write(record);
                    bw.newLine(); // 줄 바꿈 추가
                    addBookList.add(addPhysicalId);
                }
            }
        }
        catch (Exception e)
        {
            exit(0);
        }
        return addBookList;
    }


    @Override
    public List<Item> getItemByTitleAndAuthor(String title, List<String> author) // 제목이랑 저자고유번호 n명 같은 도서 리스트 반환
    {
        List<Item> srcBookList = new ArrayList<>(); // 여기에 담아서 반환
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH)))
        {
            String tmpRecord; // 데이터 파일에서 읽어온 하나의 레코드 저장
            while ((tmpRecord = br.readLine()) != null)
            {
                String[] parts = tmpRecord.split(",");
                String tmpPhysicalId = parts[0].trim();
                String tmpIsbnId = parts[1].trim();
                String tmpTitle = parts[2].trim();
                String tmpAuthor = parts[3].trim();
                String tmpIsRegistered = parts[4].trim();

                String[] authorPart = tmpAuthor.split("/");
                List<String> tmpAuthorList = Arrays.asList(authorPart);
                Collections.sort(tmpAuthorList);

                if(tmpTitle.equals(title) && author.equals(tmpAuthorList))
                    srcBookList.add(new Book(tmpPhysicalId, tmpIsbnId, tmpTitle, tmpAuthorList, tmpIsRegistered));
            }
        }
        catch (Exception e)
        {
            exit(0);
        }
        return srcBookList;
    }

    @Override
    public List<Item> getItemByTitleOrAuthor(String title, List<Integer> author, int mode) // 검색 단어로 도서 제목, 저자고유번호 비교해서 반환 mode가 1일땐 isRegistered가 O인것만 0일때는 O/X 둘다 반환
    {
        List<Item> srcBookList = new ArrayList<>(); // 여기에 담아서 반환
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH)))
        {
            String tmpRecord; // 데이터 파일에서 읽어온 하나의 레코드 저장
            while ((tmpRecord = br.readLine()) != null) {
                String[] parts = tmpRecord.split(","); // , 기준으로 나누어서 parts에 저장
                String tmpPhysicalId = parts[0].trim();
                String tmpIsbnId = parts[1].trim();
                String tmpTitle = parts[2].trim();
                String tmpAuthor = parts[3].trim();
                String tmpIsRegistered = parts[4].trim();

                String[] authorPart = tmpAuthor.split("/"); // 저자 부분은 다시 /로 나눠서
                List<String> tmpAuthorList = Arrays.asList(authorPart); // tmpAuthorList에 저장

                if(tmpIsRegistered.equals("X"))
                    if(mode == 1)
                        continue;

                if (title.equals(tmpTitle))
                    srcBookList.add(new Book(tmpPhysicalId, tmpIsbnId, tmpTitle, tmpAuthorList, tmpIsRegistered));
                else
                {
                    boolean flag = false;
                    for(String s : tmpAuthorList)
                    {
                        for(Integer I : author)
                        {
                            if(flag==true)
                                break;
                            if(I.toString().equals(s))
                            {

                                srcBookList.add(new Book(tmpPhysicalId, tmpIsbnId, tmpTitle, tmpAuthorList, tmpIsRegistered));
                                flag = true;
                            }
                        }
                        if(flag == true)
                            break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            exit(0);
        }
        return srcBookList;
    }


    @Override
    public int setBook(Item itm, String set) // 도서 삭제 혹은 발견
    {
        List<Item> tmpBookList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH)))
        {
            String tmpRecord;
            while ((tmpRecord = br.readLine()) != null)
            {
                String[] parts = tmpRecord.split(",");
                String tmpPhysicalID = parts[0].trim();
                String tmpIsbnId = parts[1].trim();
                String tmpTitle = parts[2].trim();
                String tmpAuthor = parts[3].trim();
                String tmpIsRegistered = parts[4].trim();

                String[] authorPart = tmpAuthor.split("/"); // 저자 부분은 다시 /로 나눠서
                List<String> tmpAuthorList = Arrays.asList(authorPart); // tmpAuthorList에 저장

                if (tmpPhysicalID.equals(itm.getPhysicalId())) // 물리번호 비교해서 같으면 set 인자값으로 변경
                    tmpBookList.add(new Book(tmpPhysicalID, tmpIsbnId, tmpTitle, tmpAuthorList, set));
                else
                    tmpBookList.add(new Book(tmpPhysicalID, tmpIsbnId, tmpTitle, tmpAuthorList, tmpIsRegistered));
            }
        } catch (Exception e)
        {
            exit(0);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKLIST_FIlEPATH, false)))
        {
            for (Item book : tmpBookList)
            {
                String author = String.join("/", book.getAuthor());
                bw.write(book.getPhysicalId() + "," + book.getIsbnId() + "," + book.getTitle() + "," + author + "," + book.isRegistered);
                bw.newLine(); // 줄 바꿈 추가
            }
        } catch (Exception e)
        {
            exit(0);
        }
        return Constants.SUCCESS;
    }

    @Override
    public int getResigteredBookNum() // 등록된 도서 수 반환
    {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH)))
        {
            String tmpRecord;
            while ((tmpRecord = br.readLine()) != null)
            {
                String[] parts = tmpRecord.split(",");
                String tmpIsRegistered = parts[4].trim();

                if(tmpIsRegistered.equals("O"))
                    count++;

            }
        } catch (Exception e)
        {
            exit(0);
        }
        return count;
    }

    @Override
    public Item getItemByNumber(String PhysicalId)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH)))
        {
            String tmpRecord;
            while ((tmpRecord = br.readLine()) != null)
            {
                String[] parts = tmpRecord.split(",");
                String tmpPhysicalId = parts[0].trim();
                String tmpIsbnId = parts[1].trim();
                String tmpTitle = parts[2].trim();
                String tmpAuthor = parts[3].trim();
                String tmpIsRegistered = parts[4].trim();

                String[] authorPart = tmpAuthor.split("/");
                List<String> tmpAuthorList = Arrays.asList(authorPart);
                Collections.sort(tmpAuthorList);

                if(tmpPhysicalId.equals(PhysicalId))
                    return new Book(tmpPhysicalId, tmpIsbnId, tmpTitle, tmpAuthorList, tmpIsRegistered);
            }
        }
        catch (Exception e)
        {
            exit(0);
        }

        return null;
    }

    @Override
    public void verifyFileIntegrity(int n) // 유효성 검사
    {
        //DateDao dateDao = DateDaoImpl.getInstance();
        File file = new File(BOOKLIST_FIlEPATH);
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
                    File f4 = new File("Log.txt");
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
                    BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKLIST_FIlEPATH, true))
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
                    BufferedReader br = new BufferedReader(new FileReader(BOOKLIST_FIlEPATH));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKLIST_FIlEPATH, true))
            ) {

            } catch (Exception e) {
                System.out.println("Error: Cannot access DataFile. Terminating the program.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}