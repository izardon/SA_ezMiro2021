package ntut.csie.sslab.miro.entity.model.card.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CategoryUnassigned extends DomainEvent {
	
	private final String cardId;
	private final String categoryId;
	private final String userId;
	private final String username;
	private final String boardId;

	public CategoryUnassigned(String cardId, String categoryId, String userId, String username, String boardId) {
		super(DateProvider.now());
		this.cardId = cardId;
		this.categoryId = categoryId;
		this.userId = userId;
		this.username = username;
		this.boardId = boardId;
	}
	

	public String cardId() {
		return cardId;
	}
	
	public String categoryId() {
		return categoryId;
	}

	public String userId() {
		return userId;
	}

	public String username() {
		return username;
	}

	public String boardId() {
		return boardId;
	}
}
