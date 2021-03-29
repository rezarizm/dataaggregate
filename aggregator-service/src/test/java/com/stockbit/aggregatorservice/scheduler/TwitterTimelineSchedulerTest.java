package com.stockbit.aggregatorservice.scheduler;

import com.stockbit.aggregatorservice.service.TwitterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twitter4j.TwitterException;

import static org.mockito.Mockito.*;

class TwitterTimelineSchedulerTest {

    private TwitterService twitterService;
    private TwitterTimelineScheduler timelineScheduler;

    @BeforeEach
    void setUp() {
        twitterService = mock(TwitterService.class);
        timelineScheduler = new TwitterTimelineScheduler(twitterService);
    }

    @Test
    void givenScheduled_whenFetchTimeline_shouldCallTwitterService() throws TwitterException {
        timelineScheduler.fetchTimeline();
        verify(twitterService, times(1)).execute();
    }
}
