package es.lavanda.automated.download.show.repository;

import java.util.List;

import es.lavanda.automated.download.show.models.ShowModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowModelRepository extends PagingAndSortingRepository<ShowModel, String> {

    List<ShowModel> findTop6ByOrderByCreatedAtDesc();

    boolean existsByTorrentsTorrentUrl(String torrentUrl);

    Page<ShowModel> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<ShowModel> findByIdOriginal(String idOriginal);
}