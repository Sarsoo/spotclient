package sarsoo.spotframework.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Album extends SpotifyObject {

	private AlbumType type;
	private ArrayList<Artist> artists;
	private ArrayList<String> genres;
	
	private ArrayList<Image> images;
	
	private String label;
	
	private ArrayList<Track> tracks;
	
	public Album(String name, String id, String uri, String url, AlbumType type, ArrayList<Artist> artists) {
		this.name = name;
		this.id = id;
		this.uri = uri;
		this.url = url;
		this.type = type;
		this.artists = artists;
	}

	public AlbumType getType() {
		return type;
	}

	public void setType(AlbumType type) {
		this.type = type;
	}

	public ArrayList<Artist> getArtists() {
		return artists;
	}

	public void setArtists(ArrayList<Artist> artists) {
		this.artists = artists;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
	@Override
	public String toString() {
		return "album: " + name + " / " + String.join(", ", artists.stream().map(Artist::getName).collect(Collectors.toList()));
	}
	
} 
