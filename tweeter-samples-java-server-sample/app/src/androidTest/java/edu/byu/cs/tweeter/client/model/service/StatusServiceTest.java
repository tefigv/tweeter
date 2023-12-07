package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class StatusServiceTest {
    User currentUser;
    AuthToken authToken;
    StatusService statusServiceSpy;
    StatusServiceObserver observer;
    CountDownLatch countDownLatch;

    @BeforeEach
    public void setup(){
        currentUser = new User("firstName", "lastName","image");
        authToken = new AuthToken();
        statusServiceSpy = Mockito.spy(new StatusService());
        observer = new StatusServiceObserver();

        resetCountDownLatch();
    }
    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    private class StatusServiceObserver implements StatusService.GetStatusObserver{
        private boolean success;
        private String message;
        private List<Status> story;
        private boolean hasMorePages;
        private Exception exception;


        @Override
        public void handleFailure(String message) {
            this.success = false;
            this.message = message;
            this.story = null;
            this.hasMorePages = false;
            this.exception = null;

            countDownLatch.countDown();
        }

        @Override
        public void handleException(Exception exception) {
            this.success = false;;
            this.message = null;
            this.story = null;
            this.hasMorePages = false;
            this.exception = exception;

            countDownLatch.countDown();
        }

        @Override
        public void successfulGetStatus(List<Status> status, boolean hasMorePages) {
            this.success = true;
            this.message = null;
            this.story = status;
            this.hasMorePages = hasMorePages;
            this.exception = null;

            countDownLatch.countDown();
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<Status> getStory() {
            return story;
        }

        public boolean isHasMorePages() {
            return hasMorePages;
        }

        public Exception getException() {
            return exception;
        }
    }

    @Test
    public void getStorySuccess() throws InterruptedException {
        statusServiceSpy.getStoryStatus(authToken,currentUser,3,null,observer);
        awaitCountDownLatch();

        List<Status> expectedStory = FakeData.getInstance().getFakeStatuses().subList(0,3);
        Assertions.assertTrue(observer.isSuccess());
        Assertions.assertNull(observer.getMessage());
        Assertions.assertEquals(expectedStory, observer.getStory());
        Assertions.assertTrue(observer.isHasMorePages());
        Assertions.assertNull(observer.getException());

    }

}
