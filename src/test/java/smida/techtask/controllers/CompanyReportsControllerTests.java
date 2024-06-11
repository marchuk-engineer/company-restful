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
import smida.techtask.dto.ReportDto;
import smida.techtask.entities.Company;
import smida.techtask.entities.Report;
import smida.techtask.entities.ReportDetails;
import smida.techtask.repositories.CompanyRepository;
import smida.techtask.repositories.ReportDetailsRepository;
import smida.techtask.repositories.ReportRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ActiveProfiles(profiles = {"test"})
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("Company Reports Controller API tests")
@Log4j2
public class CompanyReportsControllerTests {

    private static final String URL = ApiConstants.BASE_URL + "/companies/{companyId}/reports";
    private static final String URL_WITH_REPORT_ID = ApiConstants.BASE_URL + "/companies/{companyId}/reports/{reportId}";
    private static final String RESOURCE_LOCATION = ApiConstants.BASE_URL + "/companies/%s/reports";
    private static final Integer NUMBER_OF_INIT_COMPANIES = 100;
    private static final Integer NUMBER_REPORTS_PER_COMPANY = 5;

    private Map<Company, List<Report>> initCompanyWithReports = new HashMap<>();

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
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    private void initData() {
        for (int i = 0; i < NUMBER_OF_INIT_COMPANIES; i++) {
            List<Report> reports = new ArrayList<>();
            Company company = Company.builder()
                    .name("Name " + RandomStringUtils.randomAlphabetic(5))
                    .address("Address " + RandomStringUtils.randomAlphabetic(10))
                    .registrationNumber("Number " + RandomUtils.nextInt(1, 2000))
                    .createdAt(Instant.now())
                    .build();
            company = companyRepository.save(company);

            for (int j = 0; j < NUMBER_REPORTS_PER_COMPANY; j++) {
                Report report = Report.builder()
                        .reportDate(Instant.now())
                        .totalRevenue(BigDecimal.valueOf(RandomUtils.nextDouble(1000, 10000)))
                        .netProfit(BigDecimal.valueOf(RandomUtils.nextDouble(100, 1000)))
                        .company(company)
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
            initCompanyWithReports.put(company, reports);
            companyRepository.save(company);
        }
        log.info("Data initialization completed");
    }

    private UUID getAnySavedCompanyId() {
        List<UUID> companyIds = initCompanyWithReports.keySet().stream().map(Company::getId).toList();
        return companyIds.get(new Random().nextInt(0, NUMBER_OF_INIT_COMPANIES));
    }

    public Map.Entry<Company, List<Report>> getRandomEntry() {
        List<Map.Entry<Company, List<Report>>> entries = new ArrayList<>(initCompanyWithReports.entrySet());
        int randomIndex = new Random().nextInt(NUMBER_OF_INIT_COMPANIES);
        return entries.get(randomIndex);
    }

    @Nested
    @DisplayName("GET company reports")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"USER", "EDITOR", "ADMIN"})
    class GetAll {

        @Test
        @SneakyThrows
        @DisplayName("[200] present")
        void testGetByIdIs200() {
            Map.Entry<Company, List<Report>> randomEntry = getRandomEntry();
            Company company = randomEntry.getKey();
            Report report = randomEntry.getValue().getFirst();
            mvc.perform(get(URL, company.getId(), report.getId()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(company.getId().toString()))
                    .andExpect(jsonPath("$.name").value(company.getName()))
                    .andExpect(jsonPath("$.registrationNumber").value(company.getRegistrationNumber()))
                    .andExpect(jsonPath("$.address").value(company.getAddress()))
                    .andExpect(jsonPath("$.createdAt").exists())
                    .andExpect(jsonPath("$.reports").isArray())
                    .andExpect(jsonPath("$.reports[0].reportDate").exists())
                    .andExpect(jsonPath("$.reports[0].totalRevenue").value(report.getTotalRevenue()))
                    .andExpect(jsonPath("$.reports[0].netProfit").value(report.getNetProfit()));
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        void testByIdIs404() {
            mvc.perform(get(URL, UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

    }


    @Nested
    @DisplayName("GET report by ID")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"USER", "EDITOR", "ADMIN"})
    class GetById {

        @Test
        @SneakyThrows
        @DisplayName("[200] present")
        void testGetByIdIs200() {
            Map.Entry<Company, List<Report>> randomEntry = getRandomEntry();
            Company company = randomEntry.getKey();
            Report report = randomEntry.getValue().getFirst();
            mvc.perform(get(URL_WITH_REPORT_ID, company.getId(), report.getId()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(report.getId().toString()))
                    .andExpect(jsonPath("$.reportDate").exists())
                    .andExpect(jsonPath("$.totalRevenue").value(report.getTotalRevenue()))
                    .andExpect(jsonPath("$.netProfit").value(report.getNetProfit().toString()));
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        void testByIdIs404() {
            mvc.perform(get(URL_WITH_REPORT_ID, UUID.randomUUID(), UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

    }

    @Nested
    @DisplayName("POST create report")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @WithMockUser(roles = {"EDITOR", "ADMIN"})
    class Create {
        private ReportDto requestBody;
        private UUID createdReportId;
        private Company savedCompany;

        @BeforeAll
        void beforeAll() {
            savedCompany = getRandomEntry().getKey();
            requestBody = ReportDto.builder()
                    .totalRevenue(BigDecimal.valueOf(10000L))
                    .netProfit(BigDecimal.valueOf(10000L))
                    .build();
        }

        @AfterAll
        void afterAll() {
            reportRepository.deleteById(createdReportId);
        }

        @Test
        @SneakyThrows
        @DisplayName("[201] created and got resource location")
        void testCreateIs201() {
            MvcResult mvcResult = mvc.perform(post(URL, savedCompany.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.totalRevenue").value(10000L))
                    .andExpect(jsonPath("$.netProfit").value(10000L))
                    .andExpect(jsonPath("$.reportDate").exists())
                    .andReturn();
            Company responseCompany = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Company.class);
            createdReportId = responseCompany.getId();

            String actualResource = String.format(RESOURCE_LOCATION, savedCompany.getId()).concat("/" + createdReportId);
            String locationHeader = mvcResult.getResponse().getHeader(HttpHeaders.LOCATION);
            assertThat(locationHeader).isEqualTo(actualResource);
        }

        @Test
        @SneakyThrows
        @DisplayName("[400] invalid request body")
        void testCreateIs400() {
            requestBody.setNetProfit(null);
            mvc.perform(post(URL, savedCompany.getId())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

    }

    @Nested
    @DisplayName("PUT update report by ID")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @WithMockUser(roles = {"EDITOR", "ADMIN"})
    class Update {

        private ReportDto requestBody;
        private Report savedReport;
        private Company savedCompany;

        @BeforeAll
        void beforeAll() {
            requestBody = ReportDto.builder()
                    .totalRevenue(BigDecimal.valueOf(99990L))
                    .netProfit(BigDecimal.valueOf(99990L))
                    .build();
            savedCompany = getRandomEntry().getKey();
            savedReport = Report.builder()
                    .totalRevenue(BigDecimal.valueOf(10000L))
                    .netProfit(BigDecimal.valueOf(10000L))
                    .company(savedCompany)
                    .build();
            savedReport = reportRepository.save(savedReport);
        }


        @AfterAll
        void afterAll() {
            reportRepository.deleteById(savedReport.getId());
        }

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        @Order(1)
        void testUpdateIs404() {
            mvc.perform(put(URL_WITH_REPORT_ID, UUID.randomUUID(), UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(requestBody))
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

//        @Test
//        @SneakyThrows
//        @DisplayName("[200] updated")
//        @Order(5)
//        void testUpdateIs200() {
//            mvc.perform(put(URL_WITH_REPORT_ID, savedCompany.getId(), savedReport.getId())
//                            .contentType(MediaType.APPLICATION_JSON_VALUE)
//                            .accept(MediaType.APPLICATION_JSON_VALUE)
//                            .content(objectMapper.writeValueAsString(requestBody))
//                    )
//                    .andExpect(status().isOk())
//                    .andDo(print())
//                    .andExpect(jsonPath("$.id").exists())
//                    .andExpect(jsonPath("$.totalRevenue").value(99990L))
//                    .andExpect(jsonPath("$.netProfit").value(9999L))
//                    .andExpect(jsonPath("$.createdAt").exists());
//
//            List<Report> allByCompany = reportRepository.findAllByCompany(savedCompany);
//            assertThat(allByCompany.size()).isEqualTo(NUMBER_REPORTS_PER_COMPANY);
//        }

        @Test
        @SneakyThrows
        @DisplayName("[400] invalid request body")
        @Order(10)
        void testUpdateIs400() {
            requestBody.setNetProfit(null);
            mvc.perform(put(URL_WITH_REPORT_ID, savedCompany.getId(), savedCompany.getId())
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

        @Test
        @SneakyThrows
        @DisplayName("[404] not found")
        @Order(1)
        void testDeleteIs404() {
            mvc.perform(delete(URL_WITH_REPORT_ID, UUID.randomUUID(), UUID.randomUUID()))
                    .andExpect(status().isNotFound())
                    .andDo(print());
        }

//        @Test
//        @SneakyThrows
//        @DisplayName("[204] deleted")
//        @Order(5)
//        void testDeleteByIdIs204() {
//
//        }
//
//        @Test
//        @SneakyThrows
//        @DisplayName("[404] already deleted")
//        @Order(10)
//        void testAlreadyDeletedByIdIs404() {
//
//        }
    }

}
