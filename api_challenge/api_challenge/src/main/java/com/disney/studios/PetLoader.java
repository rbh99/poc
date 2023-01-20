package com.disney.studios;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.disney.studios.domain.CreateDogRequest;
import com.disney.studios.service.DogService;

@Component
public class PetLoader implements InitializingBean {

    private static final Logger LOGGER = getLogger(PetLoader.class);

    // Resources to the different files we need to load.
    @Value("classpath:data/labrador.txt")
    private Resource labradors;

    @Value("classpath:data/pug.txt")
    private Resource pugs;

    @Value("classpath:data/retriever.txt")
    private Resource retrievers;

    @Value("classpath:data/yorkie.txt")
    private Resource yorkies;

    //@Autowired
    //DataSource dataSource;
    
    @Autowired
    DogService dogService;

    /**
     * Load the different breeds into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load the breeds.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadBreed("Labrador", labradors);
        loadBreed("Pug", pugs);
        loadBreed("Retriever", retrievers);
        loadBreed("Yorkie", yorkies);
    }

    /**
     * Reads the list of dogs in a category and (eventually) add
     * them to the data source.
     * @param breed The breed that we are loading.
     * @param source The file holding the breeds.
     * @throws IOException In case things go horribly, horribly wrong.
     */
    private void loadBreed(String breed, Resource source) throws IOException {
        LOGGER.debug("Loading breed {}", breed);
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
            String line;
            List<CreateDogRequest> dogsToCreate = new ArrayList<>();
            int count = 1;
            while ((line = br.readLine()) != null) {
                LOGGER.debug(line);
                // TODO: Create appropriate objects and save them to the datasource.
                
                //will assume each dog has only one picture, as in the files there is no information about dogs
                //we will generate a dog name based on the file line number
                dogsToCreate.add( CreateDogRequest.builder()
                		.dogName(breed+"_dog_"+count++)
                		.breedName(breed)
                		.pictureUri(line)
                		.build()
                );
            }
            dogsToCreate.forEach(dogService::addDog);
        }
    }
}
