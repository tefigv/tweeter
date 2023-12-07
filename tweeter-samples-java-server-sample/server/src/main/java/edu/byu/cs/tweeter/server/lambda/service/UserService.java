package edu.byu.cs.tweeter.server.lambda.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.server.dao.*;
import edu.byu.cs.tweeter.server.dao.bean.User;
import edu.byu.cs.tweeter.server.factory.*;

public class UserService extends Service {

    protected DaoFactory daoFactory = getFactoryInstance();
    protected IAuthTokenDAO authTokenDAO = daoFactory.AuthTokenDAO();
    protected IImageDAO imageDAO = daoFactory.ImageDAO();
    protected IUserDAO userDAO = daoFactory.UserDAO();

    public LoginResponse login(LoginRequest request) {

        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        }

        User user = userDAO.getItem(request.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }else{
            edu.byu.cs.tweeter.model.domain.User loggedInUser = new edu.byu.cs.tweeter.model.domain.User(user.getFirstName(),
                    user.getLastName(),user.getAlias(), user.getImage());
            AuthToken authToken = generateToken();

            return new LoginResponse(loggedInUser, authToken);
        }
    }

    public RegisterResponse register (RegisterRequest request){

        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing password");
        }
        if(request.getFirstName() == null){
            throw new RuntimeException("[Bad Request] Missing first name");
        }
        if(request.getLastName() == null){
            throw new RuntimeException("[Bad Request] Missing last name");
        }
        if(request.getImage() == null){
            throw new RuntimeException("[Bad Request] Missing image");
        }

        //hash password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(request.getPassword());

        String image = imageDAO.putItem(request.getImage(), request.getUsername());
        userDAO.putItem(request.getFirstName(),request.getLastName(),
                request.getUsername(),hash,0,0,image);


        User user = userDAO.getItem(request.getUsername());
        edu.byu.cs.tweeter.model.domain.User loggedInUser = new edu.byu.cs.tweeter.model.domain.User(user.getFirstName(),
                user.getLastName(),user.getAlias(), request.getImage());
        AuthToken authToken = generateToken();

        return new RegisterResponse(loggedInUser, authToken);
    }

    public AuthToken generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        long timestamp = Instant.now().plusSeconds(3600).getEpochSecond();
        AuthToken authToken= new AuthToken(base64Encoder.encodeToString(randomBytes),timestamp);
        return authToken;
    }


    public GetUserResponse getUser(GetUserRequest request){
        if(request.getAlias() ==null){
            throw new RuntimeException("[Bad Request] Missing alias");
        }

        User user = userDAO.getItem(request.getAlias());
        edu.byu.cs.tweeter.model.domain.User loggedInUser = new edu.byu.cs.tweeter.model.domain.User(user.getFirstName(),
                user.getLastName(),user.getAlias(),"image");

        return new GetUserResponse(loggedInUser);
    }

    public LogoutResponse logout(LogoutRequest request){
        if(request.getAuthToken() == null){
            throw new RuntimeException("[Bad Request] Missing token");
        }
        try{
        authTokenDAO.deleteItem(request.getAuthToken().getToken());
            return new LogoutResponse();
        }catch (Exception ex){
            throw new RuntimeException("Error: authToken couldn't be deleted");
        }
    }

}
