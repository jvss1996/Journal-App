package com.shekhawat.journalApp.service;

import com.shekhawat.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        assertNotNull(userRepository.findByUsername("Ram"));
    }

    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "3,3,9"
//    })
//    OR
    @ValueSource(ints = {
            1,1,2,
            3,3,9
    })
    public void parameterizedTest(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }
}
