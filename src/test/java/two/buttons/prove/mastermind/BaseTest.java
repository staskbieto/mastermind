package two.buttons.prove.mastermind;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;


@ActiveProfiles({BaseTest.PROFILE_TEST})
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {
        MastermindApplication.class
})
public abstract class BaseTest {
    public static final String PROFILE_TEST = "TEST";
    public static final String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("admin:4dm1n4dm1n").getBytes()));


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
