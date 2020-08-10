package objects;

public class Textbook {
	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	private Integer number;
	private String author;
	private String title;
	private String publisher;
	private String year;
	private String isbn;

	public Integer getNumber() {
		return number;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getYear() {
		return year;
	}

	public String getIsbn() {
		return isbn;
	}
}
