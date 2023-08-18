package com.example.rental.controller;

import com.example.rental.dto.request.RequestAppUserDto;
import com.example.rental.dto.response.ResponseAppUserDto;
import com.example.rental.dto.response.paginated.PaginatedResponseAppUserDto;
import com.example.rental.security.model.RoleName;
import com.example.rental.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AppUserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserController appUserController;

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(appUserController)
                .build();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<ResponseAppUserDto> response = new ArrayList<>();
        response.add(getUserOne());
        response.add(getUserTwo());

        when(appUserService.getAllUsers()).thenReturn(response);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].firstName", is("First Name1")))
                .andExpect(jsonPath("$.data[0].lastName", is("Last Name1")))
                .andExpect(jsonPath("$.data[0].email", is("email1@gmail.com")))
                .andExpect(jsonPath("$.data[0].phone", is("0743999999")))
                .andExpect(jsonPath("$.data[0].username", is("username1")))
                .andExpect(jsonPath("$.data[0].role", is("ADMIN")))
                .andExpect(jsonPath("$.data[1].firstName", is("First Name2")))
                .andExpect(jsonPath("$.data[1].lastName", is("Last Name2")))
                .andExpect(jsonPath("$.data[1].email", is("email2@gmail.com")))
                .andExpect(jsonPath("$.data[1].phone", is("0743111111")))
                .andExpect(jsonPath("$.data[1].username", is("username2")))
                .andExpect(jsonPath("$.data[1].role", is("USER")))
                .andExpect(jsonPath("$.message", is("Users downloaded successfully.")));

        verify(appUserService).getAllUsers();
        verifyNoMoreInteractions(appUserService);
    }

    @Test
    public void findUserByUsernameTest() throws Exception {
        var response = getUserOne();

        when(appUserService.findUserByUsername(anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/{username}", "username1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName", is("First Name1")))
                .andExpect(jsonPath("$.data.lastName", is("Last Name1")))
                .andExpect(jsonPath("$.data.email", is("email1@gmail.com")))
                .andExpect(jsonPath("$.data.phone", is("0743999999")))
                .andExpect(jsonPath("$.data.username", is("username1")))
                .andExpect(jsonPath("$.data.role", is("ADMIN")))
                .andExpect(jsonPath("$.message", is("User downloaded successfully.")));

        verify(appUserService).findUserByUsername(anyString());
        verifyNoMoreInteractions(appUserService);
    }

    @Test
    public void findUserById() throws Exception {
        var response = getUserOne();

        when(appUserService.getUserById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/id/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName", is("First Name1")))
                .andExpect(jsonPath("$.data.lastName", is("Last Name1")))
                .andExpect(jsonPath("$.data.email", is("email1@gmail.com")))
                .andExpect(jsonPath("$.data.phone", is("0743999999")))
                .andExpect(jsonPath("$.data.username", is("username1")))
                .andExpect(jsonPath("$.data.role", is("ADMIN")))
                .andExpect(jsonPath("$.message", is("User downloaded successfully.")));

        verify(appUserService).getUserById(anyLong());
        verifyNoMoreInteractions(appUserService);
    }

    @Test
    public void getAppUsersPaginated() throws Exception {
        List<ResponseAppUserDto> users = new ArrayList<>();
        users.add(getUserOne());
        users.add(getUserTwo());

        var response = PaginatedResponseAppUserDto.builder()
                .users(users)
                .numberOfPages(1)
                .numberOfItems(2L)
                .build();

        when(appUserService.getAppUsersPaginated(anyInt(), anyInt(), anyString())).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/paginated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.users[0].firstName", is("First Name1")))
                .andExpect(jsonPath("$.data.users[0].lastName", is("Last Name1")))
                .andExpect(jsonPath("$.data.users[0].email", is("email1@gmail.com")))
                .andExpect(jsonPath("$.data.users[0].phone", is("0743999999")))
                .andExpect(jsonPath("$.data.users[0].username", is("username1")))
                .andExpect(jsonPath("$.data.users[0].role", is("ADMIN")))
                .andExpect(jsonPath("$.data.numberOfItems", is(2)))
                .andExpect(jsonPath("$.data.numberOfPages", is(1)))
                .andExpect(jsonPath("$.message", is("Users downloaded successfully.")));

        verify(appUserService).getAppUsersPaginated(anyInt(), anyInt(), anyString());
        verifyNoMoreInteractions(appUserService);
    }

    @Test
    public void updateAppUserTest() throws Exception {
        var request = RequestAppUserDto.builder()
                .firstName("First Name1 Updated")
                .lastName("Last Name1 Updated")
                .email("email1updated@gmail.com")
                .phone("0743999999")
                .username("username1updated")
                .role(RoleName.USER.name())
                .build();

        var response = ResponseAppUserDto.builder()
                .firstName("First Name1 Updated")
                .lastName("Last Name1 Updated")
                .email("email1updated@gmail.com")
                .phone("0743999999")
                .username("username1updated")
                .role(RoleName.USER.name())
                .build();

        when(appUserService.updateAppUser(anyLong(), eq(request))).thenReturn(response);

        mockMvc.perform(put("/api/v1/users/{appUserId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName", is("First Name1 Updated")))
                .andExpect(jsonPath("$.data.lastName", is("Last Name1 Updated")))
                .andExpect(jsonPath("$.data.email", is("email1updated@gmail.com")))
                .andExpect(jsonPath("$.data.phone", is("0743999999")))
                .andExpect(jsonPath("$.data.username", is("username1updated")))
                .andExpect(jsonPath("$.data.role", is("USER")))
                .andExpect(jsonPath("$.message", is("User updated successfully.")));

        verify(appUserService).updateAppUser(anyLong(), eq(request));
        verifyNoMoreInteractions(appUserService);
    }

    @Test
    public void deleteUserByUsernameTest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{username}", "username1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User deleted successfully")));

        verify(appUserService).deleteUserByUsername(anyString());
        verifyNoMoreInteractions(appUserService);
    }

    @Test
    public void checkEmailExistsTest() throws Exception {
        when(appUserService.checkEmailExists(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/v1/users/check-email")
                        .param("email", "email1@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));

        verify(appUserService).checkEmailExists(anyString());
        verifyNoMoreInteractions(appUserService);
    }

    private ResponseAppUserDto getUserOne() {
        return ResponseAppUserDto.builder()
                .firstName("First Name1")
                .lastName("Last Name1")
                .email("email1@gmail.com")
                .phone("0743999999")
                .username("username1")
                .role(RoleName.ADMIN.name())
                .rentals(new ArrayList<>())
                .build();
    }

    private ResponseAppUserDto getUserTwo() {
        return ResponseAppUserDto.builder()
                .firstName("First Name2")
                .lastName("Last Name2")
                .email("email2@gmail.com")
                .phone("0743111111")
                .username("username2")
                .role(RoleName.USER.name())
                .rentals(new ArrayList<>())
                .build();
    }

    @AfterEach
    public void destroy() throws Exception {
        closeable.close();
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}