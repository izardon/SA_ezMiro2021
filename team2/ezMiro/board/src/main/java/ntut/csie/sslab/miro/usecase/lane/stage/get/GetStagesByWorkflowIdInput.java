package ntut.csie.sslab.miro.usecase.lane.stage.get;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetStagesByWorkflowIdInput extends Input {
    public String getWorkflowId();

    public void setWorkflowId(String workflowId);
}
