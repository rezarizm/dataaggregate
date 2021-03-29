package com.stockbit.aggregatorservice.service;

import com.stockbit.aggregatorservice.service.impl.TwitterTimelineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import static org.mockito.Mockito.*;

class TwitterTimelineServiceTest {

    private Twitter twitter;
    private TwitterProcessor<User> userProcessor;
    private TwitterProcessor<Status> tweetProcessor;
    private TwitterService service;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        twitter = mock(Twitter.class);
        userProcessor = mock(TwitterProcessor.class);
        tweetProcessor = mock(TwitterProcessor.class);
        service = new TwitterTimelineService(twitter, userProcessor, tweetProcessor);
    }

    @Test
    void givenValidRequest_whenFetchTimeline_shouldCallTwitter() throws Exception {
        service.execute();
        verify(twitter, times(1)).getHomeTimeline();
    }
}
