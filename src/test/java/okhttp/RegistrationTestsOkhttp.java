package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOkhttp {

    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(1000) + 1000;
        AuthRequestDto auth = AuthRequestDto.builder().username("bqa" + i + "@mail.ru").password("bQa$1234" + i).build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body).build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
    }
    @Test
    public void registrationWrong() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("bqamail.ru").password("bQa$1234i" ).build();
        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body).build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getStatus(),400);
        Assert.assertEquals(errorDto.getError(),"Bad Request");
        Assert.assertEquals(errorDto.getMessage().toString(), "{username=must be a well-formed email address}");
    }
}
