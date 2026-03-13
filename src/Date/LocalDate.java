package Date;

public class LocalDate {
    private static LocalDate instance;
    private Date date;

    private LocalDate() {
        DateDao dao = DateDaoImpl.getInstance();
        this.date = dao.get();
    }

    public static LocalDate getInstance() {
        if (instance == null) {
            instance = new LocalDate();
        }
        return instance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {

        DateDaoImpl dao = (DateDaoImpl) DateDaoImpl.getInstance();
        if(dao.verifyDate(date))
            this.date = date;

    }
}
