package smida.techtask.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import smida.techtask.constants.ApiConstants;
import smida.techtask.dto.CompanyDto;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;
import smida.techtask.entities.ReportDetails;
import smida.techtask.repositories.CompanyRepository;
import smida.techtask.repositories.ReportDetailsRepository;
import smida.techtask.repositories.ReportRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles(profiles = {"test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("Company Controller API tests")
@Log4j2
class CompanyControllerTest {

    private static final String URL = ApiConstants.BASE_URL + "/companies/";
    private static final Integer NUMBER_OF_INIT_COMPANIES = 100;
    private static final Integer NUMBER_REPORTS_PER_COMPANY = 5;

    private List<Company> initSavedCompanies = new ArrayList<>(NUMBER_OF_INIT_COMPANIES);
    private List<Report> initSavedReports = new ArrayList<>(NUMBER_OF_INIT_COMPANIES * NUMBER_REPORTS_PER_COMPANY);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportDetailsRepository reportDetailsRepository;

    @BeforeAll
    void setUp() {
        companyRepository.deleteAll();
        reportRepository.deleteAll();
        reportDetailsRepository.deleteAll();
        initData();
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    private void initData() {
        List<Company> companies = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_INIT_COMPANIES; i++) {
            // Create and save Company first
            Company company = Company.builder()
                    .name("Name " + RandomStringUtils.randomAlphabetic(5))
                    .address("Address " + RandomStringUtils.randomAlphabetic(10))
                    .registrationNumber("Number " + RandomUtils.nextInt(1, 2000))
                    .createdAt(Instant.now())
                    .build();
            Company savedCompany = companyRepository.save(company);

            List<Report> reports = new ArrayList<>();
            for (int j = 0; j < NUMBER_REPORTS_PER_COMPANY; j++) {
                // Create Report with the saved company
                Report report = Report.builder()
                        .reportDate(Instant.now())
                        .totalRevenue(BigDecimal.valueOf(RandomUtils.nextDouble(1000, 10000)))
                        .netProfit(BigDecimal.valueOf(RandomUtils.nextDouble(100, 1000)))
                        .company(savedCompany)
                        .build();
                Report savedReport = reportRepository.save(report);

                ReportDetails reportDetails = ReportDetails.builder()
                        .reportId(savedReport.getId())
                        .financialData("Financial Data " + RandomStringUtils.randomAlphabetic(10))
                        .comments("Comments " + RandomStringUtils.randomAlphabetic(10))
                        .build();
                reportDetailsRepository.save(reportDetails);

                reports.add(savedReport);
            }
            savedCompany.setReports(reports);
            companyRepository.save(savedCompany); // Save the company again to update it with the reports
            companies.add(savedCompany);
        }

        initSavedCompanies = companyRepository.saveAll(companies);
        initSavedReports = initSavedCompanies.stream()
                .map(Company::getReports)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info("Data initialization completed");
    }

    @Nested
    @DisplayName("GET all companies")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"USER", "EDITOR", "ADMIN"})
    class GetAll {

        @Test
        @SneakyThrows
        @DisplayName("[200] 500 init companies")
        void testGetIs200() {
            mvc.perform(get(URL))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(NUMBER_OF_INIT_COMPANIES))
                    .andExpect(jsonPath("$[0].id").exists())
                    .andExpect(jsonPath("$[0].name").exists())
                    .andExpect(jsonPath("$[0].registrationNumber").exists())
                    .andExpect(jsonPath("$[0].address").exists())
                    .andExpect(jsonPath("$[0].createdAt").exists());
        }

    }

    @Nested
    @DisplayName("GET company by ID")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"USER", "EDITOR", "ADMIN"})
    class GetById {

        @Test
        @SneakyThrows
        @DisplayName("[200] present")
        void testGetCompanyByIdIs200() {
            List<UUID> presentIds = initSavedCompanies.stream().map(Company::getId).toList();
            UUID randomSavedId = presentIds.get(new Random().nextInt(0, NUMBER_OF_INIT_COMPANIES));

            mvc.perform(get(URL + randomSavedId))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(randomSavedId.toString()))
                    .andExpect(jsonPath("$.name").exists())
                    .andExpect(jsonPath("$.registrationNumber").exists())
                    .andExpect(jsonPath("$.address").exists())
                    .andExpect(jsonPath("$.createdAt").exists());
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        void testGetCompanyByIdIs404() {
            mvc.perform(get(URL + UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

    }

    @Nested
    @DisplayName("POST create company")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"EDITOR", "ADMIN"})
    class Create {
        private CompanyDto requestBody;
        private UUID createdCompanyId;

        @BeforeAll
        void beforeAll() {
            requestBody = CompanyDto.builder()
                    .name("Smida")
                    .registrationNumber("#20241111")
                    .address("Ukraine, Dnipro")
                    .build();
        }

        @AfterAll
        void afterAll() {
            companyRepository.deleteById(createdCompanyId);
        }

        @Test
        @SneakyThrows
        @DisplayName("[201] created and got resource location")
        void testCreateCompanyIs201() {
            MvcResult mvcResult = mvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("Smida"))
                    .andExpect(jsonPath("$.registrationNumber").value("#20241111"))
                    .andExpect(jsonPath("$.address").value("Ukraine, Dnipro"))
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andReturn();
            Company responseCompany = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Company.class);
            createdCompanyId = responseCompany.getId();

            String actualResource = URL + createdCompanyId;
            String locationHeader = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
            assertThat(locationHeader).isEqualTo(actualResource);
        }

        @Test
        @SneakyThrows
        @DisplayName("[400] invalid request body")
        void testCreateCompanyIs400() {
            requestBody.setName(null);
            mvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

    }

    @Nested
    @DisplayName("PUT update company by ID")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @WithMockUser(roles = {"EDITOR", "ADMIN"})
    class Update {
        private CompanyDto requestBody;
        private Company savedCompany;
        private List<Report> savedReports = new ArrayList<>();

        @BeforeAll
        void beforeAll() {
            requestBody = CompanyDto.builder()
                    .name("Smida")
                    .registrationNumber("#20241111")
                    .address("Ukraine, Dnipro")
                    .build();
            savedCompany = companyRepository.save(Company.builder()
                    .name("FakeSmida")
                    .registrationNumber("#FakeSmida")
                    .address("FakeSmida")
                    .build());
            for (int j = 0; j < NUMBER_REPORTS_PER_COMPANY; j++) {
                // Create Report with the saved company
                Report report = Report.builder()
                        .reportDate(Instant.now())
                        .totalRevenue(BigDecimal.valueOf(RandomUtils.nextDouble(1000, 10000)))
                        .netProfit(BigDecimal.valueOf(RandomUtils.nextDouble(100, 1000)))
                        .company(savedCompany) // Reference the saved company
                        .build();
                savedReports.add(reportRepository.save(report)); // Save to get the generated UUID
            }
        }

        @AfterAll
        void afterAll() {
            companyRepository.deleteById(savedCompany.getId());
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        @Order(1)
        void testUpdateCompanyIs404() {
            mvc.perform(put(URL + UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @SneakyThrows
        @DisplayName("[200] updated")
        @Order(5)
        void testUpdateCompanyIs200() {
            mvc.perform(put(URL + savedCompany.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.name").value("Smida"))
                    .andExpect(jsonPath("$.registrationNumber").value("#20241111"))
                    .andExpect(jsonPath("$.address").value("Ukraine, Dnipro"))
                    .andExpect(jsonPath("$.createdAt").exists());

            List<Report> allByCompany = reportRepository.findAllByCompany(savedCompany);
            assertThat(allByCompany.size()).isEqualTo(NUMBER_REPORTS_PER_COMPANY);
        }

        @Test
        @SneakyThrows
        @DisplayName("[400] invalid request body")
        @Order(10)
        void testUpdateCompanyIs400() {
            requestBody.setName(null);
            mvc.perform(put(URL + savedCompany.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

    }

    @Nested
    @DisplayName("DELETE company by ID")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"ADMIN"})
    class Delete {
        private Company savedCompany;
        private List<Report> savedReports = new ArrayList<>();
        private List<ReportDetails> savedReportDetails = new ArrayList<>();

        @BeforeAll
        void beforeAll() {
            savedCompany = Company.builder()
                    .name("Name " + RandomStringUtils.randomAlphabetic(5))
                    .address("Address " + RandomStringUtils.randomAlphabetic(10))
                    .registrationNumber("Number " + RandomUtils.nextInt(1, 2000))
                    .createdAt(Instant.now())
                    .build();
            savedCompany = companyRepository.save(savedCompany);

            List<Report> reports = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                // Create Report with the saved company
                Report report = Report.builder()
                        .reportDate(Instant.now())
                        .totalRevenue(BigDecimal.valueOf(RandomUtils.nextDouble(1000, 10000)))
                        .netProfit(BigDecimal.valueOf(RandomUtils.nextDouble(100, 1000)))
                        .company(savedCompany) // Reference the saved company
                        .build();
                Report savedReport = reportRepository.save(report); // Save to get the generated UUID

                // Use the generated UUID to create and save ReportDetails
                ReportDetails details = ReportDetails.builder()
                        .reportId(savedReport.getId())
                        .financialData("Financial Data " + RandomStringUtils.randomAlphabetic(10))
                        .comments("Comments " + RandomStringUtils.randomAlphabetic(10))
                        .build();

                savedReportDetails.add(reportDetailsRepository.save(details)); // Add the saved details to the details list

                // Add the saved report to the reports list
                reports.add(savedReport);
            }
            savedCompany.setReports(reports);
            savedCompany = companyRepository.save(savedCompany);
            savedReports.addAll(savedCompany.getReports());

            log.info("Data initialization completed");
        }

        @AfterAll
        void afterAll() {
            companyRepository.deleteById(savedCompany.getId());
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        @Order(1)
        void testDeleteCompanyIs404() {
            mvc.perform(delete(URL + UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

        @Test
        @SneakyThrows
        @DisplayName("[204] deleted")
        @Order(5)
        void testDeleteByIdIs204() {
            mvc.perform(delete(URL + "/" + savedCompany.getId()))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] already deleted")
        @Order(10)
        void testAlreadyDeletedByIdIs404() {
            mvc.perform(delete(URL + "/" + savedCompany.getId()))
                    .andDo(print())
                    .andExpect(status().isNotFound());

            Optional<Company> company = companyRepository.findById(savedCompany.getId());
            List<Report> report = reportRepository.findAllById(savedReports.stream().map(Report::getId).toList());
            List<ReportDetails> reportDetails = reportDetailsRepository.findAllById(savedReportDetails.stream().map(ReportDetails::getReportId).toList());

            assertThat(company).isEmpty();
            log.info("Company was deleted");
            assertThat(report).isEmpty();
            log.info("Reports was deleted");
            assertThat(reportDetails).isEmpty();
            log.info("Report details was deleted");
        }

    }

}
