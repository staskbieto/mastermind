package two.buttons.prove.mastermind;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles({BaseTest.PROFILE_TEST})
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {
        MastermindApplication.class
})
public abstract class BaseTest {
    public static final String PROFILE_TEST = "TEST";



    protected MockRestServiceServer mockServer;

    @Before
    public void setUpServer() {

    }

    protected Resource jsonResource(final String name) {
        return new ClassPathResource("/json/" + name + ".json");
    }

    protected Resource xmlResource(final String name) {
        return new ClassPathResource("/xml/" + name + ".xml");
    }


    protected Resource fileResource(final String name) {
        ClassPathResource classPathResource = new ClassPathResource("/file/" + name);
        return classPathResource;
    }


}
