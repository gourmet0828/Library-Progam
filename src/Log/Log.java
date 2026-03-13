package Log;

public class Log {
    private String action;
    private String physicalId;
    private String date;
    private String borrowerId;
    private String returnDate;

    // 기본 생성자
    public Log(String action, String physicalId, String date) {
        this.action = action;
        this.physicalId = physicalId;
        this.date = date;
    }


    public Log(String action, String physicalId, String date, String borrowerId, String returnDate) {
        this.action = action;
        this.physicalId = physicalId;
        this.date = date;
        this.borrowerId = borrowerId;
        this.returnDate = returnDate;
    }


    public String getAction() {
        return action;
    }

    public String getPhysicalId() {
        return physicalId;
    }

    public String getDate() {
        return date;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public String getReturnDate() {
        return returnDate;
    }


    @Override
    public String toString() {
        return action + "." + physicalId + "," + date + (borrowerId != null ? "," + borrowerId + "," + returnDate : "");
    }
}
