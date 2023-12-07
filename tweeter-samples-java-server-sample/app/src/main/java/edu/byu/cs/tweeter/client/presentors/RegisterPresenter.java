package edu.byu.cs.tweeter.client.presentors;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.model.services.UserService;
public class RegisterPresenter extends AuthenticatePresenter{

    public RegisterPresenter(View view) {
        super(view);
    }

    public void register(String firstname, String lastname, String alias, String password, ImageView picture) {

        // Convert image to byte array.
           Bitmap image = ((BitmapDrawable) picture.getDrawable()).getBitmap();
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
           byte[] imageBytes = bos.toByteArray();

           // Intentionally, Use the java Base64 encoder so it is compatible with M4.
           String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);


        if (validateRegistration(firstname, lastname, alias, password)) {
            authenticationView.hideErrorMessage();
            authenticationView.showInfoMessage("Registering...");

            var userService = new UserService();
            userService.register(firstname, lastname, alias, password, imageBytesBase64, this);
        }

    }

    public boolean validateRegistration(String firstname, String lastname, String alias, String password) {
        if (firstname.length() == 0) {
            view.showErrorMessage("First Name cannot be empty.");
            return false;
        }
        if (lastname.length() == 0) {
            view.showErrorMessage("Last Name cannot be empty.");
            return false;
        }
        if (alias.length() == 0) {
            view.showErrorMessage("Alias cannot be empty.");
            return false;
        }
        if (alias.charAt(0) != '@') {
            view.showErrorMessage("Alias must begin with @.");
            return false;
        }
        if (alias.length() < 2) {
            view.showErrorMessage("Alias must contain 1 or more characters after the @.");
            return false;
        }
        if (password.length() == 0) {
            view.showErrorMessage("Password cannot be empty.");
            return false;
        }

        return true;
    }


}
