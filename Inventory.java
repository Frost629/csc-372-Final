package csc372Final;
import java.util.ArrayList;

public class Inventory {

    private final ArrayList<Book> mainInventory = new ArrayList<>();
    private final ArrayList<Book> lendingInventory = new ArrayList<>();

    public boolean addBook(int id, String title, String author, String isbn, int numberOfPages) {
        if (findById(mainInventory, id) != null || findById(lendingInventory, id) != null) {
            return false;
        }
        mainInventory.add(new Book(id, title, author, isbn, numberOfPages));
        return true;
    }

    public boolean borrowBook(int id) {
        return moveBook(mainInventory, lendingInventory, id);
    }

    public boolean returnBook(int id) {
        return moveBook(lendingInventory, mainInventory, id);
    }

    public boolean isBorrowed(int id) {
        return findById(lendingInventory, id) != null;
    }

    public void printAll() {
        for (Book book : mainInventory) {
            book.printBookInfo();
        }
    }

    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> matches = new ArrayList<>();
        String searchText = title.toLowerCase();
        for (Book book : mainInventory) {
            if (book.getTitle().toLowerCase().contains(searchText)) {
                matches.add(book);
            }
        }
        return matches;
    }

    public int getMainInventoryCount() {
        return mainInventory.size();
    }

    public int getBorrowedCount() {
        return lendingInventory.size();
    }

    private boolean moveBook(ArrayList<Book> from, ArrayList<Book> to, int id) {
        Book book = findById(from, id);
        if (book == null) {
            return false;
        }
        from.remove(book);
        to.add(book);
        return true;
    }

    private Book findById(ArrayList<Book> books, int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
}