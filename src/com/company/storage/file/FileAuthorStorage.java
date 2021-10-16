package com.company.storage.file;

import com.company.domain.Author;
import com.company.storage.AuthorStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAuthorStorage implements AuthorStorage {
    @Override
    public void save(Author author) {
        try {
            FileWriter fileWriter = new FileWriter("authors.txt");

            String pattern = "%d %s %s";
            String format = String.format(pattern, author.getId(), author.getName(), author.getDescription());

            fileWriter.write(format);
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Author> getAll() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            List<Author> authors = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                Author author = new Author(Integer.parseInt(s[0]), s[1], s[2]);
                authors.add(author);
            }
            return authors;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void rewrite(List<Author> authors) {
        try {
            FileWriter fileWriter = new FileWriter("authors.txt");
            String pattern = "%d %s %s";
            for (Author author : authors) {
                String format = String.format(pattern, pattern, author.getId(), author.getName(), author.getDescription());
                fileWriter.write(format);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDescriptionById(int id, String description) {
        List<Author> authors = getAll();
        for (Author author : authors) {
            if (author.getId() == id) {
                author.setDescription(description);
                break;
            }
        }
        rewrite(authors);
    }

    @Override
    public String updateNameById(int id, String name) {
        String old = null;
        List<Author> authors = getAll();
        for (Author author : authors) {
            if (author.getId() == id) {
                old = author.getName();
                author.setName(name);
                break;
            }
        }
        rewrite(authors);
        return old;
    }

    @Override
    public Author deleteById(int id) {
        List<Author> authors = getAll();
        Author old = null;
        for (Author author : authors) {
            if (author.getId() == id) {
                old = author;
                authors.remove(author);
                break;
            }
        }
        rewrite(authors);
        return old;
    }

    @Override
    public Author deleteByName(String name) {
        List<Author> authors = getAll();
        Author old = null;
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                old = author;
                authors.remove(author);
                break;
            }
        }
        rewrite(authors);
        return old;
    }

    @Override
    public boolean contains(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
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
    public boolean contains(String name) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(name)) {
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
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                Author author1 = new Author(Integer.parseInt(s[0]), s[1], s[2]);
                if(author1.equals(author)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Author[] findAll() {
        return getAll().toArray(new Author[0]);
    }

    @Override
    public Author findById(int id) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(Integer.parseInt(s[0]) == id) {
                    return new Author(Integer.parseInt(s[0]), s[1], s[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author findByName(String name) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("authors.txt"));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                if(s[1].equals(name)) {
                    return new Author(Integer.parseInt(s[0]), s[1], s[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
