package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RankRepository extends JpaRepository<Rank, Long> {
    Rank findFirstByMinPointsLessThanEqualOrderByMinPointsDesc(int minPoints);

}
