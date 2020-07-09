package budzetDomowy;

import java.sql.Date;
import java.util.Scanner;

public class DatabaseApp {

    private Scanner scanner;
    private DAO transactionDAO = new TransactionDAO();

    public void run() {
        while (true) {
            System.out.println();
            System.out.println("1. Dodaj transakcje");
            System.out.println("2. Zaktualizuj opis transakcji");
            System.out.println("3. Usuń transakcję");
            System.out.println("4. Wyświetl wszystkie przychody");
            System.out.println("5. Wyświetl wszystkie wydatki");
            System.out.println("------------");
            System.out.println("0. Zakończ");

            scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    createTransactionRow();
                    break;
                case "2":
                    updateTransactionDescription();
                    break;
                case "3":
                    System.out.println("Wpływy:");
                    transactionDAO.select(1);
                    System.out.println("Wydaatki:");
                    transactionDAO.select(2);
                    deleteTransaction();
                    break;
                case "4":
                    transactionDAO.select(1);
                    break;
                case "5":
                    transactionDAO.select(2);
                    break;
                case "0":
                    scanner.close();
                    transactionDAO.close();
                    break;
                default:
                    System.err.println("Nie ma takiej opcji");
            }
        }
    }

    private void createTransactionRow() {
        int type;
        String description;
        double amount;
        Date date;
        do {
            System.out.println("Utworz nową transakcję: przychod (typ 1) lub wydatek (typ 2)");
            System.out.println("Podaj typ: ");
            type = scanner.nextInt();
            scanner.nextLine();
            if (type != 1 & type != 2) {
                System.err.println("Podałeś niepoprawny typ transakcji");
            }
        }
        while (type != 1 & type != 2);
        System.out.println("Podaj opis: ");
        description = scanner.nextLine();
        System.out.println("Podaj wysokosc transakcji: ");
        amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj date (format YYYY-MM-DD): ");
        date = Date.valueOf(scanner.nextLine());
        transactionDAO.create(type, description, amount, date);
    }

    private void updateTransactionDescription() {
        String value;
        int id;
        System.out.println("Podaj nowy opis:");
        value = scanner.nextLine();
        System.out.println("Podaj id aktualizowanej transakcji:");
        id = scanner.nextInt();
        transactionDAO.update(value, id);
    }

    private void deleteTransaction() {
        System.out.print("Podaj nr transakcji do usunięcia: ");
        int id = scanner.nextInt();
        transactionDAO.delete(id);
    }
}
