import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Demo {

    OkHttpClient client = new OkHttpClient();
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    // App information
    String server = "https://robot-service.centaurstech.com/api/chat";
    String appkey = "open-demo";
    String appsecret = "123456789";
    String nickname = "张三李四王老五";

    // Get hardware fingerprint
    String uid = GetNetworkAddress.GetAddress("mac");

    public String getResponse(String ask) throws Exception {
        // Prepare verify string
        String now = (new Date()).getTime() + "";
        String verify = Md5.digest(appsecret + uid + now);

        // Generate request body
        String bodyStr = String.format(
                "appkey=%s&timestamp=%s&uid=%s&verify=%s&msg=%s&nickname=%s",
                appkey, now, uid, verify, ask, nickname);

        RequestBody body = RequestBody.create(mediaType,
                bodyStr);
        Request request = new Request.Builder()
                .url(server)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String res = response.body().string();
        System.out.println(res);

        JSONObject json = new JSONObject(res);
        if (json.has("data"))
            System.out.println("Appended data: " + json.getJSONObject("data"));
        else
            System.out.println("There is no extra data");
        return json.getString("msg");
    }

    public static void main(String[] args) throws Exception {
        Demo demo = new Demo();
        String message = demo.getResponse("HELLO");
        System.out.println(message);

        Scanner sc = new Scanner(System.in);
        while (true) {
            message = sc.nextLine();
            message = demo.getResponse(message);
            System.out.println(message);
        }

    }

}
