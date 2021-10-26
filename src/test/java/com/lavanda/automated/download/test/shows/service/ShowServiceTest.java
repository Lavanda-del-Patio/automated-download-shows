package com.lavanda.automated.download.test.shows.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.lavanda.automated.download.shows.models.ShowModel;
import com.lavanda.automated.download.shows.repository.ShowModelRepository;
import com.lavanda.automated.download.shows.service.ProducerService;
import com.lavanda.automated.download.shows.service.ShowsServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {

        @InjectMocks
        private ShowsServiceImpl showsServiceImpl;

        @Mock
        private ShowModelRepository showModelRepository;

        @Mock
        private ProducerService producerService;

        @Test
        @SneakyThrows
        @Disabled
        public void test() {
                // ShowModel showModelToDownload = ShowModel.builder()
                //                 .image("https://pctfenix.com/uploads/i/thumbs/0_1583302664-Blindspot.jpg")
                //                 .title("blindspot").build();
                // Show show1 = new Show();
                // show1.setChapters(List.of(1));
                // show1.setSeasons(List.of(4));
                // show1.setTorrent(
                //                 "https://pctfenix.com/uploads/t/118033_-1549608530-blindspot---temporada-4--hdtv-720p-ac3-5-1.torrent");
                // Show show2 = new Show();
                // show2.setChapters(List.of(19));
                // show2.setSeasons(List.of(3));
                // show2.setTorrent(
                //                 "https://pctfenix.com/uploads/t/107820_-1526461441-blindspot---temporada-3--hdtv-720p-ac3-5-1.torrent");
                // Show show3 = new Show();
                // show3.setChapters(List.of(29));
                // show3.setSeasons(List.of(4));
                // show3.setTorrent(
                //                 "https://pctfenix.com/uploads/t/123705_-1560533348-Blindspot---Temporada-4--HDTV-720p-AC3-5-1.torrent");
                // Show show4 = new Show();
                // show4.setChapters(List.of(29, 2));
                // show4.setSeasons(List.of(4));
                // show4.setTorrent(
                //                 "https://pctfenix.com/uploads/t/121743_-1556857153-blindspot---temporada-4--hdtv-720p-ac3-5-1.torrent");
                // List<Show> show = new ArrayList<>();
                // show.add(show1);
                // show.add(show2);
                // show.add(show3);
                // show.add(show4);
                // showModelToDownload.setShow(show);
                // Assertions.assertNotNull(showsServiceImpl);

                // List<ShowModel> showModels = new ArrayList<>();
                // showModels.add(ShowModel.builder().title("Blindspot").build());
                // when(showModelRepository.findAll()).thenReturn(showModels);
                // showsServiceImpl.executeShow(showModelToDownload);
        }
}
