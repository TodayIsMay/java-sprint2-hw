package test.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    Epic epic;
    Subtask subtask;
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    public void create() {
        epic = new Epic("feed the cat", "description", 1, Status.NEW);
        subtask = new Subtask(1, "find the cat", 1, Status.NEW, Duration.ofHours(1),
                LocalDateTime.now());
        subtask1 = new Subtask(1, "open food pack", 1, Status.NEW, Duration.ofHours(1),
                LocalDateTime.now());
        subtask2 = new Subtask(1, "put food in a bowl", 1, Status.NEW, Duration.ofHours(1),
                LocalDateTime.now());
    }

    @Test
    public void shouldBeNewWhenNoSubtasks() {
        String expected = Status.NEW.toString();
        String actual = epic.getStatus().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldBeNewWhenAllSubtasksAreNew() {
        epic.addSub(subtask);
        epic.addSub(subtask1);
        epic.addSub(subtask2);
        assertEquals(Status.NEW.toString(), epic.getStatus().toString());
    }

    @Test
    public void shouldBeDoneWhenAllSubtasksAreDone() {
        subtask.setStatus(Status.DONE);
        epic.addSub(subtask);
        subtask1.setStatus(Status.DONE);
        epic.addSub(subtask1);
        subtask2.setStatus(Status.DONE);
        epic.addSub(subtask2);
        assertEquals(Status.DONE.toString(), epic.getStatus().toString());
    }

    @Test
    public void shouldBeInProgressWhenSubtasksAreNewAndDone() {
        subtask.setStatus(Status.DONE);
        epic.addSub(subtask);
        epic.addSub(subtask1);
        epic.addSub(subtask2);
        assertEquals(Status.IN_PROGRESS.toString(), epic.getStatus().toString());
    }

    @Test
    public void shouldBeInProgressWhenAllSubtasksAreInProgress() {
        subtask.setStatus(Status.IN_PROGRESS);
        epic.addSub(subtask);
        subtask1.setStatus(Status.IN_PROGRESS);
        epic.addSub(subtask1);
        subtask2.setStatus(Status.IN_PROGRESS);
        epic.addSub(subtask2);
        assertEquals(Status.IN_PROGRESS.toString(), epic.getStatus().toString());
    }

}