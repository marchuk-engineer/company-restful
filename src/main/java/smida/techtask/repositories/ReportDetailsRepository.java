package smida.techtask.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import smida.techtask.entities.ReportDetails;

import java.util.UUID;

public interface ReportDetailsRepository extends MongoRepository<ReportDetails, UUID> {

}
