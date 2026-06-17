import java.util.ArrayList;

//  …………….. BOOK CLASS………………. 
class Book {

    private String isbn;
    private String title;
    private String author;
    private boolean available;

    // Constructor 1
    public Book() {
        available = true;
    }

    // Constructor 2
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn +
               ", Title: " + title +
               ", Author: " + author +
               ", Available: " + available;
    }
}

// ……………… MEMBER CLASS…………………..
class Member {

    private String memberId;
    private String name;
    private ArrayList<Loan> loans;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        loans = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId +
               ", Name: " + name +
               ", Active Loans: " + loans.size();
    }
}

// ……………………… LOAN CLASS…………………
class Loan {

    private Book book;
    private Member member;
    private String borrowDate;
    private String dueDate;

    public Loan(Book book, Member member,
                String borrowDate, String dueDate) {

        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String toString() {
        return member.getName() +
               " borrowed \"" +
               book.getTitle() +
               "\" on " + borrowDate +
               " due on " + dueDate;
    }
}

// ……………………… LIBRARY CLASS ……………………
class Library {

    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public void lendBook(Book book,
                         Member member,
                         String borrowDate,
                         String dueDate) {

        // Enforce one active loan per book
        if (!book.isAvailable()) {
            System.out.println("\nLoan Rejected!");
            System.out.println(book.getTitle() +
                    " is already on loan.");
            return;
        }

        Loan loan = new Loan(
                book,
                member,
                borrowDate,
                dueDate);

        member.addLoan(loan);
        book.setAvailable(false);

        System.out.println("\n" +
                member.getName() +
                " borrowed " +
                book.getTitle());
    }

    public void returnBook(Book book,
                           Member member) {

        Loan loanToRemove = null;

        for (Loan loan : member.getLoans()) {

            if (loan.getBook() == book) {
                loanToRemove = loan;
                break;
            }
        }

        if (loanToRemove != null) {

            member.removeLoan(loanToRemove);
            book.setAvailable(true);

            System.out.println(
                    "\nBook returned successfully.");
        }
    }

    public void searchBookByTitle(String title) {

        boolean found = false;

        for (Book book : books) {

            if (book.getTitle()
                    .equalsIgnoreCase(title)) {

                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    @Override
    public String toString() {

        String result = "\n…………LIBRARY STATUS………….\n";

        result += "\nBOOKS\n";

        for (Book book : books) {
            result += book + "\n";
        }

        result += "\nMEMBERS\n";

        for (Member member : members) {
            result += member + "\n";
        }

        return result;
    }
}

//   MAIN CLASS  
public class LibraryDemo {

    public static void main(String[] args) {

        Library library = new Library();

        // Create Books
        Book book1 = new Book(
                "ISBN001",
                "Java Programming",
                "James Gosling");

        Book book2 = new Book(
                "ISBN002",
                "Database Systems",
                "Elmasri");

        Book book3 = new Book(
                "ISBN003",
                "Computer Networks",
                "Tanenbaum");

        // Create Members
        Member member1 =
                new Member("M001", "John");

        Member member2 =
                new Member("M002", "Sarah");

        // Add Books
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // Register Members
        library.registerMember(member1);
        library.registerMember(member2);

        // Before operations
        System.out.println("BEFORE OPERATIONS");
        System.out.println(library);

        // Lend Book
        library.lendBook(
                book1,
                member1,
                "17/06/2026",
                "24/06/2026");

        // Rejected Loan
        library.lendBook(
                book1,
                member2,
                "17/06/2026",
                "24/06/2026");

        // Another Loan
        library.lendBook(
                book2,
                member2,
                "17/06/2026",
                "24/06/2026");

        // Search Book
        System.out.println(
                "\nSearching for Java Programming:");
        library.searchBookByTitle(
                "Java Programming");

        // Return Book
        library.returnBook(
                book1,
                member1);

        // After operations
        System.out.println(
                "\nAFTER OPERATIONS");
        System.out.println(library);
    }
}
