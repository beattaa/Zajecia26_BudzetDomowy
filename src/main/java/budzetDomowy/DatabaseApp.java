package budzetDomowy;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class DatabaseApp
{

   private Scanner scanner;
   private TransactionDao transactionDAO = new TransactionDao();

   public void run()
   {
      while (true)
      {
         System.out.println();
         System.out.println("1. Dodaj transakcje");
         System.out.println("2. Zaktualizuj transakcję");
         System.out.println("3. Usuń transakcję");
         System.out.println("4. Wyświetl wszystkie przychody");
         System.out.println("5. Wyświetl wszystkie wydatki");
         System.out.println("------------");
         System.out.println("0. Zakończ");

         scanner = new Scanner(System.in);
         String option = scanner.nextLine();
         switch (option)
         {
            case "1":
               createTransactionRow();
               break;
            case "2":
               updateTransaction();
               break;
            case "3":
               System.out.println("Wpływy:");
               System.out.println(transactionDAO.read(1));
               System.out.println("Wydatki:");
               System.out.println(transactionDAO.read(1));
               deleteTransaction();
               break;
            case "4":
               System.out.println(transactionDAO.read(1));
               break;
            case "5":
               System.out.println(transactionDAO.read(2));
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

   private void createTransactionRow()
   {
      int type = getType();

      System.out.println("Podaj opis: ");
      String description = scanner.nextLine();

      System.out.println("Podaj wysokosc transakcji: ");
      double amount = scanner.nextDouble();
      scanner.nextLine();

      LocalDate date = getDateFromUser();
      Transaction transaction = new Transaction(type, description, amount, date);
      transactionDAO.create(transaction);
   }

   private void updateTransaction()
   {
      System.out.println("Podaj id aktualizowanej transakcji:");
      long id = scanner.nextLong();
      scanner.nextLine();
      int type = getType();

      System.out.println("Podaj nowy opis:");
      String description = scanner.nextLine();

      System.out.println("Podaj nową wartość:");
      double amount = scanner.nextDouble();
      scanner.nextLine();
      LocalDate date = getDateFromUser();

      Transaction transaction = new Transaction(id, type, description, amount, date);
      transactionDAO.update(transaction);
   }

   private void deleteTransaction()
   {
      System.out.print("Podaj nr transakcji do usunięcia: ");
      int id = scanner.nextInt();
      transactionDAO.delete(id);
   }

   private int getType()
   {
      int type;
      do
      {
         System.out.println("Typy transakcji: przychod (1) lub wydatek (2)");
         System.out.println("Wybierz typ: ");
         type = scanner.nextInt();
         scanner.nextLine();
         if (type != 1 & type != 2)
         {
            System.err.println("Podałeś niepoprawny typ transakcji");
         }
      }
      while (type != 1 & type != 2);
      return type;
   }

   private LocalDate getDateFromUser()
   {
      LocalDate date;
      try
      {
         System.out.println("Podaj date (format YYYY-MM-DD): ");
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         date = LocalDate.parse(scanner.nextLine(), formatter);

      }
      catch (DateTimeException exception)
      {
         System.err.println("Niepoprawny format daty");
         exception.printStackTrace();
         date = null;
      }
      return date;
   }

}
