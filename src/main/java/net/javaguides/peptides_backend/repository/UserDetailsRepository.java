package net.javaguides.peptides_backend.repository;

import net.javaguides.peptides_backend.entity.UsersDetails;
import net.javaguides.peptides_backend.entity.peptide_1121_r1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UsersDetails, String> {

    @Query(value = "select a.username from UsersDetails a where a.username like concat('%', ?1, '%')")
    List<UsersDetails> findByUsername(String param);

    @Query(value = "select a from UsersDetails a where a.username = ?1 and a.passwordHash = ?2")
    List<UsersDetails> findByUsernameAndPasswod(String userName,String password);


    @Query("select a from UsersDetails a where a.userId = ?1")
    List<UsersDetails> findByUsernameToSendMail(Integer userId);


}
