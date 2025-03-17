package ru.nextcloudnext.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.nextcloudnext.dto.NewUserDto;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private NewUserDto user;
    private NewUserDto user2;

    @BeforeEach
    void setUp() {
        user = new NewUserDto();
        user.setId(1L);
        user.setFirstname("Иван");
        user.setLastname("Иванов");
        user.setPatronymic("Иванович");

        user2 = new NewUserDto();
        user2.setId(2L);
        user2.setFirstname("Петр");
        user2.setLastname("Петров");
        user2.setPatronymic("Петрович");
    }

    @Test
    public void addNormalUser() throws Exception {
        postUser(user).andExpect(status().isCreated());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        postUser(user2).andExpect(status().isCreated());
        getUserById(user.getId()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId()), Long.class));
        getUserById(user2.getId()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user2.getId()), Long.class));
    }

    @Test
    public void getUserByIdUserNotFoundTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        getUserById(99L).andExpect(status().isNotFound());
    }

    @Test
    public void getEmptyUsers() throws Exception {
        getUsers()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getUsersTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        postUser(user2).andExpect(status().isCreated());

        getUsers()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void updateUserFieldsTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        user.setFirstname("Илья");
        patchUser(user.getId(), user).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("Илья"));
        user.setLastname("Ильин");
        patchUser(user.getId(), user).andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname").value("Ильин"));
        user.setPatronymic("Ильинович");
        patchUser(user.getId(), user).andExpect(status().isOk())
                .andExpect(jsonPath("$.patronymic").value("Ильинович"));
    }

    @Test
    public void updateAbsentUserTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        patchUser(99L, user).andExpect(status().isNotFound());
    }

    @Test
    public void deleteUserTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        getUserById(user.getId()).andExpect(status().isOk());
        deleteUser(user.getId()).andExpect(status().isNoContent());
        getUserById(user.getId()).andExpect(status().isNotFound());
    }

    @Test
    public void deleteNotFoundUserTest() throws Exception {
        postUser(user).andExpect(status().isCreated());
        getUserById(user.getId()).andExpect(status().isOk());
        deleteUser(99L).andExpect(status().isNotFound());
    }

    private ResultActions getUserById(Long userId) throws Exception {
        return mockMvc.perform(
                get("/users/" + userId)
        );
    }

    private ResultActions getUsers() throws Exception {
        return mockMvc.perform(
                get("/users")
        );
    }

    private ResultActions postUser(NewUserDto userDto) throws Exception {
        return mockMvc.perform(
                post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private ResultActions patchUser(Long userId, NewUserDto userDto) throws Exception {
        return mockMvc.perform(
                patch("/users/" + userId)
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private ResultActions deleteUser(Long userId) throws Exception {
        return mockMvc.perform(
                delete("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }
}