package sarsoo.spotframework.model;

import java.util.ArrayList;

public class Artist extends SpotifyObject {
	
	private ArrayList<Image> images;
	
	private ArrayList<String> genres;
	
	public Artist(String name, String id, String uri, String url) {
		this.name = name;
		this.id = id;
		this.uri = uri;
		this.url = url;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "artist: " + name;
	}
}
