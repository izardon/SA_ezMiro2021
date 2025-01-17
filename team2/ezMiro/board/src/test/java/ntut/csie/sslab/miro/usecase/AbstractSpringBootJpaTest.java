package ntut.csie.sslab.miro.usecase;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.card.CardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.card.CardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.workflow.WorkflowRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.workflow.WorkflowRepositoryPeer;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.card.Card;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.CardRepository;
import ntut.csie.sslab.miro.usecase.card.create.CreateCardInput;
import ntut.csie.sslab.miro.usecase.card.create.CreateCardUseCase;
import ntut.csie.sslab.miro.usecase.card.create.CreateCardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.delete.DeleteCardInput;
import ntut.csie.sslab.miro.usecase.card.delete.DeleteCardUseCase;
import ntut.csie.sslab.miro.usecase.card.delete.DeleteCardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.deadline.ChangeCardDeadlineInput;
import ntut.csie.sslab.miro.usecase.card.edit.deadline.ChangeCardDeadlineUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.deadline.ChangeCardDeadlineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.description.ChangeCardDescriptionInput;
import ntut.csie.sslab.miro.usecase.card.edit.description.ChangeCardDescriptionUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.description.ChangeCardDescriptionUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.estimate.ChangeCardEstimateInput;
import ntut.csie.sslab.miro.usecase.card.edit.estimate.ChangeCardEstimateUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.estimate.ChangeCardEstimateUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.note.ChangeCardNoteUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.note.ChangeCardNoteUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.edit.note.ChangeCardNoteInput;
import ntut.csie.sslab.miro.usecase.card.edit.type.ChangeCardTypeInput;
import ntut.csie.sslab.miro.usecase.card.edit.type.ChangeCardTypeUseCase;
import ntut.csie.sslab.miro.usecase.card.edit.type.ChangeCardTypeUseCaseImpl;
import ntut.csie.sslab.miro.usecase.card.get.GetCardUseCase;
import ntut.csie.sslab.miro.usecase.card.get.GetCardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.rename.RenameLaneInput;
import ntut.csie.sslab.miro.usecase.lane.rename.RenameLaneUseCase;
import ntut.csie.sslab.miro.usecase.lane.rename.RenameLaneUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.get.GetLaneUseCase;
import ntut.csie.sslab.miro.usecase.lane.get.GetLaneUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.stage.create.CreateStageInput;
import ntut.csie.sslab.miro.usecase.lane.stage.create.CreateStageUseCase;
import ntut.csie.sslab.miro.usecase.lane.stage.create.CreateStageUseCaseImpl;
import ntut.csie.sslab.miro.usecase.lane.swimLane.create.CreateSwimLaneInput;
import ntut.csie.sslab.miro.usecase.lane.swimLane.create.CreateSwimLaneUseCase;
import ntut.csie.sslab.miro.usecase.lane.swimLane.create.CreateSwimLaneUseCaseImpl;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowRepository;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowInput;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowUseCase;
import ntut.csie.sslab.miro.usecase.workflow.create.CreateWorkflowUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= JpaApplicationTest.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractSpringBootJpaTest {

    public static final long WAITING = 1000;

    public BoardRepository boardRepository;
    public WorkflowRepository workflowRepository;
    public CardRepository cardRepository;
    public DomainEventBus domainEventBus;

    public NotifyBoard notifyBoard;
    public NotifyWorkflow notifyWorkflow;

    public String teamId;
    public String teamName;
    public String projectId;
    public String projectName;
    public String boardId;
    public String workflowId;
    public String rootStageId;
    public String tagId;
    public String userId;
    public String username;
    public String boardName;


    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private WorkflowRepositoryPeer workflowRepositoryPeer;

    @Autowired
    private CardRepositoryPeer cardRepositoryPeer;



    @BeforeEach
    public void setUp() {

        boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        workflowRepository = new WorkflowRepositoryImpl(workflowRepositoryPeer);
        cardRepository = new CardRepositoryImpl(cardRepositoryPeer);
        domainEventBus = new DomainEventBus();

        teamId = UUID.randomUUID().toString();
        teamName = "ntut team";
        projectId = UUID.randomUUID().toString();
        projectName = "ezkanban project";
        boardId = UUID.randomUUID().toString();
        workflowId = UUID.randomUUID().toString();
        tagId = UUID.randomUUID().toString();
        rootStageId = UUID.randomUUID().toString();
        userId = UUID.randomUUID().toString();
        username = "Teddy";

        notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        notifyWorkflow = new NotifyWorkflow(cardRepository, workflowRepository, domainEventBus);
    }


    public CreateBoardUseCase newCreateBoardUseCase (){
        return new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
    }

    public GetBoardContentUseCase newGetBoardContentUseCase() {
        return new GetBoardContentUseCaseImpl(boardRepository, workflowRepository, cardRepository, domainEventBus);
    }

    public CreateWorkflowUseCase newCreateWorkflowUseCase() {
        return new CreateWorkflowUseCaseImpl(workflowRepository, domainEventBus);
    }

    public CreateStageUseCase newCreateStageUseCase() {
        return new CreateStageUseCaseImpl(workflowRepository, domainEventBus);
    }

    public RenameLaneUseCase newRenameLaneUseCase() {
        return new RenameLaneUseCaseImpl(workflowRepository, domainEventBus);
    }

    public CreateSwimLaneUseCase newCreateSwimLaneUseCase() {
        return new CreateSwimLaneUseCaseImpl(workflowRepository, domainEventBus);
    }

    public GetLaneUseCase newGetLaneByIdUseCase() {
        return new GetLaneUseCaseImpl(workflowRepository);
    }

    public CreateCardUseCase newCreateCardUseCase() {
        return new CreateCardUseCaseImpl(cardRepository, domainEventBus);
    }

    public ChangeCardDeadlineUseCase newChangeCardDeadlineUseCase() {
        return new ChangeCardDeadlineUseCaseImpl(cardRepository, domainEventBus);
    }

    public DeleteCardUseCase newDeleteCardUseCase() {
        return new DeleteCardUseCaseImpl(cardRepository, domainEventBus);
    }

    public ChangeCardNoteUseCase newChangeCardNotesUseCase() {
        return new ChangeCardNoteUseCaseImpl(cardRepository, domainEventBus);
    }


    public ChangeCardTypeUseCase newChangeCardTypeUseCase() {
        return new ChangeCardTypeUseCaseImpl(cardRepository, domainEventBus);
    }

    public ChangeCardEstimateUseCase newChangeCardEstimateUseCase() {
        return new ChangeCardEstimateUseCaseImpl(cardRepository, domainEventBus);
    }

    public ChangeCardDescriptionUseCase newChangeCardDescriptionUseCase() {
        return new ChangeCardDescriptionUseCaseImpl(cardRepository, domainEventBus);
    }

    public GetCardUseCase newGetCardByIdUseCase() {
        return new GetCardUseCaseImpl(cardRepository);
    }


    public String createBoard(String teamId, String name, String userId){
        CreateBoardUseCase createBoardUseCase = newCreateBoardUseCase();

        CreateBoardInput input = createBoardUseCase.newInput();
        input.setTeamId(teamId);
        input.setName(name);
        input.setUserId(userId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createBoardUseCase.execute(input, output);

        return output.getId();
    }

    public String createWorkflow(String boardId, String workflowName, String userId, String username) {
        CreateWorkflowUseCase createWorkflowUseCase = newCreateWorkflowUseCase();

        CreateWorkflowInput input = createWorkflowUseCase.newInput();
        input.setWorkflowName(workflowName);
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setUsername(username);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        createWorkflowUseCase.execute(input, output);

        return output.getId();
    }

    public String createStage(
            String boardId,
            String userId,
            String username,
            String workflowId,
            String parentId,
            String name,
            int wipLimit,
            String laneType) {

        CreateStageUseCase createStageUseCase = newCreateStageUseCase();

        CreateStageInput input = createStageUseCase.newInput();
        input.setBoardId(boardId);
        input.setWorkflowId(workflowId);
        input.setParentId(parentId);
        input.setStageName(name);
        input.setWipLimit(wipLimit);
        input.setLaneType(laneType);
        input.setUserId(userId);
        input.setUsername(username);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        createStageUseCase.execute(input, output);

        return output.getId();
    }

    public String createSwimLane(
            String workflowId,
            String parentId,
            String name,
            int wipLimit,
            String laneType,
            String userId,
            String username,
            String boardId) {

        CreateSwimLaneUseCase createSwimLaneUseCase = newCreateSwimLaneUseCase();

        CreateSwimLaneInput input = createSwimLaneUseCase.newInput();
        input.setWorkflowId(workflowId);
        input.setParentId(parentId);
        input.setName(name);
        input.setWipLimit(wipLimit);
        input.setLaneType(laneType);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        createSwimLaneUseCase.execute(input, output);

        return output.getId();
    }

    public String renameLane(String workflowId, String laneId, String newName, String userId, String username){

        RenameLaneUseCase renameLaneUseCase = newRenameLaneUseCase();
        RenameLaneInput input = renameLaneUseCase.newInput();
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setNewName(newName);
        input.setUserId(userId);
        input.setUsername(username);
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        renameLaneUseCase.execute(input, output);

        return  output.getId();
    }

    public String createCard(String userId,
                             String workflowId,
                             String laneId,
                             String description,
                             String estimate,
                             String notes,
                             String deadline,
                             String type,
                             String username,
                             String boardId){

        CreateCardUseCase createCardUseCase = newCreateCardUseCase();
        CreateCardInput createCardInput = createCardUseCase.newInput();
        createCardInput.setWorkflowId(workflowId);
        createCardInput.setLaneId(laneId);
        createCardInput.setUserId(userId);
        createCardInput.setDescription(description);
        createCardInput.setEstimate(estimate);
        createCardInput.setNote(notes);
        createCardInput.setDeadline(deadline);
        createCardInput.setType(type);
        createCardInput.setUsername(username);
        createCardInput.setBoardId(boardId);

        CqrsCommandPresenter addCardOutput = CqrsCommandPresenter.newInstance();

        createCardUseCase.execute(createCardInput, addCardOutput);

        Card card = cardRepository.findById(addCardOutput.getId()).get();


        assertNotNull(card);
        assertEquals(userId, card.getUserId());
        assertEquals(laneId, card.getLaneId());
        assertEquals(description, card.getDescription());
        assertEquals(estimate, card.getEstimate());
        assertEquals(notes, card.getNotes());
        assertEquals(deadline, card.getDeadline());
        assertEquals(type, card.getType().toString());

        return addCardOutput.getId();
    }

    public void deleteCard(String workflowId, String laneId, String cardId, String userId, String username, String boardId) {
        DeleteCardUseCase deleteCardUseCase = newDeleteCardUseCase();

        DeleteCardInput input = deleteCardUseCase.newInput();
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);
        input.setCardId(cardId);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        deleteCardUseCase.execute(input, output);
    }

    public void changeCardDescription(String cardId, String newDescription, String boardId, String userId, String username){
        ChangeCardDescriptionUseCase changeCardDescriptionUseCase = newChangeCardDescriptionUseCase();

        ChangeCardDescriptionInput input = changeCardDescriptionUseCase.newInput();
        input.setCardId(cardId);
        input.setNewDescription(newDescription);
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setUsername(username);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardDescriptionUseCase.execute(input, output);
    }

    public void changeCardEstimate(String cardId, String newEstimate, String boardId, String userId, String username){
        ChangeCardEstimateUseCase changeCardEstimateUseCase = newChangeCardEstimateUseCase();

        ChangeCardEstimateInput input = changeCardEstimateUseCase.newInput();
        input.setCardId(cardId);
        input.setNewEstimate(newEstimate);
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setUsername(username);


        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardEstimateUseCase.execute(input, output);
    }


    public void changeCardDeadline(String cardId, String newDeadline, String boardId, String userId, String username) {
        ChangeCardDeadlineUseCase changeCardDeadlineUseCase = newChangeCardDeadlineUseCase();

        ChangeCardDeadlineInput input = changeCardDeadlineUseCase.newInput();
        input.setCardId(cardId);
        input.setNewDeadline(newDeadline);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardDeadlineUseCase.execute(input, output);
    }

    public void changeCardNotes(String cardId, String newNotes, String boardId, String userId, String username) {
        ChangeCardNoteUseCase changeCardNoteUseCase = newChangeCardNotesUseCase();

        ChangeCardNoteInput input = changeCardNoteUseCase.newInput();
        input.setCardId(cardId);
        input.setNewNotes(newNotes);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardNoteUseCase.execute(input, output);
    }

    public void changeCardType(String cardId, String newType, String boardId, String userId, String username) {

        ChangeCardTypeUseCase changeCardTypeUseCase = newChangeCardTypeUseCase();

        ChangeCardTypeInput input = changeCardTypeUseCase.newInput();
        input.setCardId(cardId);
        input.setNewType(newType);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardTypeUseCase.execute(input, output);
    }

    public BoardRepository getBoardRepository(){
        return boardRepository;
    }
}
