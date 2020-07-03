package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {


    private int maxtotal;
    private int defaultmaxperroute;
    private int connectionrequesttimeout;
    private int sockettimeout;

    public BlockingRestTemplateCustomizer(@Value("${sfg.maxtotal}") int maxtotal,@Value("${sfg.defaultmaxperroute}") int defaultmaxperroute,@Value("${sfg.connectionrequesttimeout}") int connectionrequesttimeout,@Value("${sfg.sockettimeout}") int sockettimeout) {
        this.maxtotal = maxtotal;
        this.defaultmaxperroute = defaultmaxperroute;
        this.connectionrequesttimeout = connectionrequesttimeout;
        this.sockettimeout = sockettimeout;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager=new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(this.maxtotal);
        connectionManager.setDefaultMaxPerRoute(this.defaultmaxperroute);

        RequestConfig requestConfig=RequestConfig.custom()
                .setConnectionRequestTimeout(this.connectionrequesttimeout)
                .setSocketTimeout(this.sockettimeout)
                .build();

        CloseableHttpClient httpClient= HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig).build();

        return new  HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}
