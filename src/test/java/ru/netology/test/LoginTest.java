package ru.netology.test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.page.LoginPage;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;


public class LoginTest {
    @AfterAll
    static  void teardown() {
        cleanDatabase();
    }

    @Test

    void shouldSuccessfulLogin() {
        var loginPage = open("http://185.119.57.64:9999", LoginPage.class );
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin( authInfo );
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify( verificationCode.getCode() );
    }
}
