package com.stockbit.aggregatorservice.processor;

import com.stockbit.aggregatorcore.user.usecase.create.UserCreationInputBoundary;
import com.stockbit.aggregatorcore.user.usecase.create.UserCreationRequest;
import com.stockbit.aggregatorcore.user.usecase.detail.UserDetailInputBoundary;
import com.stockbit.aggregatorcore.user.usecase.detail.UserDetailOutputBoundary;
import com.stockbit.aggregatorcore.user.usecase.detail.UserDetailRequest;
import com.stockbit.aggregatorservice.processor.impl.TwitterUserProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import twitter4j.User;

import static com.google.common.truth.Truth.assertThat;
import static com.stockbit.aggregatorcommon.constant.TestConstant.DUMMY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TwitterUserProcessorTest {

    private TwitterProcessor<User> processor;
    private UserDetailInputBoundary userDetailUseCase;
    private UserCreationInputBoundary userCreationUseCase;
    private User user;

    @BeforeEach
    void setUp() {
        userDetailUseCase = Mockito.mock(UserDetailInputBoundary.class);
        userCreationUseCase = Mockito.mock(UserCreationInputBoundary.class);
        processor = new TwitterUserProcessor(userDetailUseCase, userCreationUseCase);
        user = Mockito.mock(User.class);
    }

    @Test
    void givenValidRequest_whenProcess_shouldCallDetailUseCase() throws Exception {
        prepareAndExecute();
        verify(userDetailUseCase, times(1)).findOne(any(), any());
    }

    @Test
    void givenValidRequest_whenProcess_shouldCallDetailUseCaseWithCorrectRequest() throws Exception {
        prepareAndExecute();
        ArgumentCaptor<UserDetailRequest> requestCaptor = ArgumentCaptor.forClass(UserDetailRequest.class);
        verify(userDetailUseCase, times(1)).findOne(requestCaptor.capture(), any());
        UserDetailRequest request = requestCaptor.getValue();
        assertThat(request.getId()).isEqualTo(String.valueOf(user.getId()));
    }

    @Test
    void givenValidRequest_whenProcess_shouldCallDetailUseCaseWithCorrectPresenter() throws Exception {
        prepareAndExecute();
        ArgumentCaptor<UserDetailOutputBoundary> presenterCaptor =
                ArgumentCaptor.forClass(UserDetailOutputBoundary.class);
        verify(userDetailUseCase, times(1)).findOne(any(), presenterCaptor.capture());
        UserDetailOutputBoundary presenter = presenterCaptor.getValue();
        assertThat(presenter).isNotNull();
    }

    @Test
    void givenExistTweet_whenProcess_shouldNotCallCreationUseCase() throws Exception {
        prepareAndExecute();
        verify(userCreationUseCase, times(0)).create(any());
    }

    @Test
    void givenNotExistTweet_whenProcess_shouldCallCreationUseCase() throws Exception {
        doThrow(DataNotFoundException.class).when(userDetailUseCase).findOne(any(), any());
        prepareAndExecute();
        verify(userCreationUseCase, times(1)).create(any());
    }

    @Test
    void givenNotExistTweet_whenProcess_shouldCallCreationUseCaseWithCorrectParam() throws Exception {
        doThrow(DataNotFoundException.class).when(userDetailUseCase).findOne(any(), any());
        prepareAndExecute();
        ArgumentCaptor<UserCreationRequest> requestCaptor = ArgumentCaptor.forClass(UserCreationRequest.class);
        verify(userCreationUseCase, times(1)).create(requestCaptor.capture());
        UserCreationRequest request = requestCaptor.getValue();
        assertThat(request.getId()).isEqualTo(String.valueOf(user.getId()));
        assertThat(request.getName()).isEqualTo(user.getName());
        assertThat(request.getScreenName()).isEqualTo(user.getScreenName());
    }

    private void prepareAndExecute() {
        injectUser();
        processor.process(user);
    }

    private void injectUser() {
        when(user.getId()).thenReturn(1L);
        when(user.getName()).thenReturn(DUMMY);
        when(user.getScreenName()).thenReturn(DUMMY);
    }
}
