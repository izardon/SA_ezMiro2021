package ntut.csie.sslab.miro.entity.model.card.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class TagUnassigned extends DomainEvent {
    private final String cardId;
    private final String tagId;
    private final String boardId;
    private final String userId;
    private final String username;

    public TagUnassigned(String cardId, String tagId, String boardId, String userId, String username) {
        super(DateProvider.now());
        this.cardId = cardId;
        this.tagId = tagId;
        this.boardId = boardId;
        this.userId = userId;
        this.username = username;
    }

    public String cardId() {
        return cardId;
    }

    public String tagId() {
        return tagId;
    }

    public String boardId() {
        return boardId;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }
}
