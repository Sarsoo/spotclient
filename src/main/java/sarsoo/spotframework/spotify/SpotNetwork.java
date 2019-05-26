package sarsoo.spotframework.spotify;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import sarsoo.spotframework.model.Album;
import sarsoo.spotframework.model.AlbumType;
import sarsoo.spotframework.model.Artist;
import sarsoo.spotframework.model.Image;
import sarsoo.spotframework.model.Playlist;
import sarsoo.spotframework.model.PlaylistTrack;
import sarsoo.spotframework.model.Track;
import sarsoo.spotframework.model.User;

public class SpotNetwork {

	private String userName;
	private String accessToken;

	public SpotNetwork(String userName, String accessToken) {
		this.userName = userName;
		this.accessToken = accessToken;
	}

	public SpotNetwork(String accessToken) {
		this.accessToken = accessToken;
	}

	public ArrayList<Playlist> getUserPlaylists() {
		List<JSONObject> list = makePagedGetRequest("https://api.spotify.com/v1/me/playlists");

		ArrayList<Playlist> playlists = new ArrayList<>();

		list.stream().forEach(t -> playlists.add(parseSimplifiedPlaylist(t)));

		return playlists;
	}

	public ArrayList<PlaylistTrack> getPlaylistTracks(String playlistId) {
		List<JSONObject> list = makePagedGetRequest(
				String.format("https://api.spotify.com/v1/playlists/%s/tracks", playlistId));

		ArrayList<PlaylistTrack> tracks = new ArrayList<>();

		list.stream().forEach(t -> tracks.add(parsePlaylistTrack(t)));

		return tracks;
	}

	public ArrayList<Album> getLibrary() {
		List<JSONObject> list = makePagedGetRequest("https://api.spotify.com/v1/me/albums");

		ArrayList<Album> albums = new ArrayList<>();

		list.stream().forEach(t -> albums.add(parseSimplifiedAlbum(t.getJSONObject("album"))));

		return albums;
	}

