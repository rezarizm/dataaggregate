package com.stockbit.aggregatorservice.controller;

import com.stockbit.aggregatorservice.service.TwitterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twitter4j.TwitterException;

import static org.mockito.Mockito.*;

class TimelineControllerTest {

    private TimelineController controller;
    private TwitterService twitterService;

    @BeforeEach
    void setUp() {
        twitterService = mock(TwitterService.class);
        controller = new TimelineController(twitterService);
    }

    @Test
    void givenRequest_whenFetchTimeLine_shouldCallService() throws TwitterException {
        controller.fetchTimeline();
        verify(twitterService, times(1)).execute();
    }
}
