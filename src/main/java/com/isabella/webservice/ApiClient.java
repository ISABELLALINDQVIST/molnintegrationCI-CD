package com.isabella.webservice;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ApiClient {
    private static final String BASE_URL = "http://molnintegrationbooks-env.eba-zr2ertjv.eu-north-1.elasticbeanstalk.com";

    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Välj ett alternativ:");
                System.out.println("1. Hämta alla författare");
                System.out.println("2. Skapa en ny författare");
                System.out.println("3. Hämta alla böcker");
                System.out.println("4. Skapa en ny bok");
                System.out.println("5. Hämta en författare");
                System.out.println("6. Uppdatera en författare");
                System.out.println("7. Radera en författare");
                System.out.println("8. Hämta en bok");
                System.out.println("9. Uppdatera en bok");
                System.out.println("10. Radera en bok");
                System.out.println("0. Avsluta");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        getAllAuthors();
                        break;
                    case 2:
                        scanner.nextLine(); //rens rens
                        System.out.print("Ange namn på författare: ");
                        String authorName = scanner.nextLine();

                        int authorAge = -1;
                        while (true) {
                            System.out.print("Ange ålder: ");
                            try {
                                //läsa ålder
                                authorAge = scanner.nextInt();
                                //om ålder tokig så bryt
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ogiltig inmatning, ange ett heltal för ålder.");
                                scanner.next();
                            }
                        }

                        createAuthor(authorName, authorAge);
                        break;

                    case 3:
                        getAllBooks();
                        break;
                    case 4:
                        System.out.print("Ange titel på bok: ");
                        String bookTitle = scanner.next();
                        System.out.print("Ange ISBN (utan specialtecken!): ");
                        String bookIsbn = scanner.next();
                        System.out.print("Ange författarens ID: ");
                        long authorId = scanner.nextLong();
                        createBook(bookTitle, bookIsbn, authorId);
                        break;
                    case 5:
                        System.out.print("Ange författarens ID: ");
                        long authorIdToGet = scanner.nextLong();
                        getOneAuthor(authorIdToGet);
                        break;
                    case 6:
                        System.out.print("Ange författarens ID: ");
                        long authorIdToUpdate = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("Ange nytt namn: ");
                        String newAuthorName = scanner.nextLine();
                        System.out.print("Ange ny ålder: ");
                        int newAuthorAge;
                        while (true) {
                            try {
                                newAuthorAge = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ogiltig inmatning, ange ett heltal för ålder.");
                                scanner.next();
                            }
                        }
                        updateAuthor(authorIdToUpdate, newAuthorName, newAuthorAge);
                        break;

                    case 7:
                        System.out.print("Ange författarens ID: ");
                        long authorIdToDelete = scanner.nextLong();
                        deleteAuthor(authorIdToDelete);
                        break;
                    case 8:
                        System.out.print("Ange bokens ID: ");
                        long bookIdToGet = scanner.nextLong();
                        getOneBook(bookIdToGet);
                        break;
                    case 9:
                        System.out.print("Ange bokens ID: ");
                        long bookIdToUpdate = scanner.nextLong();
                        System.out.print("Ange ny titel: ");
                        String newBookTitle = scanner.next();
                        System.out.print("Ange ny ISBN: ");
                        String newBookIsbn = scanner.next();
                        System.out.print("Ange författarens ID: ");
                        long newAuthorIdForBook = scanner.nextLong();
                        updateBook(bookIdToUpdate, newBookTitle, newBookIsbn, newAuthorIdForBook);
                        break;
                    case 10:
                        System.out.print("Ange bokens ID: ");
                        long bookIdToDelete = scanner.nextLong();
                        deleteBook(bookIdToDelete);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Ogiltigt val, försök igen.");
                }
            }

            scanner.close();
        }


    //hämta alla författare
    private static void getAllAuthors() {
        try {
            URL url = new URL(BASE_URL + "/authors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Författare: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hämta författare
    private static void getOneAuthor(long id) {
        try {
            URL url = new URL(BASE_URL + "/authors/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Författare: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ny författare
    private static void createAuthor(String name, int age) {
        try {
            URL url = new URL(BASE_URL + "/authors");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"name\": \"%s\", \"age\": %d}", name, age);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("Ny författare skapad med status: " + conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //uppdatera författare
    private static void updateAuthor(long id, String name, int age) {
        try {
            URL url = new URL(BASE_URL + "/authors/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"name\": \"%s\", \"age\": %d}", name, age);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("Författare uppdaterad med status: " + conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //radera författare
    private static void deleteAuthor(long id) {
        try {
            URL url = new URL(BASE_URL + "/authors/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            System.out.println("Författare raderad med status: " + conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hämta alla böcker
    private static void getAllBooks() {
        try {
            URL url = new URL(BASE_URL + "/books");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Böcker: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hämta bok
    private static void getOneBook(long id) {
        try {
            URL url = new URL(BASE_URL + "/books/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Bok: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //skapa bok
    private static void createBook(String title, String isbn, long authorId) {
        try {
            URL url = new URL(BASE_URL + "/books");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"title\": \"%s\", \"ISBN\": \"%s\", \"author\": {\"id\": %d}}", title, isbn, authorId);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("Ny bok skapad med status: " + conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //uppdatera bok
    private static void updateBook(long id, String title, String isbn, long authorId) {
        try {
            URL url = new URL(BASE_URL + "/books/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"title\": \"%s\", \"ISBN\": \"%s\", \"author\": {\"id\": %d}}", title, isbn, authorId);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            System.out.println("Bok uppdaterad med status: " + conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //radera bok
    private static void deleteBook(long id) {
        try {
            URL url = new URL(BASE_URL + "/books/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            System.out.println("Bok raderad med status: " + conn.getResponseCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

