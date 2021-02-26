package com.niveksys.recipeapp.router;

import static org.mockito.Mockito.when;

import com.niveksys.recipeapp.model.Recipe;
import com.niveksys.recipeapp.service.RecipeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
// We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeRouterTests {

    @MockBean
    RecipeService recipeService;

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    WebTestClient webTestClient;

    // @BeforeEach
    // public void setUp() {
    // RecipeRouter router = new RecipeRouter();
    // RouterFunction<?> routerFunction = router.listRoute(this.recipeService);
    // this.webTestClient =
    // WebTestClient.bindToRouterFunction(routerFunction).build();
    // }

    @Test
    public void list() throws Exception {
        // given
        when(recipeService.getRecipes()).thenReturn(Flux.just());
        // when
        this.webTestClient.get().uri("/api/recipes").accept(MediaType.APPLICATION_JSON).exchange().expectStatus()
                .isOk();
    }

    @Test
    public void listWithData() throws Exception {
        // given
        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));
        // when
        this.webTestClient.get().uri("/api/recipes").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }
}
