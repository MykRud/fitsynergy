package com.mike.projects.fitsynergy.users.client.service;

import com.mike.projects.fitsynergy.program.dto.ExerciseRequestDto;
import com.mike.projects.fitsynergy.program.dto.ProgramRequestDto;
import com.mike.projects.fitsynergy.program.model.*;
import com.mike.projects.fitsynergy.program.repo.ExerciseContextRepo;
import com.mike.projects.fitsynergy.program.repo.ExerciseRepo;
import com.mike.projects.fitsynergy.program.repo.OccupationRepo;
import com.mike.projects.fitsynergy.program.repo.ProgramRepo;
import com.mike.projects.fitsynergy.program.service.ConstructorService;
import com.mike.projects.fitsynergy.program.service.ExerciseService;
import com.mike.projects.fitsynergy.program.service.OccupationService;
import com.mike.projects.fitsynergy.program.service.ProgramService;
import com.mike.projects.fitsynergy.security.model.SystemUserDetails;
import com.mike.projects.fitsynergy.security.model.SystemUserRole;
import com.mike.projects.fitsynergy.security.service.UserCredential;
import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.exception.UserAlreadyExistsException;
import com.mike.projects.fitsynergy.users.model.Client;
import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.repo.UserRepo;
import com.mike.projects.fitsynergy.users.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
public class ClientRegistrationLogicTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // SQL Scripts
    @Value("${sql.script.create.user}")
    private String createUser;

    @Value("${sql.script.delete.client}")
    private String deleteClientScript;
    @Value("${sql.script.delete.trainer}")
    private String deleteTrainerScript;
    @Value("${sql.script.delete.user}")
    private String deleteUserScript;
    @Value("${sql.script.delete.user.details}")
    private String deleteUserDetailsScript;

    @Value("${sql.script.reload.user.sequence}")
    private String reloadUserSequenceScript;
    @Value("${sql.script.reload.user.details.sequence}")
    private String reloadUserDetailsSequenceScript;

    @Test
    public void registrationLogicWithValidData_shouldPass(){

        // Вхідні дані
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Jameson");
        userRequestDto.setAge(20);
        userRequestDto.setGender("male");
        userRequestDto.setEmail("alex@gmail.com");
        userRequestDto.setPassword("password");

        // Очікувані результати
        Client expectedClient = new Client();
        expectedClient.setId(1);
        expectedClient.setFirstName("Alex");
        expectedClient.setLastName("Jameson");
        expectedClient.setAge(20);
        expectedClient.setGender("male");
        expectedClient.setEmail("alex@gmail.com");
        expectedClient.setPassword("password");

        SystemUserDetails userDetails = new SystemUserDetails();
        userDetails.setRole(SystemUserRole.CLIENT);
        userDetails.setIsLocked(false);
        userDetails.setIsExpired(false);
        userDetails.setIsCredentialsExpired(false);
        userDetails.setIsEnabled(true);

        expectedClient.setUserDetails(userDetails);

        // Тестування
        User actualUser = userService.registerUser(userRequestDto);
        assertEquals(expectedClient.getId(), actualUser.getId());
        assertEquals(expectedClient.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedClient.getLastName(), actualUser.getLastName());
        assertEquals(expectedClient.getEmail(), actualUser.getEmail());
        assertTrue(passwordEncoder.matches(expectedClient.getPassword(), actualUser.getPassword()));
        assertEquals(expectedClient.getAge(), actualUser.getAge());
        assertEquals(expectedClient.getGender(), actualUser.getGender());
        assertEquals(expectedClient.getUserDetails().getRole(), actualUser.getUserDetails().getRole());
        assertEquals(expectedClient.getUserDetails().getIsEnabled(), actualUser.getUserDetails().getIsEnabled());
        assertEquals(expectedClient.getUserDetails().getIsExpired(), actualUser.getUserDetails().getIsExpired());
        assertEquals(expectedClient.getUserDetails().getIsLocked(), actualUser.getUserDetails().getIsLocked());
        assertEquals(expectedClient.getUserDetails().getIsCredentialsExpired(), actualUser.getUserDetails().getIsCredentialsExpired());
    }

    @Test
    public void initProgramConstruct_ErrorOccurs_emailIsAlreadyPresent(){

        // Вхідні дані
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Alex");
        userRequestDto.setLastName("Jameson");
        userRequestDto.setAge(20);
        userRequestDto.setGender("male");
        userRequestDto.setEmail("alex@gmail.com");
        userRequestDto.setPassword("password");

        // Підготовка
        jdbcTemplate.update(createUser);

        // Тестування
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(userRequestDto));

    }

    @AfterEach
    public void setupAfterTransaction(){
        jdbcTemplate.update(deleteClientScript);
        jdbcTemplate.update(deleteTrainerScript);
        jdbcTemplate.update(deleteUserScript);
        jdbcTemplate.update(deleteUserDetailsScript);

        jdbcTemplate.update(reloadUserDetailsSequenceScript);
        jdbcTemplate.update(reloadUserSequenceScript);
    }

}
