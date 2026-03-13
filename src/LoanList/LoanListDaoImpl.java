package LoanList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.System;

import Account.*;
import ItemList.*;
import Date.*;

import static java.lang.System.exit;

public class LoanListDaoImpl implements LoanListDao {
	private static final String FILE_PATH = "BookLoanList.txt";
	private static final String TEMP_FILE_PATH = "BookLoanListTmp.txt";
	private static LoanListDaoImpl instance;

	private LoanListDaoImpl() {}

    public static LoanListDaoImpl getInstance() {
    	if (instance == null) {
        	instance = new LoanListDaoImpl();
        }
        return instance;
	}

	@Override // 내역 추가
	public String add(Item item) {
		User user = (User) LocalAccount.getInstance().getAccount();
		String phoneNum = user.getPhoneNum();
		String itemId = item.getPhysicalId();

		LoanDurationDao durationDao = LoanDurationDaoImpl.getInstance();
		int loanDuration = durationDao.getLoanDuration();

		Date loanDate = LocalDate.getInstance().getDate();
		Date returnDate = LocalDate.getInstance().getDate().addDays(loanDuration);

		String record = phoneNum + "," + itemId + "," + loanDate.toString() + "," + returnDate.toString();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
			writer.write(record);
			writer.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return returnDate.toString();
	}

	@Override // 내역 삭제
	public void delete(Loan loan)
	{
		LocalAccount localAccount = LocalAccount.getInstance();
		User account = (User)localAccount.getAccount();
		String phoneNum = account.getPhoneNum();
		System.out.println("phoneNUm"+phoneNum);

		try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
			BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE_PATH))) {
			String line;
			String itemIdToDelete = loan.item.getPhysicalId();
			String itemLoanDate = loan.getLoanDate();
			System.out.println("loanDate"+itemLoanDate);

			int count = 0;

			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				String itemId = fields[1];
				String userPhone = fields[0];
				String loanDate = fields[2];
				if ((!userPhone.equals(phoneNum) || !itemId.equals(itemIdToDelete) || !loanDate.equals(itemLoanDate)) || count== 1 ) {
					writer.write(line);
					writer.newLine();
				} else {
					count++;
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(TEMP_FILE_PATH));
			 BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH)))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				writer.write(line); // 한 줄 복사
				writer.newLine();   // 줄바꿈 추가
			}

		} catch (IOException e)
		{
			exit(0);
		}

		File tempFile = new File(TEMP_FILE_PATH);
		tempFile.delete();
		//new File(FILE_PATH).delete();
		//new File(TEMP_FILE_PATH).renameTo(new File(FILE_PATH));
	}

	@Override // 사용자가 대출한 도서 목록 작성
	public List<Loan> find() {
		List<Loan> loanList = new ArrayList<>();
		User user = (User) LocalAccount.getInstance().getAccount();
		String phoneNum = user.getPhoneNum();

		try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				String recordPhoneNum = fields[0];
				if (recordPhoneNum.equals(phoneNum)) {
					String itemId = fields[1];
					ItemListDao dao = new ItemListDaoImpl();
					Item item = dao.getItemByNumber(itemId);
					Date loanDate = new Date(fields[2]);
					Date returnDate = new Date(fields[3]);
					loanList.add(new Loan(user, item, loanDate, returnDate));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return loanList;
	}

	@Override // 사용자 대출 가능 여부 확인
	public boolean userLoanValid() {
		int loanCount = 0;
		User user = (User) LocalAccount.getInstance().getAccount();
		String phoneNum = user.getPhoneNum();
		Date currentDate = LocalDate.getInstance().getDate();

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				String recordPhoneNum = fields[0];
				Date returnDate = new Date(fields[3]);

				if (recordPhoneNum.equals(phoneNum)) {
					if (Integer.parseInt(currentDate.toString()) > Integer.parseInt(returnDate.toString()))
					{
						System.out.println("You cannot borrow books because there are books that are overdue. Please return the books first\n");
						return false;
					} // 연체된 도서가 있는 경우
					loanCount++;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if(loanCount<3){
			return true;
		} else {
			System.out.println("You've already borrowed three books, which you can borrow. Please return the books first.\n");
			return false;
		}

	}

	@Override // 도서가 대출되었는지 확인
	public boolean itemIsLoan(Item item) {
		String itemId = item.getPhysicalId();

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				if (fields[1].equals(itemId))
					return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
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
					File f2 = new File("BookList.txt");
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