package two.buttons.prove.mastermind.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.yaml.snakeyaml.util.ArrayUtils;
import two.buttons.prove.mastermind.BaseTest;
import two.buttons.prove.mastermind.beans.ColorEnum;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Game;
import two.buttons.prove.mastermind.repository.GameRepository;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.assertTrue;


@AutoConfigureMockMvc
public class GameControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void pushDataToTestDB(){
        gameRepository.save(Game.builder()
                .key(123436475L)
                .code(
                        Arrays.asList(new ColorEnum[]{ColorEnum.BLACK, ColorEnum.BLACK, ColorEnum.RED, ColorEnum.GREEN})
                )
                .build()
        );
    }

    @After
    public void resetTestDB() throws Exception {
        gameRepository.deleteAll();
    }


    @Test
    public void initGameOk() throws Exception{

        MvcResult result = mockMvc.perform(get("/init")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                ;

        Long gameId = Long.parseLong(result.getResponse().getContentAsString());
        assertTrue(gameRepository.findById(gameId).isPresent());

    }


    @Test
    public void guessOk() throws Exception{
        String jsonString = new JSONObject()
                .put("gameKey","123436475")
                .put("guess",new JSONArray()
                        .put("BLUE")
                        .put("RED")
                        .put("RED")
                        .put("GREEN")
                )
                .toString();

        MvcResult result = mockMvc.perform(post("/guess")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString)
        )        .andExpect(status().isOk())
                .andReturn()
                ;

        GuessResponse guessResponse = objectMapper.readValue( result.getResponse().getContentAsString(), GuessResponse.class);

        assertTrue(guessResponse.getHistoric().size() == 1);
        assertTrue(guessResponse.getExact() == 2);
        assertTrue(guessResponse.getNear() == 0);
        assertTrue(!guessResponse.getIsFinished());
    }



    @Test
    public void guessFailGameNotFound() throws Exception {

        String jsonString = new JSONObject()
                .put("gameKey", "555555")
                .put("guess", new JSONArray()
                        .put("BLUE")
                        .put("RED")
                        .put("RED")
                        .put("GREEN")
                )
                .toString();

        ResultActions result = mockMvc.perform(post("/guess")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString)
        )        .andExpect(status().isNotFound())
                ;
    }

}
