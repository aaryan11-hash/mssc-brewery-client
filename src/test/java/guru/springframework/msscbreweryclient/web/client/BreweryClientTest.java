package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getbeerbyId() {

        BeerDto beerdto=client.getbeerbyId(UUID.randomUUID());
        assertNotNull(beerdto);
    }

    @Test
    void saveNewBeer() {
        //given
       BeerDto beerDto= BeerDto.builder().beerName("New Beer").build();
      URI uri= client.saveNewBeer(beerDto);

        assertNotNull(uri);
        System.out.println(uri.toString());
    }

    @Test
    void handleUpdate() {
        client.handleUpdate(UUID.randomUUID(),BeerDto.builder().build());

    }

    @Test
    void deleteBeer() {

        client.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomer() {
        CustomerDto customerDto=client.getCustomer(UUID.randomUUID());

        assertNotNull(customerDto);

    }

    @Test
    void handlePost() {
        CustomerDto customerDto=CustomerDto.builder().name("aaryan").build();
        URI uri=client.handlePost(customerDto);

        assertNotNull(uri);

        System.out.println(uri.toString());
    }

    @Test
    void testHandleUpdate() {

        client.handlePost(CustomerDto.builder().build());
    }

    @Test
    void deleteCustomer() {

        client.deleteCustomer(UUID.randomUUID());
    }
}