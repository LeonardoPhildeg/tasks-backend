package br.ce.wcaquino.taskbackend.utils;

import org.junit.Test;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.*;

public class DateUtilsTest {

    @Test
    public void shouldReturnTrueToFutureDate() {
        LocalDate date = LocalDate.of(2030, 1, 1);
        boolean futureDate = DateUtils.isEqualOrFutureDate(date);
        assertThat(futureDate).isTrue();
    }

    @Test
    public void shouldReturnTrueToActualDate() {
        LocalDate date = LocalDate.now();
        boolean futureDate = DateUtils.isEqualOrFutureDate(date);
        assertThat(futureDate).isTrue();
    }

    @Test
    public void shouldReturnFalseToPastDate() {
        LocalDate date = LocalDate.of(2010, 1, 1);
        boolean futureDate = DateUtils.isEqualOrFutureDate(date);
        assertThat(futureDate).isFalse();
    }

}
