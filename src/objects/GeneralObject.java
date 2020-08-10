package objects;

//deliverables and labs extend this class
abstract public class GeneralObject {
	protected Integer number;
	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected String title;
	protected String url;

	public Integer getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}
}
