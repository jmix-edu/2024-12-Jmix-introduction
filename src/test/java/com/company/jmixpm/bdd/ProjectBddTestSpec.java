package com.company.jmixpm.bdd;

import com.codeborne.selenide.SelenideElement;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.*;

public class ProjectBddTestSpec {

    private final static String X_PATH_START = "//vaadin-grid-cell-content[contains(.,'";
    private final static String X_PATH_END = "')]";

    @Step("Open application in the browser")
    public void openAppURL(){
        open("/");
    }

    @Step("Log in as user with <username> username and <password> password")
    public void login(String username, String password) {

        SelenideElement usernameField = $(By.name("username"));
        usernameField.shouldBe(visible, enabled);
        usernameField.click();
        usernameField.sendKeys(Keys.CONTROL + "A");
        usernameField.sendKeys(Keys.BACK_SPACE);
        usernameField.sendKeys(username);

        SelenideElement passwordField = $(By.name("password"));
        passwordField.shouldBe(visible, enabled);
        passwordField.click();
        passwordField.sendKeys(Keys.CONTROL + "A");
        passwordField.sendKeys(Keys.BACK_SPACE);
        passwordField.sendKeys(password);

        SelenideElement loginButton = $(By.tagName("vaadin-button"));
        loginButton.click();
    }

    @Step("Open the project list view")
    public void openProjectBrowser() {

        SelenideElement projectsLink = $(By.linkText("Projects"));
        projectsLink.shouldBe(visible, enabled);
        projectsLink.click();
    }

    @Step("Open the project detail view to create new instance")
    public void openProjectEditor() {

        SelenideElement createButton = $(By.id("createButton"));
        createButton.shouldBe(visible, enabled);
        createButton.click();
    }

    @Step("Fill form fields with following values: name is <name>, manager name is <managerName>")
    public void fillFormFields(String name, String managerName) {

        SelenideElement projectNameField = $(By.id("nameField"));
        projectNameField.shouldBe(visible, enabled);
        projectNameField.click();
        projectNameField.sendKeys(name);

        SelenideElement pickerButton = $(By.cssSelector("jmix-value-picker-button"));
        pickerButton.shouldBe(visible, enabled);
        sleep(1000);
        pickerButton.click();
        sleep(1000);

        SelenideElement adminField = $(By.xpath(X_PATH_START + managerName + X_PATH_END));
        adminField.click();

        SelenideElement selectButton = $(By.id("selectButton"));
        selectButton.shouldBe(visible, enabled);
        sleep(1000);
        selectButton.click();

    }

    @Step("Save new project")
    public void saveNewProject() {
        SelenideElement saveButton = $(By.id("saveAndCloseButton"));
        saveButton.shouldBe(visible, enabled);
        sleep(1000);
        saveButton.click();
    }

    @Step("Make sure the new project with <passedName> name is added to project table")
    public void checkProjectCreation(String passedName) {
        sleep(1000);
        SelenideElement createdProjectField = $(By.xpath(X_PATH_START + passedName + X_PATH_END));
        createdProjectField.shouldBe(visible);
        createdProjectField.click();
    }

    @Step("Remove test project")
    public void removeProject() {
        sleep(1000);
        SelenideElement removeButton = $(By.id("removeButton"));
        removeButton.shouldBe(visible, enabled);
        removeButton.click();

        sleep(1000);
        SelenideElement okButton = $(By.xpath("//vaadin-button[contains(.,'Yes')]"));
        okButton.shouldBe(visible, enabled);
        okButton.click();
    }

}
