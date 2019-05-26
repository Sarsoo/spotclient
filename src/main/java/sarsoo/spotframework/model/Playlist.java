package sarsoo.spotframework.model;

import java.util.ArrayList;

public class Playlist extends SpotifyObject {
	
	private Boolean collaborative;
	private Boolean isPublic;
	
	private String description;

	private User owner;
	
	private ArrayList<Image> images;
	
	private ArrayList<PlaylistTrack> tracks;
	
	public Playlist(String name, String id, String uri, String url, Boolean collaborative, Boolean isPublic, User owner, ArrayList<Image> images) {
		this.name = name;
		this.id = id;
		this.uri = uri;
		this.url = url;
		this.collaborative = collaborative;
		this.isPublic = isPublic;
		this.owner = owner;
		this.images = images;
	}

	public Boolean getCollaborative() {
		return collaborative;
	}

	public void setCollaborative(Boolean collaborative) {
		this.collaborative = collaborative;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

	public ArrayList<PlaylistTrack> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<PlaylistTrack> tracks) {
		this.tracks = tracks;
	}
	
	@Override
	public String toString() {
		return "playlist: " + name + " / " + owner.getName();
	}
	
}
