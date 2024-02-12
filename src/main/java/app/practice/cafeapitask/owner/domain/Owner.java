package app.practice.cafeapitask.owner.domain;

import app.practice.cafeapitask.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owner", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone_number")
})
public class Owner {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Comment("사장님 연락처")
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Comment("비밀번호")
    @Column(nullable = false, name = "password")
    private String password;

    @OneToMany(mappedBy = "owner", fetch = LAZY)
    private final List<Product> products = new ArrayList<>();

    @Builder
    private Owner(Long id, String phoneNumber, String password) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
