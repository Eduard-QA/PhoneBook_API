package restassured;

import com.jayway.restassured.RestAssured;
import dto.AllContactsDto;
import dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsTestsRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYnFhQG1haWwucnUiLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3ODIwNzc5MSwiaWF0IjoxNjc3NjA3NzkxfQ.77cbsE2Mclgco-tIGACLBh8uRr8MPbiSXqBhFdNrd9M";

    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContactsSuccess() {
        AllContactsDto all = given()
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response().as(AllContactsDto.class);
        List<ContactDto> contacts = all.getContacts();
        for (ContactDto contactDto : contacts) {
            System.out.println(contactDto.getId());
            System.out.println("**********");
        }

    }
    @Test
    public void getAllContactsNegative() {
        given()
                .header("Authorization", "djcnb")
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(401);


    }

}
