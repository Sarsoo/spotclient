package sarsoo.spotframework.model;

public class Image {
	
	private int height;
	private String url;
	private int width;
	
	public Image(String url, int height, int width) {
		this.url = url;
		this.height = height;
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getWidth() {
		return width;
	}

}
