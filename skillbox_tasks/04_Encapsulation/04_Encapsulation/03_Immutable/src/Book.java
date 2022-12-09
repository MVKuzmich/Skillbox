public class Book {
    private final String bookTitle;
    private final String bookAuthor;
    private final int bookCountPages;
    private final int numberISBN;

    public Book(String bookTitle, String bookAuthor, int bookCountPages, int numberISBN) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookCountPages = bookCountPages;
        this.numberISBN = numberISBN;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookCountPages() {
        return bookCountPages;
    }

    public int getNumberISBN() {
        return numberISBN;
    }
}
