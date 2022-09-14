package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskRepo taskRepo;

    @Test
    public void shouldFindAll(){
        Task task = new Task();
        task.setTask("description");
        task.setDueDate(LocalDate.now());
        Mockito.when(this.taskRepo.findAll()).thenReturn(Collections.singletonList(task));

        List<Task> all = this.taskController.findAll();

        assertThat(all.contains(task)).isTrue();
        verify(this.taskRepo, times(1)).findAll();
    }

    @Test
    public void shouldNotSaveWithNullDescription() {
        Task task = new Task();
        task.setTask(null);
        assertThatThrownBy(() -> this.taskController.save(task)).isInstanceOf(ValidationException.class)
            .hasMessage("Fill the task description");
    }

    @Test
    public void shouldNotSaveWithEmptyDescription() {
        Task task = new Task();
        task.setTask("");
        assertThatThrownBy(() -> this.taskController.save(task)).isInstanceOf(ValidationException.class)
            .hasMessage("Fill the task description");
    }

    @Test
    public void shouldNotSaveWithoutDate() {
        Task task = new Task();
        task.setTask("description");
        assertThatThrownBy(() -> this.taskController.save(task)).isInstanceOf(ValidationException.class)
            .hasMessage("Fill the due date");
    }

    @Test
    public void shouldNotSaveWithoutValidDate() {
        Task task = new Task();
        task.setTask("description");
        task.setDueDate(LocalDate.now().minusDays(1L));
        assertThatThrownBy(() -> this.taskController.save(task)).isInstanceOf(ValidationException.class)
            .hasMessage("Due date must not be in past");
    }

    @Test
    public void shouldSaveProperly() throws ValidationException {
        Task task = new Task();
        task.setTask("description");
        task.setDueDate(LocalDate.now());

        this.taskController.save(task);

        verify(this.taskRepo).save(task);
    }

}