	private ArrayList<JSONObject> makePagedGetRequest(String url) {

		HttpRequest request;
		try {
			request = Unirest.get(url).header("Accept", "application/json");

			request.header("Authorization", "Bearer " + accessToken);

			HttpResponse<JsonNode> response = request.asJson();

			if (response.getStatus() >= 200 && response.getStatus() < 300) {

				JSONObject obj = new JSONObject(response.getBody().toString());

				ArrayList<JSONObject> list = new ArrayList<>();

				for (Object jsonObj : obj.getJSONArray("items")) {
					list.add((JSONObject) jsonObj);
				}

				try {
					String next = obj.getString("next");
					list.addAll(makePagedGetRequest(next));
				} catch (JSONException e) {
					System.out.println("end");
				}

				return list;

			} else {
				JSONObject obj = new JSONObject(response.getBody().toString());

				System.out.println(obj);

//				throw new ApiCallException(method, obj.getInt("error"), obj.getString("message"));
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;

	}

	private JSONObject makeGetRequest(String method, HashMap<String, String> parameters,
			HashMap<String, String> headers) {

		HttpRequest request;
		try {
			request = Unirest.get("https://api.spotify.com/v1/" + method).header("Accept", "application/json");

			request.header("Authorization", accessToken);

			if (headers != null) {
				for (String key : headers.keySet()) {
					request = request.header(key, headers.get(key));
				}
			}

			if (parameters != null) {
				for (String key : parameters.keySet()) {
					request = request.queryString(key, parameters.get(key));
				}
			}

			HttpResponse<JsonNode> response = request.asJson();

			if (response.getStatus() >= 200 && response.getStatus() < 300) {

				return new JSONObject(response.getBody().toString());

			} else {
				JSONObject obj = new JSONObject(response.getBody().toString());

//				throw new ApiCallException(method, obj.getInt("error"), obj.getString("message"));
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return null;

	}

	private Playlist parseSimplifiedPlaylist(JSONObject object) {

		JSONObject ownerObj = object.getJSONObject("owner");

		User owner = new User(ownerObj.getString("display_name"), ownerObj.getString("id"), ownerObj.getString("uri"),
				ownerObj.getString("href"));

		JSONArray imageObj = object.getJSONArray("images");

		ArrayList<Image> images = new ArrayList<>();
		for (Object i : imageObj) {
			JSONObject image = (JSONObject) i;

			System.out.println(image.get("height"));

			int height = 0;
			int width = 0;

			try {
				height = image.getInt("height");
				width = image.getInt("width");
			} catch (JSONException e) {
				System.out.println("null image dimensions");
			}

			images.add(new Image(image.getString("url"), height, width));
		}

		Playlist playlist = new Playlist(object.getString("name"), object.getString("id"), object.getString("uri"),
				object.getString("href"), object.getBoolean("collaborative"), object.getBoolean("public"), owner,
				images);

		return playlist;
	}

	private Artist parseSimplifiedArtist(JSONObject object) {
		return new Artist(object.getString("name"), object.getString("id"), object.getString("uri"),
				object.getString("href"));
	}

	private Album parseSimplifiedAlbum(JSONObject object) {

		AlbumType type = null;

		switch (object.getString("album_type")) {
		case "album":
			type = AlbumType.ALBUM;
			break;
		case "single":
			type = AlbumType.SINGLE;
			break;
		case "compilation":
			type = AlbumType.COMPILATION;
			break;
		}

		ArrayList<Artist> artists = new ArrayList<>();

		JSONArray artistObj = object.getJSONArray("artists");

		for (Object i : artistObj) {
			JSONObject artist = (JSONObject) i;
			artists.add(parseSimplifiedArtist(artist));
		}

		Album album = new Album(object.getString("name"), object.getString("id"), object.getString("uri"),
				object.getString("href"), type, artists);

		try {
			ArrayList<Track> tracks = new ArrayList<>();

			object.getJSONObject("tracks").getJSONArray("items").forEach(t -> tracks.add(parseTrack((JSONObject) t)));

			album.setTracks(tracks);

		} catch (JSONException e) {
			System.out.println("no tracks");
		}

		return album;
	}

	private User parseUser(JSONObject object) {
		return new User(object.getString("id"), object.getString("id"), object.getString("uri"),
				object.getString("href"));
	}

	private PlaylistTrack parsePlaylistTrack(JSONObject object) {

		JSONObject trackObj = object.getJSONObject("track");

		Album album = parseSimplifiedAlbum(trackObj.getJSONObject("album"));

		ArrayList<Artist> artists = new ArrayList<>();

		JSONArray artistObj = trackObj.getJSONArray("artists");

		for (Object i : artistObj) {
			JSONObject artist = (JSONObject) i;
			artists.add(parseSimplifiedArtist(artist));
		}

		PlaylistTrack track = new PlaylistTrack(trackObj.getString("name"), trackObj.getString("id"),
				trackObj.getString("uri"), trackObj.getString("href"), album,
				LocalDateTime.ofInstant(Instant.parse(object.getString("added_at")), ZoneId.systemDefault()),
				parseUser(object.getJSONObject("added_by")), trackObj.getBoolean("is_local"), artists);

		return track;
	}

	private Track parseTrack(JSONObject object) {

		JSONObject trackObj = object.getJSONObject("track");

		Album album = null;

		try {
			album = parseSimplifiedAlbum(trackObj.getJSONObject("album"));
		} catch (JSONException e) {
			System.out.println("no album");

		}
		ArrayList<Artist> artists = new ArrayList<>();

		JSONArray artistObj = trackObj.getJSONArray("artists");

		for (Object i : artistObj) {
			JSONObject artist = (JSONObject) i;
			artists.add(parseSimplifiedArtist(artist));
		}

		Track track = new Track(trackObj.getString("name"), trackObj.getString("id"), trackObj.getString("uri"),
				trackObj.getString("href"), album, artists);

		return track;
	}

}
