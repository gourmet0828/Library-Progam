package ItemList;
import java.util.*;

public abstract class Item
{
    protected String physicalId = null; // 물리적 고유번호
    protected String isbnId= null; // isbn 번호
    protected String title; // 도서 제목
    protected List<String> author; // 저자 번호(리스트로 관리)
    protected String isRegistered= null; // 도서 시스템 등록 여부 --> O/X

    protected String lng; // 도서 언어 (2차 요구사항 대비)

    public abstract String getPhysicalId();
    public abstract String getIsbnId();
    public abstract String getTitle();
    public abstract List<String> getAuthor();
    public abstract String getIsRegistered();

}