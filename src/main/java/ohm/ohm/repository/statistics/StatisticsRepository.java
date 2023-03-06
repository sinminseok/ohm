package ohm.ohm.repository.statistics;

import ohm.ohm.entity.Statistics.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("select st from Statistics st where st.gym.id = :gymId")
    Statistics get_statistics(@Param("gymId") Long gymId);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.one = (st.one + :count)/2 where st.gym.id = :gymId")
    void update_1(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.two = (st.two + :count)/2 where st.gym.id = :gymId")
    void update_2(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.three = (st.three + :count)/2 where st.gym.id = :gymId")
    void update_3(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.four = (st.four + :count)/2 where st.gym.id = :gymId")
    void update_4(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.five = (st.five + :count)/2 where st.gym.id = :gymId")
    void update_5(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.six = (st.six + :count)/2 where st.gym.id = :gymId")
    void update_6(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.seven = (st.seven + :count)/2 where st.gym.id = :gymId")
    void update_7(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.eight = (st.eight + :count)/2 where st.gym.id = :gymId")
    void update_8(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.nine = (st.nine + :count)/2 where st.gym.id = :gymId")
    void update_9(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.ten = (st.ten + :count)/2 where st.gym.id = :gymId")
    void update_10(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.eleven = (st.eleven + :count)/2 where st.gym.id = :gymId")
    void update_11(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.twelve = (st.twelve + :count)/2 where st.gym.id = :gymId")
    void update_12(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.thirteen = (st.thirteen + :count)/2 where st.gym.id = :gymId")
    void update_13(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.fourteen = (st.fourteen + :count)/2 where st.gym.id = :gymId")
    void update_14(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.fifteen = (st.fifteen + :count)/2 where st.gym.id = :gymId")
    void update_15(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.sixteen = (st.sixteen + :count)/2 where st.gym.id = :gymId")
    void update_16(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.seventeen = (st.seventeen + :count)/2 where st.gym.id = :gymId")
    void update_17(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.eighteen = (st.eighteen + :count)/2 where st.gym.id = :gymId")
    void update_18(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.nineteen = (st.nineteen + :count)/2.0 where st.gym.id = :gymId")
    void update_19(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.twenty = (st.twenty + :count)/2 where st.gym.id = :gymId")
    void update_20(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.twenty_one = (st.twenty_one + :count)/2 where st.gym.id = :gymId")
    void update_21(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.twenty_two = (st.twenty_two + :count)/2 where st.gym.id = :gymId")
    void update_22(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.twenty_three = (st.twenty_three + :count)/2 where st.gym.id = :gymId")
    void update_23(@Param("gymId") Long gymId, @Param("count") double count);

    @Modifying(clearAutomatically = true)
    @Query("update Statistics st set st.twenty_four = (st.twenty_four + :count)/2 where st.gym.id = :gymId")
    void update_24(@Param("gymId") Long gymId, @Param("count") double count);


}
