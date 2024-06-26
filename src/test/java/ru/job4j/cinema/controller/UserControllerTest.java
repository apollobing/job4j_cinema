package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserService userService;

    private UserController userController;

    private User user;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        user = new User(1, "email@email.com", "name", "password");
    }

    @Test
    public void whenRequestRegisterPageThenGetRegisterPage() {
        var view = userController.getRegistrationPage();

        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenRegisterUserThenGetSameDataAndRedirectToLoginPage() {
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var response = new MockHttpServletResponse();
        var view = userController.register(model, user, response);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/users/register");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenRegisterUserThatDBHasThenGetPageWithErrorMessageAnd409Status() {
        when(userService.save(user)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var response = new MockHttpServletResponse();
        var view = userController.register(model, user, response);
        var actualErrorMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/error");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(actualErrorMessage).isEqualTo("User with the same email already exists");
    }

    @Test
    public void whenRequestLoginPageThenGetLoginPage() {
        var view = userController.getLoginPage();

        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenLoginUserThenGetSameDataAndRedirectToIndexPage() {
        var userEmailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        var userPasswordArgumentCaptor = ArgumentCaptor.forClass(String.class);
        when(userService.findByEmailAndPassword(userEmailArgumentCaptor.capture(), userPasswordArgumentCaptor.capture()))
                .thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, new MockHttpServletRequest());
        var actualUserEmail = userEmailArgumentCaptor.getValue();
        var actualUserPassword = userPasswordArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(actualUserEmail).isEqualTo(user.getEmail());
        assertThat(actualUserPassword).isEqualTo(user.getPassword());
    }

    @Test
    public void whenLoginUserWithWrongDataThenCanNotGetIndexPageAndGetLoginPageWithErrorMessage() {
        when(userService.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, new MockHttpServletRequest());
        var actualErrorMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualErrorMessage).isEqualTo("Email or password is incorrect");
    }

    @Test
    public void whenRequestLogoutThenRedirectToLoginPage() {
        MockHttpSession session = new MockHttpSession();
        var view = userController.logout(session);

        assertThat(session.isInvalid()).isTrue();
        assertThat(view).isEqualTo("redirect:/users/login");
    }

}