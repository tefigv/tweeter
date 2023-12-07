package edu.byu.cs.tweeter.client.presentors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.invocation.*;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.services.MainService;
import edu.byu.cs.tweeter.client.model.services.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

class MainPresenterTest {
    private MainPresenter.MainView mockMainView;
    private MainService mockMainService;
    private StatusService mockStatusService;
    private Cache mockCache;
    private MainPresenter mainPresenterSpy;
    private AuthToken authToken;
    private User user;

    @BeforeEach
    public void setup(){

        authToken = new AuthToken();

        authToken.token = "4321";

        mockMainView = mock(MainPresenter.MainView.class);
        mockMainService = mock(MainService.class);
        mockStatusService = mock(StatusService.class);
        mockCache = mock(Cache.class);

        user = new User("Mimi","Noni","@minoni","image");

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockMainView, user));
        when(mainPresenterSpy.returnMainService()).thenReturn(mockMainService);

        Cache.setInstance(mockCache);

    }

    @Test
    void postStatusSuccess() {

        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.MainActivityObserver observer = invocation.getArgument(2, MainService.MainActivityObserver.class);
                Status status = invocation.getArgument(1, Status.class);
                Assertions.assertEquals("This is my post", status.post);
                observer.successMessage("Successfully Posted!", "post");
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockMainService).status(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(user,"This is my post", authToken);
        Mockito.verify(mockMainView).showInfoMessage("Posting Status...");
        Mockito.verify(mockMainView).post("Successfully Posted!");

    }

    @Test
    void postStatusFailure(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.MainActivityObserver observer = invocation.getArgument(2, MainService.MainActivityObserver.class);
                //verifying that the post passed is correct
                Status status = invocation.getArgument(1, Status.class);
                Assertions.assertEquals("This is my post",status.post);
                observer.handleFailure("Failed to post!");
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).status(Mockito.any(),Mockito.any(),Mockito.any());

        mainPresenterSpy.postStatus(user,"This is my post",authToken);

        Mockito.verify(mockMainView).showInfoMessage("Posting Status...");
        Mockito.verify(mockMainView).showErrorMessage("Failed to post!");
    }

    @Test
    void postStatusException(){
        Answer<Void> answer = new Answer<>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainService.MainActivityObserver observer = invocation.getArgument(2, MainService.MainActivityObserver.class);
                //verifying that the post passed is correct
                Status status = invocation.getArgument(1, Status.class);
                Assertions.assertEquals("This is my post",status.post);

                observer.handleException(new Exception("Post exception message"));
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockMainService).status(Mockito.isA(AuthToken.class), Mockito.isA(Status.class),Mockito.any());

        mainPresenterSpy.postStatus(user,"This is my post",authToken);

        Mockito.verify(mockMainView).showInfoMessage("Posting Status...");
        Mockito.verify(mockMainView).showErrorMessage("Fail to get item because of exception: "+ "Post exception message");
    }



}