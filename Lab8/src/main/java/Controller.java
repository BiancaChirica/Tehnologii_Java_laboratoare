import SubmissionNumber.Random;
import Util.FileUploader;
import Util.Period;
import interceptor.InterceptorBinding;
import models.RegisterUserModel;
import models.User;
import org.primefaces.model.file.UploadedFile;
import services.UserService;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Default
@Named("controller")
@SessionScoped
public class Controller implements Serializable {
    @Inject
    private User currentUser;
    @Inject
    RegisterUserModel registerUser;
    @Inject
    private UserService userService;

    private UploadedFile file;

    @Random
    @Inject
    Instance<Integer> randomInt;

    // fileSubmitted[uniq submission number][0] = username
    // fileSubmitted[][1] = oldFileName
    private String[][] fileSubmitted = new String[1001][3];

    public Date getEndDate() {
        return Period.getEndDate();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public RegisterUserModel getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(RegisterUserModel registerUser) {
        this.registerUser = registerUser;
    }

    public void addUser() {
        this.registerUser.setRole("guest");
        saveUser();
    }

    // register functions

    public void addAdmin() {
        this.registerUser.setRole("admin");
        saveUser();
    }

    private void saveUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (Period.isInPeriod()) {
            boolean addUserSuccess = this.userService.createUser(this.registerUser.getUsername(), this.registerUser.getPassword(), this.registerUser.getRole());
            if (addUserSuccess) {
                context.addMessage(null, new FacesMessage("SUCCESS", "Account created successfully"));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", "Account was not created : An error occurred"));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", "Period for registration and submission expired"));
        }
    }

    // login functions

    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean check = this.userService.checkUser(this.currentUser.getUsername(), this.currentUser.getPassword());
        if (check) {
            this.currentUser.setLoggedOn(true);
            this.currentUser.setRole(this.userService.getRoleByUsername(this.currentUser.getUsername()));
            context.addMessage(null, new FacesMessage("SUCCESS", "You are now logged on"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", "Invalid username or password"));
        }
    }

    public void redirectToLogin() {
        if (!isLoggedOn()) {
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler)
                    context.getApplication().getNavigationHandler();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", "Please login and refresh to access the desired page"));
            nav.performNavigation("login");
        }
    }

    private boolean isLoggedOn() {
        if (currentUser == null || currentUser.isLoggedOn() == false) {
            return false;
        }
        return true;

    }

    // file update functions

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        if (file == null)
            return;
        this.file = file;
    }

    @InterceptorBinding
    public void upload() {
        if (file == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "FAILURE", "Bad file.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        if (currentUser == null || !currentUser.isLoggedOn()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "FAILURE", "You are not logged in.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        if (!currentUser.getRole().equals("guest")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "FAILURE", "You have to be a guest to upload file.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        if (!Period.isInPeriod()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "FAILURE", "The period to upload files is over.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }
        int uniqSubmissionNumber = randomInt.get();
        while (fileSubmitted[uniqSubmissionNumber][0] != null) {
            uniqSubmissionNumber = randomInt.get();
        }

        fileSubmitted[uniqSubmissionNumber][0] = currentUser.getUsername();
        fileSubmitted[uniqSubmissionNumber][1] = file.getFileName();

        String fileName = "File" + uniqSubmissionNumber;
        FileUploader.writeFile(file, fileName);


        FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public List<String> getAllSubmissions() {
        String item;
        List<String> allFiles = new ArrayList<>();
        for (int count = 0; count < fileSubmitted.length; count++) {
            if (fileSubmitted[count][0] != null) {
                item = "User " + fileSubmitted[count][0] + " added the file "
                        + fileSubmitted[count][1];
                allFiles.add(item);
            }
        }
        return allFiles;
    }


    public void redirectForGuest() {
        if (!isLoggedOn()) {
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler)
                    context.getApplication().getNavigationHandler();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", "Please login and refresh to access the desired page"));
            nav.performNavigation("login");
            return;
        }
        if (!currentUser.getRole().equals("admin")) {
            FacesContext context = FacesContext.getCurrentInstance();
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler)
                    context.getApplication().getNavigationHandler();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILURE", "You have to be an admin to access this page."));
            nav.performNavigation("login");
        }

    }
}