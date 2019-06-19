package two.buttons.prove.mastermind.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import two.buttons.prove.mastermind.BaseTest;
import two.buttons.prove.mastermind.beans.ColorEnum;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Game;
import two.buttons.prove.mastermind.repository.GameRepository;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class GameControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    @Before
    public void pushDataToTestDB(){

        gameRepository.deleteAll();
        gameRepository.save(Game.builder()
                .key(123436475L)
                .code(
                        Arrays.asList(new ColorEnum[]{ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.RED, ColorEnum.GREEN})
                )
                .build()
        );

        gameRepository.save(Game.builder()
                .key(4237433475L)
                .code(
                        Arrays.asList(new ColorEnum[]{ColorEnum.RED, ColorEnum.YELLOW, ColorEnum.RED, ColorEnum.GREEN})
                )
                .isFinished(true)
                .build()
        );

    }

    @Test
    public void initGameOk() throws Exception{


        MvcResult result = mockMvc.perform(get("/init")
                .header("Authorization", basicDigestHeaderValue).accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                ;

        Long gameId = Long.parseLong(result.getResponse().getContentAsString());
        assertTrue(gameRepository.findByKey(gameId).isPresent());

    }


    @Test
    public void guessOk() throws Exception{
        String jsonString = new JSONObject()
                .put("gameKey","123436475")
                .put("guess",new JSONArray()
                        .put("YELLOW")
                        .put("RED")
                        .put("RED")
                        .put("GREEN")
                )
                .toString();

        String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64(("admin:4dm1n4dm1n").getBytes()));
        MvcResult result = mockMvc.perform(post("/guess")
                .header("Authorization", basicDigestHeaderValue).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        )
                .andExpect(status().isOk())
                .andReturn()
                ;

        GuessResponse guessResponse = objectMapper.readValue( result.getResponse().getContentAsString(), GuessResponse.class);

        assertTrue(guessResponse.getHistoric().size() == 1);
        assertTrue(guessResponse.getExact() == 2);
        assertTrue(guessResponse.getNear() == 0);
        assertTrue(!guessResponse.getIsFinished());
    }


    @Test
    public void guessOkFinished() throws Exception{
        String jsonString = new JSONObject()
                .put("gameKey","123436475")
                .put("guess",new JSONArray()
                        .put("BLACK")
                        .put("BLACK")
                        .put("RED")
                        .put("GREEN")
                )
                .toString();

        MvcResult result = mockMvc.perform(post("/guess")
                .header("Authorization", basicDigestHeaderValue).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        )
                .andExpect(status().isOk())
                .andReturn()
                ;

        GuessResponse guessResponse = objectMapper.readValue( result.getResponse().getContentAsString(), GuessResponse.class);

        assertTrue(guessResponse.getHistoric().size() == 1);
        assertTrue(guessResponse.getExact() == 4);
        assertTrue(guessResponse.getNear() == 0);
        assertTrue(guessResponse.getIsFinished());
    }

    @Test
    public void guessFailFinished() throws Exception{
        String jsonString = new JSONObject()
                .put("gameKey","4237433475")
                .put("guess",new JSONArray()
                        .put("YELLOW")
                        .put("RED")
                        .put("RED")
                        .put("GREEN")
                )
                .toString();
        ResultActions result = mockMvc.perform(post("/guess")
                .header("Authorization", basicDigestHeaderValue).accept(MediaType.APPLICATION_JSON)

                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void guessFailGameNotFound() throws Exception {
        String jsonString = new JSONObject()
                .put("gameKey","9237433475")
                .put("guess",new JSONArray()
                        .put("YELLOW")
                        .put("RED")
                        .put("RED")
                        .put("GREEN")
                )
                .toString();

        ResultActions result = mockMvc.perform(post("/guess")
                .header("Authorization", basicDigestHeaderValue).accept(MediaType.APPLICATION_JSON)

                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)

        )        .andExpect(status().isNotFound())
                ;
    }

}
