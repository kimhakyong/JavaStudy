package refactoring.java.r06_ec;

public class Book {
    private String title;
    private String isbn;
    private String price;

    private Author author;

    public Book(String title, String isbn, String price, String authorName, String authorMail) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;

        this.author = new Author(authorName, authorMail);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public void setAuthorName(String name) {
        author.setName(name);
    }

    public String getAuthorMail() {
        return author.getMail();
    }

    public void setAuthorMail(String mail) {
        author.setMail(mail);
    }

    public ImmutableAuthor getAuthor() {
        return author;
    }

    public String toXml() {
        String author = tag("author",
                tag("name", this.author.getName()) + tag("mail", this.author.getMail()));
        String book = tag("book",
                tag("title", title) + tag("isbn", isbn) + tag("price", price) + author);

        return book;
    }

    private String tag(String element, String content) {
        return "<" + element + ">" + content + "</" + element + ">";
    }
}
