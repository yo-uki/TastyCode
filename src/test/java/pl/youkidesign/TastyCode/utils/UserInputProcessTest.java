package pl.youkidesign.TastyCode.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserInputProcessTest {

    @InjectMocks
    @Spy
    private UserInputProcess userInputProcess;


    @Test
    public void testIfOrderWithYes() {
        String input = "y\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        userInputProcess.scanner = new Scanner(inputStream);

        boolean result = userInputProcess.ifOrder();

        assertTrue(result);
    }

    @Test
    public void testIfOrderWithNo() {
        String input = "n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        userInputProcess.scanner = new Scanner(inputStream);

        boolean result = userInputProcess.ifOrder();

        assertFalse(result);
    }

}