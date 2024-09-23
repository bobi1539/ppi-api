package com.grasia.prima.ppi.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class PpiApiApplicationTests {

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("PPI_SHOW_SQL", () -> "false");
    }

    @Test
    void testMainMethod() {
        try (var mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            // Call the main method
            PpiApiApplication.main(new String[]{});

            // Verify that SpringApplication.run was called once with PpiApiApplication.class
            mockedSpringApplication.verify(
                    () -> SpringApplication.run(
                            PpiApiApplication.class, new String[]{}
                    ), Mockito.times(1)
            );
        }
    }
}
