package sarsoo.spotframework.spotify;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import sarsoo.spotframework.model.Playlist;
import sarsoo.spotframework.model.PlaylistTrack;

class SpotNetworkTest {

	@Test
	void test() {
		
		Scanner scan = new Scanner(System.in);
		
		SpotNetwork net = new SpotNetwork(scan.nextLine());
		ArrayList<Playlist> playlists = net.getUserPlaylists();
		
		ArrayList<PlaylistTrack> tracks = net.getPlaylistTracks(playlists.get(0).getId());
		
		tracks.stream().forEach(t -> System.out.println(t.getAddedAt()));
		
		System.out.println(tracks.size());
//		.stream().forEach(System.out::println);
		
		scan.close();
		assertTrue(true);
	}

}
