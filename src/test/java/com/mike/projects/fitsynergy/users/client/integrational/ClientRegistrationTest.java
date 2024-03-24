package com.mike.projects.fitsynergy.users.client.integrational;

import com.mike.projects.fitsynergy.users.controller.UserController;
import com.mike.projects.fitsynergy.users.dto.UserRequestDto;
import com.mike.projects.fitsynergy.users.mapper.UserMapper;
import com.mike.projects.fitsynergy.users.model.User;
import com.mike.projects.fitsynergy.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
public class ClientRegistrationTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "adam@gmail.com", password = "password", roles = {"CLIENT"})
    public void loginViewPage_shouldReturnViewPage() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/log-in"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

    }

    @Test
    @WithMockUser(username = "adam@gmail.com", password = "password", roles = {"CLIENT"})
    public void registrationViewPage_shouldReturnRegistrationPage() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));

    }

    @Test
    public void registrationLogicWithValidData_shouldPass() throws Exception{

        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("adam@gmail.com");
        requestDto.setPassword("Ragnervi1");
        requestDto.setFirstName("Adam");
        requestDto.setLastName("Jeskii");
        requestDto.setAge(20);
        requestDto.setGender("Male");

        User user = new User();
        UserMapper.mapUserRequestDto(requestDto, user);

        when(userService.registerUser(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/sign-up")
                        .flashAttr("requestDto", requestDto)
                        .with(SecurityMockMvcRequestPostProcessors.user("adam@gmail.com").password("password").roles("CLIENT"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/users/login"));

    }
}
