package two.buttons.prove.mastermind.controller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import two.buttons.prove.mastermind.BaseTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class GameControllerTest extends BaseTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void llistaSubscripcions() throws Exception{
        ResultActions result = mockMvc.perform(get("/init")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                ;

    }

}
