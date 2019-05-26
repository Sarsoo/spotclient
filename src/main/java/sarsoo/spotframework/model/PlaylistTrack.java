package sarsoo.spotframework.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlaylistTrack extends Track {
	
	private LocalDateTime addedAt;
	private User addedBy;
	private Boolean isLocal;
	
	public PlaylistTrack(String name, String id, String uri, String url, Album album, LocalDateTime addedAt, User addedBy, Boolean isLocal,ArrayList<Artist> artists) {
		super(name, id, uri, url, album, artists);
		this.setAddedAt(addedAt);
		this.setAddedBy(addedBy);
		this.setIsLocal(isLocal);
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public Boolean getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}

}
