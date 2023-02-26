package okhttp;


import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {

    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("bqa@mail.ru").password("bQa$1234").build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AuthResponseDto resDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        System.out.println(resDto.getToken());
    }
//eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYnFhQG1haWwucnUiLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY3NzkzMjk3OCwiaWF0IjoxNjc3MzMyOTc4fQ.PnJYpOgsmOTByeiH2Xfbtv_J6VsQkKgm0Ov4TWw6EgE
@Test
public void loginWrongEmail() throws IOException {
    AuthRequestDto auth = AuthRequestDto.builder().username("noagmail.com").password("Nnoa12345$").build();
    RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

    Request request = new Request.Builder()
            .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
            .post(body).build();

    Response response = client.newCall(request).execute();
    Assert.assertFalse(response.isSuccessful());
    Assert.assertEquals(response.code(),401);

    ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

    Assert.assertEquals(errorDto.getStatus(),401);
    Assert.assertEquals(errorDto.getError(),"Unauthorized");
    Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");



}

}