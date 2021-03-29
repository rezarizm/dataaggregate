package com.stockbit.aggregatorservice.processor;

import com.stockbit.aggregatorcommon.util.DateTimeUtil;
import com.stockbit.aggregatorcore.tweet.usecase.create.TweetCreationInputBoundary;
import com.stockbit.aggregatorcore.tweet.usecase.create.TweetCreationRequest;
import com.stockbit.aggregatorcore.tweet.usecase.detail.TweetDetailInputBoundary;
import com.stockbit.aggregatorcore.tweet.usecase.detail.TweetDetailOutputBoundary;
import com.stockbit.aggregatorcore.tweet.usecase.detail.TweetDetailRequest;
import com.stockbit.aggregatorservice.processor.impl.TwitterTweetProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import twitter4j.Status;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static com.stockbit.aggregatorcommon.constant.TestConstant.DUMMY;
import static org.mockito.Mockito.*;

class TwitterTweetProcessorTest {

    private TwitterProcessor<Status> processor;
    private TweetDetailInputBoundary tweetDetailUseCase;
    private TweetCreationInputBoundary tweetCreationUseCase;
    private Status status;

    @BeforeEach
    void setUp() {
        tweetDetailUseCase = mock(TweetDetailInputBoundary.class);
        tweetCreationUseCase = mock(TweetCreationInputBoundary.class);
        processor = new TwitterTweetProcessor(tweetDetailUseCase, tweetCreationUseCase);
        status = mock(Status.class);
    }

    @Test
    void givenValidRequest_whenProcess_shouldCallDetailUseCase() throws Exception {
        prepareAndExecute();
        verify(tweetDetailUseCase, times(1)).findOne(any(), any());
    }

    @Test
    void givenValidRequest_whenProcess_shouldCallDetailUseCaseWithCorrectRequest() throws Exception {
        prepareAndExecute();
        ArgumentCaptor<TweetDetailRequest> requestCaptor = ArgumentCaptor.forClass(TweetDetailRequest.class);
        verify(tweetDetailUseCase, times(1)).findOne(requestCaptor.capture(), any());
        TweetDetailRequest request = requestCaptor.getValue();
        assertThat(request.getId()).isEqualTo(String.valueOf(status.getId()));
    }

    @Test
    void givenValidRequest_whenProcess_shouldCallDetailUseCaseWithCorrectPresenter() throws Exception {
        prepareAndExecute();
        ArgumentCaptor<TweetDetailOutputBoundary> presenterCaptor =
                ArgumentCaptor.forClass(TweetDetailOutputBoundary.class);
        verify(tweetDetailUseCase, times(1)).findOne(any(), presenterCaptor.capture());
        TweetDetailOutputBoundary presenter = presenterCaptor.getValue();
        assertThat(presenter).isNotNull();
    }

    @Test
    void givenExistTweet_whenProcess_shouldNotCallCreationUseCase() throws Exception {
        prepareAndExecute();
        verify(tweetCreationUseCase, times(0)).create(any());
    }

    @Test
    void givenNotExistTweet_whenProcess_shouldCallCreationUseCase() throws Exception {
        doThrow(DataNotFoundException.class).when(tweetDetailUseCase).findOne(any(), any());
        prepareAndExecute();
        verify(tweetCreationUseCase, times(1)).create(any());
    }

    @Test
    void givenNotExistTweet_whenProcess_shouldCallCreationUseCaseWithCorrectParam() throws Exception {
        doThrow(DataNotFoundException.class).when(tweetDetailUseCase).findOne(any(), any());
        prepareAndExecute();
        ArgumentCaptor<TweetCreationRequest> requestCaptor = ArgumentCaptor.forClass(TweetCreationRequest.class);
        verify(tweetCreationUseCase, times(1)).create(requestCaptor.capture());
        TweetCreationRequest request = requestCaptor.getValue();
        assertThat(request.getId()).isEqualTo(String.valueOf(status.getId()));
        assertThat(request.getCreatedAt()).isEqualTo(DateTimeUtil.toLocalDateTime(status.getCreatedAt()));
        assertThat(request.getText()).isEqualTo(status.getText());
        assertThat(request.getSource()).isEqualTo(status.getSource());
    }

    private void prepareAndExecute() {
        injectStatus();
        processor.process(status);
    }

    private void injectStatus() {
        when(status.getId()).thenReturn(1L);
        when(status.getCreatedAt()).thenReturn(new Date());
        when(status.getText()).thenReturn(DUMMY);
        when(status.getSource()).thenReturn(DUMMY);
    }
}
