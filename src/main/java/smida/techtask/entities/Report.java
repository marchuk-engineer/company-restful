package smida.techtask.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Timestamp reportDate;

    @Column(nullable = false)
    private BigDecimal totalRevenue;

    @Column(nullable = false)
    private BigDecimal netProfit;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}
