package ItemList;
import java.util.*;

public interface ItemListDao
{
    // 도서 추가
    List<String> addBook(Item newBook, int quantityToAdd, int mode); // mode = 0 -> 이미 등록되어 있는 isbn 번호의 책 추가
    // mode = 1 -> 기존에 등록되어있지 않은 새로운 isbn 번호의 책 추가

    // 일치하는 도서 검색 (저자는 저자번호로 비교)
    List<Item> getItemByTitleAndAuthor(String title, List<String> author); // 제목이랑 저자 n명 모두 동일한 도서 리스트 반환;
    List<Item> getItemByTitleOrAuthor(String src, List<Integer> author, int mode); // 제목이 같거나 도서 저자가 포함된 도서 반환(도서 저자의 경우 한 명만 입력받고 포함되어 있는 도서 검색하는 꼴)

    // 도서 등록 여부 변경 (도서 발견과 삭제)
    int setBook(Item itm, String set); // (set에 따라 레코드 O => X or X => O 변경)
    // 도서 추가할 때 도서의 발견 경우인지 구분하려면, getItemByTitleAndAuthor로 받은 list 중에서 확인한 다음에, 이거 메소드 인자값 set = "O"로 쓰면 될 듯
    // 도서 삭제할 때는 해당 도서 book 객체랑 인자값 set = "X"로 주고


    // 등록된 도서 수 반환
    int getResigteredBookNum(); // 도서목록파일에 등록되어있는 도서의 수 반환 ('O' 갯수 반환)

    // 파일 유효성 검사
    void verifyFileIntegrity(int n); // 파일 유효성 검사

    public Item getItemByNumber(String PhysicalId);
}