package ntut.csie.sslab.miro.entity.model.workflow.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class WorkflowCreated extends DomainEvent {

    private final String workflowId;
    private final String workflowName;
    private final String userId;
    private final String username;
    private final String boardId;

    public WorkflowCreated(String workflowId, String workflowName, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
    }


    public String workflowId() {
        return workflowId;
    }

    public String workflowName() {
        return workflowName;
    }

    public String userId() { return userId; }

    public String username() { return username; }

    public String boardId() {
        return boardId;
    }
}
