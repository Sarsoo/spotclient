package sarsoo.spotframework.model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Track extends SpotifyObject {
	
	private ArrayList<Artist> artists = new ArrayList<>();
	private Album album;
	
	public Track(String name, String id, String uri, String url, Album album, ArrayList<Artist> artists) {
		this.name = name;
		this.id = id;
		this.uri = uri;
		this.url = url;
		this.album = album;
		this.artists = artists;
	}
	
	public ArrayList<Artist> getArtists() {
		return artists;
	}
	public void setArtists(ArrayList<Artist> artists) {
		this.artists = artists;
	}
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	@Override
	public String toString() {
		return "track: " + name + " / " + album.getName() + " / " + String.join(", ", artists.stream().map(Artist::getName).collect(Collectors.toList()));
	}
	
}
