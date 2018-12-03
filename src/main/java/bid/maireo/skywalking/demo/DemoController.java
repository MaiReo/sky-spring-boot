package bid.maireo.skywalking.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class DemoController {

    private final RestTemplate restTemplate;

    private  final  HttpClient httpClient;

    @Autowired
    public  DemoController(RestTemplate  restTemplate, HttpClient httpClient){

        this.restTemplate = restTemplate;
        this. httpClient = httpClient;
    }

    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {

        return "Hello, " + name + "!";
    }

    @RequestMapping("/call")
    String call(@RequestParam String url) {
        if(url == null || url.trim().length()==0) return "error";
        String result = restTemplate.getForObject("http://"+ url,String.class);
        return result;
    }

    @RequestMapping("/call2")
    String call2(@RequestParam String url) throws IOException {
        if(url == null || url.trim().length()==0) return "error";

        HttpGet httpGet = new HttpGet("http://"+ url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity());
        return result;
    }
}
