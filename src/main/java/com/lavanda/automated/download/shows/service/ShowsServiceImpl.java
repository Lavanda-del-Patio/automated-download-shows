package com.lavanda.automated.download.shows.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.lavanda.automated.download.shows.exception.AutomatedDownloadShowsException;
import com.lavanda.automated.download.shows.models.ShowModel;
import com.lavanda.automated.download.shows.models.TransmissionModelRequest;
import com.lavanda.automated.download.shows.repository.ShowModelRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import es.lavanda.lib.common.model.MediaIDTO;
import es.lavanda.lib.common.model.MediaODTO;
import es.lavanda.lib.common.model.ShowModelTorrent;
import es.lavanda.lib.common.model.MediaIDTO.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShowsServiceImpl {

    private final ShowModelRepository showModelRepository;

    private final ProducerService producerService;

    public List<ShowModel> getAllShows() {
        return StreamSupport.stream(showModelRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Page<ShowModel> getAllShowsOrderedByCreated(Pageable pageable) {
        return showModelRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public ShowModel getShow(String id) {
        Optional<ShowModel> optShowModel = showModelRepository.findById(id);
        if (optShowModel.isPresent()) {
            return optShowModel.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found");
        }
    }

    public List<ShowModel> getLastShows() {
        return showModelRepository.findTop6ByOrderByCreatedAtDesc();
    }

    public ShowModel save(ShowModel showModel) {
        return showModelRepository.save(showModel);
    }

    public void deleteShow(String id) {
        showModelRepository.delete(getShow(id));
    }

    public ShowModel updateShow(ShowModel showModel) {
        ShowModel showModelUpdated = showModelRepository.save(showModel);
        sendToAgent(showModelUpdated);
        return showModelUpdated;
    }

    public void executeShow(ShowModelTorrent showModelTorrent) {
        log.info("Execute show {}", showModelTorrent.toString());
        if (Boolean.FALSE.equals(showModelRepository.existsByTorrentsTorrentUrl(showModelTorrent.getTorrentUrl()))) {
            try {
                createNewShowModel(showModelTorrent);
            } catch (AutomatedDownloadShowsException e) {
                log.error("Show not sended to Agent {} ", showModelTorrent, e);
            }
        }
    }

    private void createNewShowModel(ShowModelTorrent showModelTorrent) {
        ShowModel showModel = new ShowModel();
        showModel.getTorrents().add(showModelTorrent);
        sendToAgent(showModelRepository.save(showModel));
    }

    private void sendToAgent(ShowModel filmModel) {
        MediaIDTO mediaIDTO = new MediaIDTO();
        mediaIDTO.setId(filmModel.getId());
        String torrentCroppedTitle = filmModel.getTorrents().stream()
                .filter(torrent -> StringUtils.hasText(torrent.getTorrentCroppedTitle()))
                .map(ShowModelTorrent::getTorrentCroppedTitle).findFirst().orElse(null);
        mediaIDTO.setTorrentCroppedTitle(torrentCroppedTitle);
        mediaIDTO.setTorrentTitle(
                filmModel.getTorrents().stream().filter(torrent -> StringUtils.hasText(torrent.getTorrentTitle()))
                        .map(ShowModelTorrent::getTorrentTitle).findFirst().orElse(null));
        mediaIDTO.setType(filmModel.getType());
        producerService.sendToFeedAgentTMDB(mediaIDTO);
    }

    public void updateShowWithMediaDTO(MediaODTO mediaODTO) {
        log.info("Updating show with mediaIDTO {} ", mediaODTO);
        ShowModel filmModel = getShow(mediaODTO.getId());
        List<ShowModel> filmModelsWithSameOriginalId = showModelRepository.findByIdOriginal(mediaODTO.getIdOriginal());
        for (ShowModel filmModelIterator : filmModelsWithSameOriginalId) {
            filmModel.getTorrents().addAll(filmModelIterator.getTorrents());
            showModelRepository.deleteAll(filmModelsWithSameOriginalId);
        }
        log.info("Update film {} with id {}", filmModel.getTorrents().stream().map(torrent -> torrent.getTorrentTitle())
                .findFirst().orElse("NO NAME"), filmModel.getId());
        filmModel.setImage(mediaODTO.getImage());
        filmModel.setTitle(mediaODTO.getTitle());
        filmModel.setTitleOriginal(mediaODTO.getTitleOriginal());
        filmModel.setReleaseDate(mediaODTO.getReleaseDate());
        filmModel.setIdOriginal(mediaODTO.getIdOriginal());
        filmModel.setBackdropImage(mediaODTO.getBackdropImage());
        filmModel.setOverview(mediaODTO.getOverview());
        filmModel.setVoteAverage(mediaODTO.getVoteAverage());
        save(filmModel);
    }

    public void updateLibrary() {
        Iterable<ShowModel> withDuplicatesIterable = showModelRepository.findAll();
        List<ShowModel> filmModels = new ArrayList<>();
        withDuplicatesIterable.forEach(filmModels::add);
        for (ShowModel filmModel : filmModels) {
            sendToAgent(filmModel);
            // filmModelRepository.save(filmModel);
            // updateFilm(filmModel);
        }
    }

}