package pl.szvmczek.movieclub.domain.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    List<Movie> findAllByPromotedIsTrue();
    @Query("""
           select m
           from Movie m
           join m.genre g
           where lower(g.name) = lower(:name)
           """)
    List<Movie> findAllByGenreNameIgnoreCase(@Param("name") String genre);
    @Query("select m from Movie m join m.ratings r group by m order by avg(r.rating) desc")
    List<Movie> findTopByRating(Pageable pageable);

}
