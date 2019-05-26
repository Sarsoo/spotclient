package sarsoo.spotframework.model;

public class User extends SpotifyObject {
	
	public User(String name, String id, String uri, String url) {
		
		this.name = name;
		this.id = id;
		this.uri = uri;
		this.url = url;
		
	}
	
	@Override
	public String toString() {
		return "user: " + name;
	}

}
