package com.company.storage.file;

import com.company.domain.Author;
import com.company.domain.Book;
import com.company.storage.BookStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBookStorage implements BookStorage {
    @Override
    public void save(Book book) {
        try {
            FileWriter fileWriter = new FileWriter("books.txt");

            String pattern = "%d %s %s %d %d";
            String format = String.format(pattern, book.getId(), book.getTitle(), book.getDescription(), book.getAuthor().getId(), book.getPrice());

            fileWriter.write(format);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rewrite(List<Book> books) {
        try {
            FileWriter fileWriter = new FileWriter("books.txt");
            String pattern = "%d %s %s %d %d";
            for (Book book : books) {
                fileWriter.write(String.format(pattern, book.getId(), book.getTitle(), book.getDescription(), book.getAuthor().getId(), book.getPrice()));
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Author getAuthorById(int id) throws IOException {
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("authors.txt"));
        String newLine;
        Author author = null;
        while((newLine = bufferedReader1.readLine()) != null) {
            String[] str = newLine.split(" ");
            if(Integer.parseInt(str[0]) == id) {
                author = new Author(Integer.parseInt(str[0]), str[1], str[2]);
                break;
            }
        }
        return author;
    }

    private List<Book> findAll() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("books.txt"));
            List<Book> books = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                int authorId = Integer.parseInt(s[3]);
                Book book = new Book(Integer.parseInt(s[0]), s[1], s[2], getAuthorById(authorId), Integer.parseInt(s[4]));
                books.add(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String updateTitleById(int id, String newTitle) {
        String old = null;
        List<Book> books =findAll();
        for (Book book : books) {
            if(book.getId() == id) {
                old = book.getTitle();
                book.setTitle(newTitle);
                break;
            }
        }
        rewrite(books);
        return old;
    }

    @Override
    public void updateDescriptionById(int id, String newDescription) {
        List<Book> books =findAll();
        for (Book book : books) {
            if(book.getId() == id) {
                book.setDescription(newDescription);
                break;
            }
        }
        rewrite(books);
    }

    @Override
    public int updatePriceById(int id, int newPrice) {
        int old = 0;
        List<Book> books =findAll();
        for (Book book : books) {
            if(book.getId() == id) {
                old = book.getPrice();
                book.setPrice(newPrice);
                break;
            }
        }
        rewrite(books);
        return old;
    }

    @Override
    public Author updateAuthor(int id, Author author) {
        Author old =null;
        List<Book> books =findAll();
        for (Book book : books) {
            if(book.getId() == id) {
                old = book.getAuthor();
                book.setAuthor(author);
                break;
            }
        }
        rewrite(books);
        return old;
    }

    @Override
    public Book deleteById(int id) {
        List<Book> books =findAll();
        Book old = null;
        for (Book book : books) {
            old = book;
            if(book.getId() == id) {
                books.remove(book);
                break;
            }
        }
        rewrite(books);
        return old;
    }

    @Override
    public Book deleteByTitle(String title) {
        List<Book> books =findAll();
        Book old = null;
        for (Book book : books) {
            old = book;
            if(book.getTitle().equals(title)) {
                books.remove(book);
                break;
            }
        }
        rewrite(books);
        return old;
    }

    @Override
    public void deleteAllByAuthor(Author author) {
        List<Book> books = findAll();
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i).getAuthor().equals(author)) {
                books.remove(books.get(i));
            }
        }
        rewrite(books);
    }

    @Override
    public Book getById(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("books.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return new Book(Integer.parseInt(s[0]), s[1], s[2], getAuthorById(id), Integer.parseInt(s[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getByTitle(String title) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("books.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(title)) {
                    return new Book(Integer.parseInt(s[0]), s[1], s[2], getAuthorById(Integer.parseInt(s[3])), Integer.parseInt(s[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] getAll() {
        return findAll().toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByPrice(int price) {
        List<Book> books = findAll();
        List<Book> books1 = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i).getPrice() == price) {
                books1.add(books.get(i));
            }
        }
        return books1.toArray(new Book[0]);
    }

    @Override
    public Book[] getAllByAuthor(Author author) {
        List<Book> books = findAll();
        List<Book> books1 = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i).getAuthor().getName().equals(author.getName())) {
                books1.add(books.get(i));
            }
        }
        return books1.toArray(new Book[0]);
    }

    @Override
    public boolean contains(String title) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("books.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(title)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Book book) {
        List<Book> books = findAll();
        for (Book book1 : books) {
            if(book1.equals(book)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("books.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean contains(Author author) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(author.getName())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
