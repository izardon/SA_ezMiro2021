package ntut.csie.islab.miro.usecase.figure.stickyNote;

import ntut.csie.islab.miro.entity.figure.stickyNote.StickyNote;
import ntut.csie.islab.miro.entity.figure.stickyNote.StickyNoteRepository;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.*;
import org.apache.catalina.Store;

public class CreateStickyNoteUseCase {

    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;
    public CreateStickyNoteInput newInput() {
        return new CreateStickyNoteInput();
    }

    public void execute(CreateStickyNoteInput input, ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput output) {
        StickyNote stickyNote = new StickyNote(input.getBoardId(), input.getPosition(), input.getContent(), input.getStyle());


//        stickyNoteRepository.save(stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
    }
}
