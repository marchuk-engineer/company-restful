package smida.techtask.migrations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import smida.techtask.entities.ReportDetails;
import smida.techtask.repositories.ReportDetailsRepository;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MongoDataLoader implements CommandLineRunner {


    private final ReportDetailsRepository reportDetailsRepository;

    // TODO create mongo collection
    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("db/changelog/mongodb/init-details.json");
        InputStream inputStream = resource.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ReportDetails> myDataList = objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        reportDetailsRepository.saveAll(myDataList);
    }

}
