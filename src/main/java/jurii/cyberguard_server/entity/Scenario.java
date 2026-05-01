package jurii.cyberguard_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "scenarios")
@Getter
@Setter
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Техническое имя, например 'interestingSC'

    @Column(nullable = false)
    private String title; // Отображаемое имя для списка

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> content; // Здесь будет весь объект со steps и start

    private String type; // SMS, EMAIL или CALL
}