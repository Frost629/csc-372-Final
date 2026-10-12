package csc372Final;
import java.util.ArrayList;
import java.util.Scanner;

public class LibrarySystem {

    private static final Scanner input = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();

    public static void main(String[] args) {
        inventory.addBook(1, "The Hobbit", "J.R.R. Tolkien", "9780547928227", 300);
        inventory.addBook(2, "To Kill a Mockingbird", "Harper Lee", "9780061120084", 336);
        inventory.addBook(3, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 180);

        System.out.println("=== Basic Library System ===");
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(input.nextLine().trim());
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        borrowBook();
                        break;
                    case 3:
                        returnBook();
                        break;
                    case 4:
                        searchByTitle();
                        break;
                    case 5:
                        printAllBooks();
                        break;
                    case 6:
                        System.out.println("Exiting the program. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a number from 1 to 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 6.");
            }
        }

        input.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add Book        3. Return Book        5. Print All Books");
        System.out.println("2. Borrow Book     4. Search by Title    6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addBook() {
        int id = readInt("Enter book ID: ");
        String title = readText("Enter title: ");
        String author = readText("Enter author: ");
        String isbn = readText("Enter ISBN number: ");
        int pages = readInt("Enter number of pages: ");

        if (inventory.addBook(id, title, author, isbn, pages)) {
            System.out.println("Book added to the library.");
        } else {
            System.out.println("A book with ID " + id + " already exists. Book was not added.");
        }
    }

    private static void borrowBook() {
        int id = readInt("Enter the ID of the book to borrow: ");

        if (inventory.borrowBook(id)) {
            System.out.println("Book successfully borrowed.");
        } else if (inventory.isBorrowed(id)) {
            System.out.println("Book ID " + id + " is already borrowed.");
        } else {
            System.out.println("No book found with ID " + id + ".");
        }
    }

    private static void returnBook() {
        int id = readInt("Enter the ID of the book to return: ");

        if (inventory.returnBook(id)) {
            System.out.println("Book successfully returned.");
        } else if (inventory.getBorrowedCount() == 0) {
            System.out.println("No books are currently borrowed, so there is nothing to return.");
        } else {
            System.out.println("Book ID " + id + " is not in the lending list.");
        }
    }

    private static void searchByTitle() {
        String title = readText("Enter a full or partial title: ");
        ArrayList<Book> matches = inventory.searchByTitle(title);

        if (matches.isEmpty()) {
            System.out.println("No matching book found.");
        } else {
            System.out.println("Found " + matches.size() + " matching book(s):");
            for (Book book : matches) {
                book.printBookInfo();
            }
        }
    }

    private static void printAllBooks() {
        if (inventory.getMainInventoryCount() == 0) {
            System.out.println("No books are currently available in the library.");
        } else {
            System.out.println("Books available (" + inventory.getMainInventoryCount() + "):");
            inventory.printAll();
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("This field cannot be blank.");
        }
    }
}