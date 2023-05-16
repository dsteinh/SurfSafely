package hr.algebra.surfsafly.controller;

import hr.algebra.surfsafly.configuration.SecurityConfiguration;
import hr.algebra.surfsafly.converter.UserConverter;
import hr.algebra.surfsafly.dto.ApiResponseDto;
import hr.algebra.surfsafly.dto.UserDto;
import hr.algebra.surfsafly.exception.UserNotFoundException;
import hr.algebra.surfsafly.model.User;
import hr.algebra.surfsafly.security.JwtRequestFilter;
import hr.algebra.surfsafly.security.JwtUserDetailsService;
import hr.algebra.surfsafly.security.JwtUtils;
import hr.algebra.surfsafly.service.CurrentUserService;
import hr.algebra.surfsafly.service.JwtBlacklistService;
import hr.algebra.surfsafly.service.UserService;
import hr.algebra.surfsafly.util.user.UserDtoMother;
import hr.algebra.surfsafly.util.user.UserMother;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static hr.algebra.surfsafly.utils.StringUtils.asJsonString;


@WebMvcTest(controllers = AuthenticationController.class)
@Import(SecurityConfiguration.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserConverter userConverter;

    @MockBean
    private JwtBlacklistService jwtBlacklistService;

    @MockBean
    private CurrentUserService currentUserService;

    @MockBean
    private JwtUtils jwtUtils;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @MockBean
    JwtUserDetailsService jwtUserDetailsService;

    public static final String ALREADY_EXISTS_ERROR = "username already exists";

    @Test
    void register_returnsCreatedResponse_whenUserIsRegistered() throws Exception {
        UserDto userDto = UserDtoMother.complete().build();

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(ApiResponseDto.ok(userDto))))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void register_returnsConflictResponse_whenUserAlreadyExists() throws Exception {
        User user = UserMother.complete().build();
        UserDto userDto = UserDtoMother.complete().build();
        Mockito.when(userService.getByUsername(userDto.getUsername())).thenReturn(Optional.ofNullable(user));

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(ApiResponseDto.error(userDto, ALREADY_EXISTS_ERROR))))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenUserWithValidCredentials_whenLogin_thenReturnToken() throws Exception {
        UserDto userDto = UserDtoMother.complete().build();
        User user = UserMother.complete().build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("token", "generatedToken");

        Mockito.when(userService.getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword()))
                .thenReturn(Optional.of(user));
        Mockito.when(userConverter.convert(userDto)).thenReturn(user);
        Mockito.when(jwtUtils.generateToken(user)).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(ApiResponseDto.ok(expectedResponse))))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(userService).getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        Mockito.verify(userConverter).convert(userDto);
        Mockito.verify(jwtUtils).generateToken(user);
    }

    @Test
    void givenUserWithMissingCredentials_whenLogin_thenReturnError() throws Exception {
        UserDto userDto = UserDtoMother.usernameAndPasswordAreNull().build();

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(ApiResponseDto.builder().error("Username or Password is Empty").build())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenUserWithInvalidCredentials_whenLogin_thenReturnError() throws Exception {
        UserDto userDto = UserDtoMother.complete().build();
        Mockito.when(userService.getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword()))
                .thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(ApiResponseDto.builder().error("Username or Password is Invalid").build())))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(userService).getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
    }

    @Test
    void givenUserWithException_whenLogin_thenReturnError() throws Exception {
        UserDto userDto = UserDtoMother.complete().build();
        Mockito.when(userService.getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword()))
                .thenThrow(UserNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(ApiResponseDto.builder().build())))
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(userService).getUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
    }
}